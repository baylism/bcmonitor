package com.bcam.bcmonitor;

public class DashRPCResponses {


        public static final String getTransactionResponse = "{\n" +
                "    \"result\": {\n" +
                "        \"hex\": \"0200000001e7eb4ddccb03980c4662c560fda119b2f635f60fbc132a495b2218470a6aa3b2010000006b483045022100e485c0b9f5b34c8a0d6e92060dd6726de3c03e295e1cf3cc4126f8064ef1314902200cf62de5698dc1ff838978e4341e05ca1b091eee596db4ab21226422d460055001210291a5f2ffb03b95ece88f5b806acdf04f9609ca5e5c165bfc3d0b57a842b62c86feffffff0270940000000000001976a914ef890e70f8fb56d70513fcb8efed0c429caed01688ac776b1600000000001976a914317c4cdf6b5e0a9a641466131375b4d0325da98b88ac1be00d00\",\n" +
                "        \"txid\": \"56b4880a119776d820347efb197a0cb040c30f54591a60fb9094a5ca660b364a\",\n" +
                "        \"size\": 226,\n" +
                "        \"version\": 2,\n" +
                "        \"locktime\": 909339,\n" +
                "        \"vin\": [{\n" +
                "            \"txid\": \"b2a36a0a4718225b492a13bc0ff635f6b219a1fd60c562460c9803cbdc4debe7\",\n" +
                "            \"vout\": 1,\n" +
                "            \"scriptSig\": {\n" +
                "                \"asm\": \"3045022100e485c0b9f5b34c8a0d6e92060dd6726de3c03e295e1cf3cc4126f8064ef1314902200cf62de5698dc1ff838978e4341e05ca1b091eee596db4ab21226422d4600550[ALL] 0291a5f2ffb03b95ece88f5b806acdf04f9609ca5e5c165bfc3d0b57a842b62c86\",\n" +
                "                \"hex\": \"483045022100e485c0b9f5b34c8a0d6e92060dd6726de3c03e295e1cf3cc4126f8064ef1314902200cf62de5698dc1ff838978e4341e05ca1b091eee596db4ab21226422d460055001210291a5f2ffb03b95ece88f5b806acdf04f9609ca5e5c165bfc3d0b57a842b62c86\"\n" +
                "            },\n" +
                "            \"sequence\": 4294967294\n" +
                "        }],\n" +
                "        \"vout\": [{\n" +
                "            \"value\": 0.00038000,\n" +
                "            \"valueSat\": 38000,\n" +
                "            \"n\": 0,\n" +
                "            \"scriptPubKey\": {\n" +
                "                \"asm\": \"OP_DUP OP_HASH160 ef890e70f8fb56d70513fcb8efed0c429caed016 OP_EQUALVERIFY OP_CHECKSIG\",\n" +
                "                \"hex\": \"76a914ef890e70f8fb56d70513fcb8efed0c429caed01688ac\",\n" +
                "                \"reqSigs\": 1,\n" +
                "                \"type\": \"pubkeyhash\",\n" +
                "                \"addresses\": [\"XxXPWwu6U3w7ZXhR51MdtbLRyGBWh2J9qA\"]\n" +
                "            }\n" +
                "        }, {\n" +
                "            \"value\": 0.01469303,\n" +
                "            \"valueSat\": 1469303,\n" +
                "            \"n\": 1,\n" +
                "            \"scriptPubKey\": {\n" +
                "                \"asm\": \"OP_DUP OP_HASH160 317c4cdf6b5e0a9a641466131375b4d0325da98b OP_EQUALVERIFY OP_CHECKSIG\",\n" +
                "                \"hex\": \"76a914317c4cdf6b5e0a9a641466131375b4d0325da98b88ac\",\n" +
                "                \"reqSigs\": 1,\n" +
                "                \"type\": \"pubkeyhash\",\n" +
                "                \"addresses\": [\"XfCVrBshHwRV2Gb4oaNymcc9tTetb2FfJV\"]\n" +
                "            }\n" +
                "        }],\n" +
                "        \"blockhash\": \"000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582\",\n" +
                "        \"height\": 909341,\n" +
                "        \"confirmations\": 3,\n" +
                "        \"time\": 1532526128,\n" +
                "        \"blocktime\": 1532526128\n" +
                "    },\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";


        public static final String getBlockResponse = "{\n" +
                "    \"result\": {\n" +
                "        \"hash\": \"000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582\",\n" +
                "        \"confirmations\": 3,\n" +
                "        \"size\": 1942,\n" +
                "        \"height\": 909341,\n" +
                "        \"version\": 536870916,\n" +
                "        \"versionHex\": \"20000004\",\n" +
                "        \"merkleroot\": \"51951e802a6bea4ab2c8e53ece6ac099c63bb3b40e5a536f9cfb1eacd238609c\",\n" +
                "        \"tx\": [\"d903e90e5745ca48a88c84d6f2e0431d49a27cfede8e6171c1df7ed6aa7747ed\", \"09c7ba9498aee4e662c914d9c806d759bf44957fe5f8d771d76e3f2405d28e80\", \"85cbbabe0adab7610c37341ec0f4615eea17e2f454f8c718bc1352224234a16d\", \"4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c\"],\n" +
                "        \"time\": 1532526128,\n" +
                "        \"mediantime\": 1532525560,\n" +
                "        \"nonce\": 1009414283,\n" +
                "        \"bits\": \"19536d13\",\n" +
                "        \"difficulty\": 51481529.69355331,\n" +
                "        \"chainwork\": \"000000000000000000000000000000000000000000000925a74ac9e4c48ca76c\",\n" +
                "        \"previousblockhash\": \"000000000000003e67c0f34b6689965bf77e5fed825ced45928f61c91b9a8e02\",\n" +
                "        \"nextblockhash\": \"000000000000000e4cccf3ef87318867de7e798bc30ac3e8e13c1a6ca3c26dc7\"\n" +
                "    },\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";


        // doesn't contain nextblockhash
        public static final String getGetLatestBlockResponse = "{\"result\":{\"hash\":\"00000000000000427a9048cbb95c484afc559c01bc42eef505e075e5ef05c93f\",\"confirmations\":2,\"size\":466,\"height\":909830,\"version\":536870912,\"versionHex\":\"20000000\",\"merkleroot\":\"3968bc2ff0bad53fa3c6decf5b8ead3ecdcc296592b791563a722ae960c1a5c8\",\"tx\":[\"809d92fcdec079782960449c15e759aeb6935f4a124f6bd699e104397b7a30b2\",\"25190c3e6b215542fa211d58bc483556b54b70e567d87b5b9e06c6bb16d79b6c\"],\"time\":1532602907,\"mediantime\":1532602325,\"nonce\":1677675824,\"bits\":\"194d5463\",\"difficulty\":55540178.42653183,\"chainwork\":\"00000000000000000000000000000000000000000000092c50fd0a6b7a9ce843\",\"previousblockhash\":\"0000000000000044bdf2189c36e92d6c6dc2f60f95a84d407e07b6e0456c6e7b\"},\"error\":null,\"id\":\"curltest\"}";


        public static final String getBlockchainInfoResponsePretty = "{\n" +
                "    \"result\": {\n" +
                "        \"chain\": \"main\",\n" +
                "        \"blocks\": 909341,\n" +
                "        \"headers\": 909341,\n" +
                "        \"bestblockhash\": \"000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582\",\n" +
                "        \"difficulty\": 51481529.69355331,\n" +
                "        \"mediantime\": 1532525560,\n" +
                "        \"verificationprogress\": 0.9999976439947849,\n" +
                "        \"chainwork\": \"000000000000000000000000000000000000000000000925a74ac9e4c48ca76c\",\n" +
                "        \"pruned\": false,\n" +
                "        \"softforks\": [{\n" +
                "            \"id\": \"bip34\",\n" +
                "            \"version\": 2,\n" +
                "            \"reject\": {\n" +
                "                \"status\": true\n" +
                "            }\n" +
                "        }, {\n" +
                "            \"id\": \"bip66\",\n" +
                "            \"version\": 3,\n" +
                "            \"reject\": {\n" +
                "                \"status\": true\n" +
                "            }\n" +
                "        }, {\n" +
                "            \"id\": \"bip65\",\n" +
                "            \"version\": 4,\n" +
                "            \"reject\": {\n" +
                "                \"status\": true\n" +
                "            }\n" +
                "        }],\n" +
                "        \"bip9_softforks\": {\n" +
                "            \"csv\": {\n" +
                "                \"status\": \"active\",\n" +
                "                \"startTime\": 1486252800,\n" +
                "                \"timeout\": 1517788800,\n" +
                "                \"since\": 622944\n" +
                "            },\n" +
                "            \"dip0001\": {\n" +
                "                \"status\": \"active\",\n" +
                "                \"startTime\": 1508025600,\n" +
                "                \"timeout\": 1539561600,\n" +
                "                \"since\": 782208\n" +
                "            },\n" +
                "            \"bip147\": {\n" +
                "                \"status\": \"started\",\n" +
                "                \"bit\": 2,\n" +
                "                \"startTime\": 1524477600,\n" +
                "                \"timeout\": 1556013600,\n" +
                "                \"since\": 858816\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";


        public static final String getBlockchainInfoResponse = "{\"result\":{\"chain\":\"main\",\"blocks\":909894,\"headers\":909894,\"bestblockhash\":\"000000000000001707165ebe701845fde191fd2c300e7a05c55107382398bfc4\",\"difficulty\":44463768.24481726,\"mediantime\":1532612865,\"verificationprogress\":0.9999992287526149,\"chainwork\":\"00000000000000000000000000000000000000000000092d21b401da81406d40\",\"pruned\":false,\"softforks\":[{\"id\":\"bip34\",\"version\":2,\"reject\":{\"status\":true}},{\"id\":\"bip66\",\"version\":3,\"reject\":{\"status\":true}},{\"id\":\"bip65\",\"version\":4,\"reject\":{\"status\":true}}],\"bip9_softforks\":{\"csv\":{\"status\":\"active\",\"startTime\":1486252800,\"timeout\":1517788800,\"since\":622944},\"dip0001\":{\"status\":\"active\",\"startTime\":1508025600,\"timeout\":1539561600,\"since\":782208},\"bip147\":{\"status\":\"started\",\"bit\":2,\"startTime\":1524477600,\"timeout\":1556013600,\"since\":858816}}},\"error\":null,\"id\":\"curltest\"}";

        public static final String getBestBlockHashResponse = "{\n" +
                "    \"result\": \"000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582\",\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";


        public static final String getBlockHashResppnse = "{\n" +
                "    \"result\": \"000007d91d1254d60e2dd1ae580383070a4ddffa4c64c2eeb4a2f9ecc0414343\",\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";


        public static final String getMempoolResponse = "{\n" +
                "    \"result\": [\"5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b\", \"22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9\", \"24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5\", \"4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c\"],\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";


        public static final String getGetMempoolInfoResponse = "{\n" +
                "    \"result\": {\n" +
                "        \"size\": 29,\n" +
                "        \"bytes\": 20509,\n" +
                "        \"usage\": 56000,\n" +
                "        \"maxmempool\": 300000000,\n" +
                "        \"mempoolminfee\": 0.00000000\n" +
                "    },\n" +
                "    \"error\": null,\n" +
                "    \"id\": \"curltest\"\n" +
                "}";



}
