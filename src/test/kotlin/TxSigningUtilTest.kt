import org.bitcoinj.params.MainNetParams
import org.junit.Assert
import org.junit.Test
import ua.com.radiokot.bitcoinutil.tx.TxSigningUtil
import ua.com.radiokot.bitcoinutil.tx.model.UtxoData

class TxSigningUtilTest {
    @Test
    fun signWithReorder() {
        val result = TxSigningUtil(MainNetParams.get())
            .signHex(
                txToSignHex = "01000000000101a4fbddc22697a933f5e4fbb14fa36cda0de3be62b08af2fa6e3cacb02474ddf30000000023220020c9ef908f40de2f11e22d3cd8cadca1a64e638ad43c1b3a3b8730c544fdddf78cffffffff022f8bda00000000001976a91450bc59a1d4677fb9eba0cc6637d7b45e5d12bec688ac385500000000000017a914a763084a432dddff724fc6d72d4bbaba1099ad1f870300483045022100c1ea4cbcba6a621179623c27b560df1496a3ce6c502dc54f30b6d7eb23e2964202201b233e431491ce69c4928c2efafbcf3bb0fa2129d2be963a9a06273c667524e80147522102c43081271ee3d901ef3ac4ac6aa5722adb403207984b5f4a5f170585fb4b7f642102def99f799430c5be73caef4cb2e01e167b563e1929886b93740af13441a9218b52ae00000000",
                utxoData = listOf(
                    UtxoData(
                        txId = "f3dd7424b0ac3c6efaf28ab062bee30dda6ca34fb1fbe4f533a99726c2ddfba4",
                        vOut = 0,
                        redeemScriptHex = "522102c43081271ee3d901ef3ac4ac6aa5722adb403207984b5f4a5f170585fb4b7f642102def99f799430c5be73caef4cb2e01e167b563e1929886b93740af13441a9218b52ae",
                        amount = 14352479,
                        privateKeyWif = "KxLYTLryNoAzgEpmJa9TbjzQUiYFf6M6hw9rnUEFXbiKHNAiR4ET"
                    )
                )
            )

        Assert.assertEquals(
            "01000000000101a4fbddc22697a933f5e4fbb14fa36cda0de3be62b08af2fa6e3cacb02474ddf30000000023220020c9ef908f40de2f11e22d3cd8cadca1a64e638ad43c1b3a3b8730c544fdddf78cffffffff022f8bda00000000001976a91450bc59a1d4677fb9eba0cc6637d7b45e5d12bec688ac385500000000000017a914a763084a432dddff724fc6d72d4bbaba1099ad1f870400483045022100b73adefdf3291bf2821cd91e7d2785d90bd05b76258613a556c7a3a3948c42120220756c176ec714def9d7d7054d61649ce599e10b122513ff35225d18d8f59a3a3201483045022100c1ea4cbcba6a621179623c27b560df1496a3ce6c502dc54f30b6d7eb23e2964202201b233e431491ce69c4928c2efafbcf3bb0fa2129d2be963a9a06273c667524e80147522102c43081271ee3d901ef3ac4ac6aa5722adb403207984b5f4a5f170585fb4b7f642102def99f799430c5be73caef4cb2e01e167b563e1929886b93740af13441a9218b52ae00000000",
            result
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun signWithNotEnoughData() {
        TxSigningUtil(MainNetParams.get())
            .signHex(
                txToSignHex = "01000000000101a4fbddc22697a933f5e4fbb14fa36cda0de3be62b08af2fa6e3cacb02474ddf30000000023220020c9ef908f40de2f11e22d3cd8cadca1a64e638ad43c1b3a3b8730c544fdddf78cffffffff022f8bda00000000001976a91450bc59a1d4677fb9eba0cc6637d7b45e5d12bec688ac385500000000000017a914a763084a432dddff724fc6d72d4bbaba1099ad1f870300483045022100c1ea4cbcba6a621179623c27b560df1496a3ce6c502dc54f30b6d7eb23e2964202201b233e431491ce69c4928c2efafbcf3bb0fa2129d2be963a9a06273c667524e80147522102c43081271ee3d901ef3ac4ac6aa5722adb403207984b5f4a5f170585fb4b7f642102def99f799430c5be73caef4cb2e01e167b563e1929886b93740af13441a9218b52ae00000000",
                utxoData = listOf()
            )
    }
}