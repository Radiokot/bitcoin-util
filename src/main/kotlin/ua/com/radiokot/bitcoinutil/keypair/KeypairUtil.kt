package ua.com.radiokot.bitcoinutil.keypair

import org.bitcoinj.core.ECKey
import ua.com.radiokot.bitcoinutil.keypair.model.SerializedKeypair

object KeypairUtil {
    @JvmStatic
    fun createKey(): SerializedKeypair {
        return SerializedKeypair(ECKey())
    }
}