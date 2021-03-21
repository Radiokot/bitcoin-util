package ua.com.radiokot.bitcoinutil.tx.model

data class UtxoData(
    val txId: String,
    val vOut: Long,
    val redeemScriptHex: String,
    val amount: Long,
    val privateKeyWif: String
) {
    val uid: String
        get() = getUtxoUid(txId, vOut)

    companion object {
        @JvmStatic
        fun getUtxoUid(
            txId: String,
            vOut: Long
        ) = "${txId}_$vOut"
    }
}
