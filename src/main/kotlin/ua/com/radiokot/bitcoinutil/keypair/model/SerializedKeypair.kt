package ua.com.radiokot.bitcoinutil.keypair.model

import org.bitcoinj.core.ECKey

data class SerializedKeypair(
    val publicKeyHex: String,
    val privateKeyHex: String
) {
    constructor(source: ECKey): this(
        publicKeyHex = source.publicKeyAsHex,
        privateKeyHex = source.privateKeyAsHex
    )
}
