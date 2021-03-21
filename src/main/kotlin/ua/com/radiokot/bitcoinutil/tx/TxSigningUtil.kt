package ua.com.radiokot.bitcoinutil.tx

import org.bitcoinj.core.*
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.script.Script
import ua.com.radiokot.bitcoinutil.tx.model.UtxoData

class TxSigningUtil
/**
 * @see [MainNetParams], [TestNet3Params]
 */
@JvmOverloads
constructor(
    private val networkParams: NetworkParameters = MainNetParams.get()
) {

    /**
     * @param txToSignHex not signed or half signed transaction hex
     * @param utxoData UTXO data the transaction is spending
     *
     * @return signed transaction hex
     */
    fun signHex(
        txToSignHex: String,
        utxoData: Collection<UtxoData>
    ): String {
        val utxoMap = utxoData
            .associateBy(UtxoData::uid)

        val tx = Transaction(
            networkParams,
            Utils.HEX.decode(txToSignHex)
        )

        tx.inputs.forEachIndexed { i, input ->
            val outpoint = input.outpoint
            val utxo = utxoMap[UtxoData.getUtxoUid(outpoint.hash.toString(), outpoint.index)]
                ?: throw IllegalArgumentException("No UTXO found for #$i input")

            val amount = Coin.valueOf(utxo.amount)
            val key = DumpedPrivateKey.fromBase58(networkParams, utxo.privateKeyWif).key
            val redeemScript = Script(Utils.HEX.decode(utxo.redeemScriptHex))

            val signature = tx.calculateWitnessSignature(
                i,
                key,
                redeemScript,
                amount,
                Transaction.SigHash.ALL,
                false
            )

            val oldWitness = tx.getWitness(i)

            val newWitness =
                when (oldWitness.pushCount) {
                    0 ->
                        TransactionWitness(3).apply {
                            setPush(0, byteArrayOf())
                            setPush(1, signature.encodeToBitcoin())
                            setPush(2, redeemScript.program)
                        }
                    3 ->
                        TransactionWitness(oldWitness.pushCount + 1).apply {
                            setPush(0, byteArrayOf())

                            val publicKeys = redeemScript.pubKeys.map { it.pubKey }

                            if (key.pubKey.contentEquals(publicKeys[0])) {
                                setPush(1, signature.encodeToBitcoin())
                                setPush(2, oldWitness.getPush(1))
                            } else {
                                setPush(1, oldWitness.getPush(1))
                                setPush(2, signature.encodeToBitcoin())
                            }

                            setPush(3, redeemScript.program)
                        }
                    else ->
                        throw IllegalStateException("Don't know what to do with ${oldWitness.pushCount}-pushes witness")
                }

            tx.setWitness(i, newWitness)
        }

        return Utils.HEX.encode(tx.bitcoinSerialize())
    }
}