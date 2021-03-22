import org.bitcoinj.params.MainNetParams
import org.junit.Assert
import org.junit.Test
import ua.com.radiokot.bitcoinutil.tx.TxSigningUtil
import ua.com.radiokot.bitcoinutil.tx.model.TxOutputData
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
                        privateKeyHex = "215a276e28af03830d05fea6053c12737803f01a638f375cf8188bbd6aad7781"
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

    @Test
    fun createSigned() {
        val privateKeyHex = "6b493f5903fcff6747aadbcfd32c9543a587920ced6a99d3a8c13d940f6d1c58"
        val result = TxSigningUtil(MainNetParams.get())
            .createAndSignTransaction(
                inputsUtxo = listOf(
                    UtxoData(
                        txId = "86c05695b4cbf6990565703fc4babb42fe60abae560f83716191f8fdec8110c6",
                        vOut = 1,
                        amount = 167950,
                        privateKeyHex = privateKeyHex,
                        secondMultisigPublicKeyHex = "029c4579f8dfaff0e292cb7443ead2c23ac92df70c8f618f965de3f45b281f460a"
                    ),
                    UtxoData(
                        txId = "d3ea302be3f05d93fe9fcf553a664714e707ae9a511ada8021278be54ebbb6a1",
                        vOut = 1,
                        amount = 28727,
                        privateKeyHex = privateKeyHex,
                        secondMultisigPublicKeyHex = "0210056f186fe48c2fa1125c86357b1e7bda79bac5acf1f36142f31dd22dbf2916"
                    ),
                    UtxoData(
                        txId = "0986139a59a317c50fe6303f2eea880427207ea82d6a3833946fd941e394a755",
                        vOut = 0,
                        amount = 89429,
                        privateKeyHex = privateKeyHex,
                        secondMultisigPublicKeyHex = "0280df08018085fb321dba8566f80b255d05ddb08068aa11c779c18c0c24730588"
                    )
                ),
                outputs = listOf(
                    TxOutputData(
                        address = "3PpHfQGUnkPgWh7R9Tg4SKkgV5PF6XsUcE",
                        amount = 283553
                    )
                )
            )

        Assert.assertEquals(
            "01000000000103c61081ecfdf8916171830f56aeab60fe42bbbac43f70650599f6cbb49556c086010000002322002082649e503ddc696b4a353cdf6c8a73281af0f5e3bba720a1db21f0f0fcc1707fffffffffa1b6bb4ee58b272180da1a519aae07e71447663a55cf9ffe935df0e32b30ead301000000232200207052bd8594da5422d072a674f94cbe37152aa16cfb754c20ba998ffe8322049bffffffff55a794e341d96f9433386a2da87e20270488ea2e3f30e60fc517a3599a13860900000000232200200c720fa826b2b9410331bb034764c98b645f2e499fb62325a4a36a76689f0be0ffffffff01a15304000000000017a914f2b2d1327e9265d616c5f7a7c2b7998cb67eaac58703004830450221009593fc37fa76acf035b2020a02ecb3c55bea7d7f47b127fcec7ccac1a4d33ce102207a50f7f238f59bce56468703e2811684576dcafaa1e0e031ec764298ac904c2601475221029c4579f8dfaff0e292cb7443ead2c23ac92df70c8f618f965de3f45b281f460a210348f2863b49419fbec7bba412f363cb31e01b55cc97c3b6b93b3f14e897f37a6852ae030047304402206cc646a72c4bfb6869c512a68397f0a08414d07691d8bf13a8204a36e21e39990220069c9649131b82a796fa593230c2a23073732e693d8c34f59347e00e9c06d508014752210210056f186fe48c2fa1125c86357b1e7bda79bac5acf1f36142f31dd22dbf2916210348f2863b49419fbec7bba412f363cb31e01b55cc97c3b6b93b3f14e897f37a6852ae0300483045022100afb68b90186326f497ceff1997d1961a738b0fcf6e9699a5c6708b13162dca0b02200634f720e20af3845b7af1a32dc0779c4e6d42856324817272cc5dccee7a3098014752210280df08018085fb321dba8566f80b255d05ddb08068aa11c779c18c0c24730588210348f2863b49419fbec7bba412f363cb31e01b55cc97c3b6b93b3f14e897f37a6852ae00000000",
            result
        )
    }
}