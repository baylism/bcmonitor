package com.bcam.bcmonitor;

public class BitcoinRPCResponses {

    public static final String validBlockResponse = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"hash\": \"00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048\",\n" +
            "\t\t\"confirmations\": 527114,\n" +
            "\t\t\"strippedsize\": 215,\n" +
            "\t\t\"size\": 215,\n" +
            "\t\t\"weight\": 860,\n" +
            "\t\t\"height\": 1,\n" +
            "\t\t\"version\": 1,\n" +
            "\t\t\"versionHex\": \"00000001\",\n" +
            "\t\t\"merkleroot\": \"0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098\",\n" +
            "\t\t\"tx\": [\n" +
            "\t\t\t\"0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098\"\n" +
            "\t\t],\n" +
            "\t\t\"time\": 1231469665,\n" +
            "\t\t\"mediantime\": 1231469665,\n" +
            "\t\t\"nonce\": 2573394689,\n" +
            "\t\t\"bits\": \"1d00ffff\",\n" +
            "\t\t\"difficulty\": 1,\n" +
            "\t\t\"chainwork\": \"0000000000000000000000000000000000000000000000000000000200020002\",\n" +
            "\t\t\"previousblockhash\": \"000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f\",\n" +
            "\t\t\"nextblockhash\": \"000000006a625f06636b8bb6ac7b960a8d03705d1ace08b1a19da3fdcc99ddbd\"\n" +
            "\t},\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";

    public static final String errorBlockResponse = "{\n" +
            "\t\"result\": null,\n" +
            "\t\"error\": {\n" +
            "\t\t\"code\": -5,\n" +
            "\t\t\"message\": \"Block not found\"\n" +
            "\t},\n" +
            "\t\"id\": null\n" +
            "}";

    public static final String getBlockHashResponse = "{\n" +
            "\t\"result\": \"000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f\",\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";

    public static final String getTransactionResponse = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"txid\": \"4a4373359f0d2f423d40d9124b100a0917ded551351d7d90e79f5cef90404194\",\n" +
            "\t\t\"hash\": \"4a4373359f0d2f423d40d9124b100a0917ded551351d7d90e79f5cef90404194\",\n" +
            "\t\t\"version\": 1,\n" +
            "\t\t\"size\": 288,\n" +
            "\t\t\"vsize\": 288,\n" +
            "\t\t\"locktime\": 0,\n" +
            "\t\t\"vin\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"txid\": \"2a21805ff8cfc622a2a2eda95f13e64f427bfea64a9640d32bbbbb24cc800896\",\n" +
            "\t\t\t\t\"vout\": 92,\n" +
            "\t\t\t\t\"scriptSig\": {\n" +
            "\t\t\t\t\t\"asm\": \"30440220078f014fa2f66de9eb6eddd2dbfda03388e44fd88af8465a989cd5f0e2af855e022064d51636cc9caf1962df8bbcf40bfaeebe2b332c8a84d707c38d67483b4fa2ff[ALL] 04fcf07bb1222f7925f2b7cc15183a40443c578e62ea17100aa3b44ba66905c95d4980aec4cd2f6eb426d1b1ec45d76724f26901099416b9265b76ba67c8b0b73d\",\n" +
            "\t\t\t\t\t\"hex\": \"4730440220078f014fa2f66de9eb6eddd2dbfda03388e44fd88af8465a989cd5f0e2af855e022064d51636cc9caf1962df8bbcf40bfaeebe2b332c8a84d707c38d67483b4fa2ff014104fcf07bb1222f7925f2b7cc15183a40443c578e62ea17100aa3b44ba66905c95d4980aec4cd2f6eb426d1b1ec45d76724f26901099416b9265b76ba67c8b0b73d\"\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"sequence\": 4294967295\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"vout\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"value\": 0.00002184,\n" +
            "\t\t\t\t\"n\": 0,\n" +
            "\t\t\t\t\"scriptPubKey\": {\n" +
            "\t\t\t\t\t\"asm\": \"OP_DUP OP_HASH160 fa0692278afe508514b5ffee8fe5e97732ce0669 OP_EQUALVERIFY OP_CHECKSIG\",\n" +
            "\t\t\t\t\t\"hex\": \"76a914fa0692278afe508514b5ffee8fe5e97732ce066988ac\",\n" +
            "\t\t\t\t\t\"reqSigs\": 1,\n" +
            "\t\t\t\t\t\"type\": \"pubkeyhash\",\n" +
            "\t\t\t\t\t\"addresses\": [\n" +
            "\t\t\t\t\t\t\"1Po1oWkD2LmodfkBYiAktwh76vkF93LKnh\"\n" +
            "\t\t\t\t\t]\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"value\": 0.00000000,\n" +
            "\t\t\t\t\"n\": 1,\n" +
            "\t\t\t\t\"scriptPubKey\": {\n" +
            "\t\t\t\t\t\"asm\": \"OP_RETURN 6f6d6e69000000000000001f000003ce475689c0\",\n" +
            "\t\t\t\t\t\"hex\": \"6a146f6d6e69000000000000001f000003ce475689c0\",\n" +
            "\t\t\t\t\t\"type\": \"nulldata\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"value\": 0.00000546,\n" +
            "\t\t\t\t\"n\": 2,\n" +
            "\t\t\t\t\"scriptPubKey\": {\n" +
            "\t\t\t\t\t\"asm\": \"OP_DUP OP_HASH160 6471a5235fd52ac5ce87df5e509a61bfe2af47bc OP_EQUALVERIFY OP_CHECKSIG\",\n" +
            "\t\t\t\t\t\"hex\": \"76a9146471a5235fd52ac5ce87df5e509a61bfe2af47bc88ac\",\n" +
            "\t\t\t\t\t\"reqSigs\": 1,\n" +
            "\t\t\t\t\t\"type\": \"pubkeyhash\",\n" +
            "\t\t\t\t\t\"addresses\": [\n" +
            "\t\t\t\t\t\t\"1AA6iP6hrZfYiacfzb3VS5JoyKeZZBEYRW\"\n" +
            "\t\t\t\t\t]\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"hex\": \"0100000001960880cc24bbbb2bd340964aa6fe7b424fe6135fa9eda2a222c6cff85f80212a5c0000008a4730440220078f014fa2f66de9eb6eddd2dbfda03388e44fd88af8465a989cd5f0e2af855e022064d51636cc9caf1962df8bbcf40bfaeebe2b332c8a84d707c38d67483b4fa2ff014104fcf07bb1222f7925f2b7cc15183a40443c578e62ea17100aa3b44ba66905c95d4980aec4cd2f6eb426d1b1ec45d76724f26901099416b9265b76ba67c8b0b73dffffffff0388080000000000001976a914fa0692278afe508514b5ffee8fe5e97732ce066988ac0000000000000000166a146f6d6e69000000000000001f000003ce475689c022020000000000001976a9146471a5235fd52ac5ce87df5e509a61bfe2af47bc88ac00000000\",\n" +
            "\t\t\"blockhash\": \"000000000000000000285a375f9d33e1782f3cff9cb1d4cfe1faf1356eb998fb\",\n" +
            "\t\t\"confirmations\": 1,\n" +
            "\t\t\"time\": 1528836244,\n" +
            "\t\t\"blocktime\": 1528836244\n" +
            "\t},\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";

    public static final String getBestBlockHashResponse = "{\n" +
            "\t\"result\": \"00000000000000000024c244f9c7d1cc0e593a7a4aa31c1ee2ef35206934bfff\",\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";

    public static final String getTransactionPoolInfoResponse = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"size\": 13808,\n" +
            "\t\t\"bytes\": 10997650,\n" +
            "\t\t\"usage\": 34191136,\n" +
            "\t\t\"maxmempool\": 50000000,\n" +
            "\t\t\"mempoolminfee\": 0.00001000,\n" +
            "\t\t\"minrelaytxfee\": 0.00001000\n" +
            "\t},\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";

    public static final String getBlockchainInfoResponsePretty = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"chain\": \"main\",\n" +
            "\t\t\"blocks\": 531489,\n" +
            "\t\t\"headers\": 531489,\n" +
            "\t\t\"bestblockhash\": \"0000000000000000003092e0372f341f5e027e026612b79d24558211eb486909\",\n" +
            "\t\t\"difficulty\": 5363678461481.357,\n" +
            "\t\t\"mediantime\": 1531318259,\n" +
            "\t\t\"verificationprogress\": 0.9999863833408901,\n" +
            "\t\t\"initialblockdownload\": false,\n" +
            "\t\t\"chainwork\": \"00000000000000000000000000000000000000000253f0139546a883ac201446\",\n" +
            "\t\t\"size_on_disk\": 198571576396,\n" +
            "\t\t\"pruned\": false,\n" +
            "\t\t\"softforks\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"bip34\",\n" +
            "\t\t\t\t\"version\": 2,\n" +
            "\t\t\t\t\"reject\": {\n" +
            "\t\t\t\t\t\"status\": true\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"bip66\",\n" +
            "\t\t\t\t\"version\": 3,\n" +
            "\t\t\t\t\"reject\": {\n" +
            "\t\t\t\t\t\"status\": true\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": \"bip65\",\n" +
            "\t\t\t\t\"version\": 4,\n" +
            "\t\t\t\t\"reject\": {\n" +
            "\t\t\t\t\t\"status\": true\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t],\n" +
            "\t\t\"bip9_softforks\": {\n" +
            "\t\t\t\"csv\": {\n" +
            "\t\t\t\t\"status\": \"active\",\n" +
            "\t\t\t\t\"startTime\": 1462060800,\n" +
            "\t\t\t\t\"timeout\": 1493596800,\n" +
            "\t\t\t\t\"since\": 419328\n" +
            "\t\t\t},\n" +
            "\t\t\t\"segwit\": {\n" +
            "\t\t\t\t\"status\": \"active\",\n" +
            "\t\t\t\t\"startTime\": 1479168000,\n" +
            "\t\t\t\t\"timeout\": 1510704000,\n" +
            "\t\t\t\t\"since\": 481824\n" +
            "\t\t\t}\n" +
            "\t\t},\n" +
            "\t\t\"warnings\": \"\"\n" +
            "\t},\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";


    public static final String getBlockchainInfoResponse = "{\n" +
            "\t\"result\": {\"chain\": \"main\"},\n" +
            "\t\"error\": null,\n" +
            "\t\"id\": null\n" +
            "}";


    // from dash client
    public static final String getMempoolResponse = "{\n" +
            "    \"result\": [\"5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b\", \"22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9\", \"24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5\", \"4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c\"],\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";


}

