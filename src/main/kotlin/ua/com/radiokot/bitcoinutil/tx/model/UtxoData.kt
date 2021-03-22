package ua.com.radiokot.bitcoinutil.tx.model

import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Utils
import org.bitcoinj.script.ScriptBuilder

data class UtxoData(
    val txId: String,
    val vOut: Long,
    val redeemScriptHex: String,
    val amount: Long,
    val privateKeyHex: String,
) {
    /**
     * Creates UTXO with multisig redeem script for 2 of 2 keys: [privateKeyHex] and [secondMultisigPublicKeyHex]
     */
    constructor(
        txId: String,
        vOut: Long,
        amount: Long,
        privateKeyHex: String,
        secondMultisigPublicKeyHex: String,
    ) : this(
        txId = txId,
        vOut = vOut,
        amount = amount,
        privateKeyHex = privateKeyHex,
        redeemScriptHex = get2of2MultisigRedeemScriptHex(privateKeyHex, secondMultisigPublicKeyHex),
    )

    val uid: String
        get() = getUtxoUid(txId, vOut)

    companion object {
        @JvmStatic
        fun getUtxoUid(
            txId: String,
            vOut: Long
        ) = "${txId}_$vOut"

        private fun get2of2MultisigRedeemScriptHex(
            privateKeyHex: String,
            secondMultisigPublicKeyHex: String
        ): String {
            return listOf(
                ECKey.fromPublicOnly(Utils.HEX.decode(secondMultisigPublicKeyHex)),
                ECKey.fromPrivate(Utils.HEX.decode(privateKeyHex))
            )
                .sortedBy(ECKey::getPublicKeyAsHex)
                .let { ScriptBuilder.createMultiSigOutputScript(2, it) }
                .program
                .let(Utils.HEX::encode)
        }
    }
}
