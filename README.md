# Bitcoin util

Bitcoinj-based utility for Bitcoin operations

## Transactions

Signing:
```kotlin
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
    
println(result)
```

Creation:
```kotlin
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
    
println(result)
```

## Keypairs

Creation:
```kotlin
val serialized = KeypairUtil.createKey()

println(serialized.publicKeyHex)
println(serialized.privateKeyHex)
```
