package com.bcam.bcmonitor;

public class ZCashRPCResponses {

    public static final String getTransactionResponse = "{\n" +
            "    \"result\": {\n" +
            "        \"hex\": \"01000000010000000000000000000000000000000000000000000000000000000000000000ffffffff025100ffffffff0250c30000000000002321027a46eb513588b01b37ea24303f4b628afd12cc20df789fede0921e43cad3e875acd43000000000000017a9147d46a730d31f97b1930d3368a967c309bd4d136a8700000000\",\n" +
            "        \"txid\": \"851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609\",\n" +
            "        \"overwintered\": false,\n" +
            "        \"version\": 1,\n" +
            "        \"locktime\": 0,\n" +
            "        \"vin\": [{\n" +
            "            \"coinbase\": \"5100\",\n" +
            "            \"sequence\": 4294967295\n" +
            "        }],\n" +
            "        \"vout\": [{\n" +
            "            \"value\": 0.00050000,\n" +
            "            \"valueZat\": 50000,\n" +
            "            \"n\": 0,\n" +
            "            \"scriptPubKey\": {\n" +
            "                \"asm\": \"027a46eb513588b01b37ea24303f4b628afd12cc20df789fede0921e43cad3e875 OP_CHECKSIG\",\n" +
            "                \"hex\": \"21027a46eb513588b01b37ea24303f4b628afd12cc20df789fede0921e43cad3e875ac\",\n" +
            "                \"reqSigs\": 1,\n" +
            "                \"type\": \"pubkey\",\n" +
            "                \"addresses\": [\"t1KstPVzcNEK4ZeauQ6cogoqxQBMDSiRnGr\"]\n" +
            "            }\n" +
            "        }, {\n" +
            "            \"value\": 0.00012500,\n" +
            "            \"valueZat\": 12500,\n" +
            "            \"n\": 1,\n" +
            "            \"scriptPubKey\": {\n" +
            "                \"asm\": \"OP_HASH160 7d46a730d31f97b1930d3368a967c309bd4d136a OP_EQUAL\",\n" +
            "                \"hex\": \"a9147d46a730d31f97b1930d3368a967c309bd4d136a87\",\n" +
            "                \"reqSigs\": 1,\n" +
            "                \"type\": \"scripthash\",\n" +
            "                \"addresses\": [\"t3Vz22vK5z2LcKEdg16Yv4FFneEL1zg9ojd\"]\n" +
            "            }\n" +
            "        }],\n" +
            "        \"vjoinsplit\": [],\n" +
            "        \"blockhash\": \"0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283\",\n" +
            "        \"confirmations\": 4653,\n" +
            "        \"time\": 1477671596,\n" +
            "        \"blocktime\": 1477671596\n" +
            "    },\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";

    public static final String getGetBlockResponse_New = "{\"result\":{\"hash\":\"0000000000cc50281e133961f91dea67d6b4a9af68f5bf2bbf3504169dcd45b0\",\"confirmations\":31,\"size\":2637,\"height\":381560,\"version\":4,\"merkleroot\":\"823af238b71a9cc29d93dede4bf7fa0df7a8f665594552756b7565d6dc823e0f\",\"finalsaplingroot\":\"0000000000000000000000000000000000000000000000000000000000000000\",\"tx\":[\"1342c6415d7db3768618dac1912777d20a30e0aed8350094cca5db72367b2398\",\"32d1a2067bb6aefe7fcc9ba203122946316c91d790fb6ff3cbf206f5eea5c740\",\"d7358b29a186c7d47e72994ee3d8721aa97b3c1cddd69a129861786f4fa170fb\",\"801beb6971bba5170ee62bca7d3a47e11a89ec77f026ff0dac1b030e9e348787\",\"6451bef531a88a3ff067d115d701a4a666413fb734925b7d7a84ea4c7ef731a5\"],\"time\":1535108275,\"nonce\":\"a51b114600000000000000000000000000000000000000001058714f5326b720\",\"solution\":\"0144cd862fd4915f3f40f9587cdce836d33e3ec71418425fd24e6280cd4279428344f35d8329ccbf7478260d7cde99318bc1a62937b7acd81b3af58ad912f33ab7bce51777089df2c5059416ca1ef21f36d9bc170425be276356a6273c73eb735b7b84c2eb321d92f8172de58a22261923577db66e943a4a3a119532db79053d36a4bcd2cb13e91846fc943cc98a561e79859e0daad1217a6f0f7da26050e54a4e606211c8149c02050e4a0eb817f68d22dc030eeba43f4686b41cc38d1b2886c9d3b5f91dda2a327f26547f31c80d5c50100b328078c58ef47b00e982ff86adb0e75979db5a6d408424f70a6e8e75ba30548c1fdd4062e000d7272405cf503828af28c9a0da95980c7427be4ec9fa65c327d83f6e915e2a6d83ae4452ba5075713f0bb4d56b10c5c9da3a1930e95abeda9a59f3de5f29235ded9042a7df1f42558bc189fa9d89c0f9e21769a23c2464027ddabae66046479ada712740bfd94c8439b4024e2a1a3c42eef07e73ba7103c078779f7164b7f60c3f132ab7004ee8ec79dd2544c7662e11394c2fcb83cf397bcd09859b92931eacd798c161a89e94ee7a678a05d1beb5dd8185ecb3ec21c51cf2d43dccca56c28917b99716615b2f6dcac2949c59474039d24d54d76d05f0e659bed9e594ecb1518c7d543cbfa2f5fdc22a2d1e33ec4efb1b7bf8f9c37a39f9d1fe8858b5f8b4044969ef94c750170c3fc36b5cffcbc502344d94cb433ef74e3f372b3bf875d668214ec5ab64efbb3b3d088086f829a43da3b428c7af31e86a972adc3a0a4d1381de0f5119e5f50bbde38fc1d4d70167c455a5f009dc66437199e4a320cb92d0a24d62a55f8f104b432674ecb1c60bf74700c233f4c7efa7120782be3be10a7374147f459ed82f54133cbcbd88bf76147f08902d3b1b1c3858b32fec2ff4d25f5edd6a588a7a3c93017bcec1879ecbb3ea3670a049beea2c4752dc01030aa965665c5436dceb890345122f8d92f41dba331407ed65185653e9c3e3a155cf4a7603d371c21dd7cb32ee6fefe7d83338cb0fc8d18d56378e692afbf4e1021dd9cc8f50b363814b70c2b20744844c25b970291778afc62f97aab772acd21af29585b9f7f81965b107c99f4ae3356311bdef317856e5042cd6b2477592174b98cadbb6a227f69a230bb9d095d5ab2758f30f054a2cb381d000bf4e13a28e82575d75e267b345560c2510913ec9d331ec3a50f9aa5929207b665aa0360b92a1e9cd09ebdd827e50ced51b816513bb8c9258226b3593eb69c6475ce4626c5ece2654a2e708e6b90886c4a75c5449d3f8e8c68210bcb9c2e07bb812cf2ee3ab547e5b3ef348c515f1463994da1f6b166883222ab354afdb2b394e24b56342b437aad3ac7f3ce43446533fcc1b3236f87bc3a5cd5c900d6138ee0ca201c76517cb16ac65a28410fe658a5ff6f8ed38437b2035b5bb715149d2ddb60599a3d8568f46319a9aa7032439ae6797ca87625754157b275d2680b3baa1ec0e6f7467db475991a881d48004584f3707adb93e8b03fb23283c49374d4774e37b5bd84842b1ebfc41aa19a79b67672f0ff3afc6e3d8a0e0e51a943ede607b2174b64eb4e06d95a7dc36a935ed47228bb0f8e1913ba51d824097a9b6d188c640c14abd9dafe674c49a03828992ce6f2ab19c1d677fe367c1aaa769def81269a4759b0f3be45de2d8d7703d5dd6b1e4a09dcdef103d53d1becb6e927ea5644bb02f9159e35a35d7ff1825c59cce4d06e11ebd96d661f89c2b6a967e331b03892696bf073b04b5d605ae5b789467a3489de39307ae1c3dbfd1ccd798e041f746f6ced9a8382eeebe0995e6ae26869ced8062814045d54d40713f7b977d71e8ff8998ea0053c3b358d8e8e6496e65987bd663\",\"bits\":\"1c07ae48\",\"difficulty\":17474444.63094992,\"chainwork\":\"000000000000000000000000000000000000000000000000003af7def7cbb8e7\",\"anchor\":\"55a57425221c17f4edb1c908ae28db59cd96bbc7de96295f23a46305b669a335\",\"valuePools\":[{\"id\":\"sprout\",\"monitored\":true,\"chainValue\":335401.36071253,\"chainValueZat\":33540136071253,\"valueDelta\":0.00000000,\"valueDeltaZat\":0},{\"id\":\"sapling\",\"monitored\":true,\"chainValue\":0.00000000,\"chainValueZat\":0,\"valueDelta\":0.00000000,\"valueDeltaZat\":0}],\"previousblockhash\":\"00000000065b8793ffab9dfc0567bc368addb071e81a8f7161c379afdf63d9dd\",\"nextblockhash\":\"00000000076bd7c0c25c98b723c1636811afab10baa3bb2d3e006c000cbb5d8c\"},\"error\":null,\"id\":\"curltest\"}";

    public static final String getBlockResponse = "{\n" +
            "    \"result\": {\n" +
            "        \"hash\": \"0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283\",\n" +
            "        \"confirmations\": 5248,\n" +
            "        \"size\": 1617,\n" +
            "        \"height\": 1,\n" +
            "        \"version\": 4,\n" +
            "        \"merkleroot\": \"851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609\",\n" +
            "        \"finalsaplingroot\": \"0000000000000000000000000000000000000000000000000000000000000000\",\n" +
            "        \"tx\": [\"851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609\"],\n" +
            "        \"time\": 1477671596,\n" +
            "        \"nonce\": \"9057977ea6d4ae867decc96359fcf2db8cdebcbfb3bd549de4f21f16cfe83475\",\n" +
            "        \"solution\": \"002b2ee0d2f5d0c1ebf5a265b6f5b428f2fdc9aaea07078a6c5cab4f1bbfcd56489863deae6ea3fd8d3d0762e8e5295ff2670c9e90d8e8c68a54a40927e82a65e1d44ced20d835818e172d7b7f5ffe0245d0c3860a3f11af5658d68b6a7253b4684ffef5242fefa77a0bfc3437e8d94df9dc57510f5a128e676dd9ddf23f0ef75b460090f507499585541ab53a470c547ea02723d3a979930941157792c4362e42d3b9faca342a5c05a56909b046b5e92e2870fca7c932ae2c2fdd97d75b6e0ecb501701c1250246093c73efc5ec2838aeb80b59577741aa5ccdf4a631b79f70fc419e28714fa22108d991c29052b2f5f72294c355b57504369313470ecdd8e0ae97fc48e243a38c2ee7315bb05b7de9602047e97449c81e46746513221738dc729d7077a1771cea858865d85261e71e82003ccfbba2416358f023251206d6ef4c5596bc35b2b5bce3e9351798aa2c9904723034e5815c7512d260cc957df5db6adf9ed7272483312d1e68c60955a944e713355089876a704aef06359238f6de5a618f7bd0b4552ba72d05a6165e582f62d55ff2e1b76991971689ba3bee16a520fd85380a6e5a31de4dd4654d561101ce0ca390862d5774921eae2c284008692e9e08562144e8aa1f399a9d3fab0c4559c1f12bc945e626f7a89668613e8829767f4116ee9a4f832cf7c3ade3a7aba8cb04de39edd94d0d05093ed642adf9fbd9d373a80832ffd1c62034e4341546b3515f0e42e6d8570393c6754be5cdb7753b4709527d3f164aebf3d315934f7b3736a1b31052f6cc5699758950331163b3df05b9772e9bf99c8c77f8960e10a15edb06200106f45742d740c422c86b7e4f5a52d3732aa79ee54cfc92f76e03c268ae226477c19924e733caf95b8f350233a5312f4ed349d3ad76f032358f83a6d0d6f83b2a456742aad7f3e615fa72286300f0ea1c9793831ef3a5a4ae08640a6e32f53d1cba0be284b25e923d0d110ba227e54725632efcbbe17c05a9cde976504f6aece0c461b562cfae1b85d5f6782ee27b3e332ac0775f681682ce524b32889f1dc4231226f1aada0703beaf8d41732c9647a0a940a86f8a1be7f239c44fcaa7ed7a055506bdbe1df848f9e047226bee1b6d788a03f6e352eead99b419cfc41741942dbeb7a5c55788d5a3e636d8aab7b36b4db71d16700373bbc1cdeba8f9b1db10bf39a621bc737ea4f4e333698d6e09b51ac7a97fb6fd117ccad1d6b6b3a7451699d5bfe448650396d7b58867b3b0872be13ad0b43da267df0ad77025155f04e20c56d6a9befb3e9c7d23b82cbf3a534295ebda540682cc81be9273781b92519c858f9c25294fbacf75c3b3c15bda6d36de1c83336f93e96910dbdcb190d6ef123c98565ff6df1e903f57d4e4df167ba6b829d6d9713eb2126b0cf869940204137babcc6a1b7cb2f0b94318a7460e5d1a605c249bd2e72123ebad332332c18adcb285ed8874dbde084ebcd4f744465350d57110f037fffed1569d642c258749e65b0d13e117eaa37014a769b5ab479b7c77178880e77099f999abe712e543dbbf626ca9bcfddc42ff2f109d21c8bd464894e55ae504fdf81e1a7694180225da7dac8879abd1036cf26bb50532b8cf138b337a1a1bd1a43f8dd70b7399e2690c8e7a5a1fe099026b8f2a6f65fc0dbedda15ba65e0abd66c7176fb426980549892b4817de78e345a7aeab05744c3def4a2f283b4255b02c91c1af7354a368c67a11703c642a385c7453131ce3a78b24c5e22ab7e136a38498ce82082181884418cb4d6c2920f258a3ad20cfbe7104af1c6c6cb5e58bf29a9901721ad19c0a260cd09a3a772443a45aea4a5c439a95834ef5dc2e26343278947b7b796f796ae9bcadb29e2899a1d7313e6f7bfb6f8b\",\n" +
            "        \"bits\": \"1f07ffff\",\n" +
            "        \"difficulty\": 1,\n" +
            "        \"chainwork\": \"0000000000000000000000000000000000000000000000000000000000004000\",\n" +
            "        \"anchor\": \"59d2cde5e65c1414c32ba54f0fe4bdb3d67618125286e6a191317917c812c6d7\",\n" +
            "        \"valuePools\": [{\n" +
            "            \"id\": \"sprout\",\n" +
            "            \"monitored\": true,\n" +
            "            \"chainValue\": 0.00000000,\n" +
            "            \"chainValueZat\": 0,\n" +
            "            \"valueDelta\": 0.00000000,\n" +
            "            \"valueDeltaZat\": 0\n" +
            "        }, {\n" +
            "            \"id\": \"sapling\",\n" +
            "            \"monitored\": true,\n" +
            "            \"chainValue\": 0.00000000,\n" +
            "            \"chainValueZat\": 0,\n" +
            "            \"valueDelta\": 0.00000000,\n" +
            "            \"valueDeltaZat\": 0\n" +
            "        }],\n" +
            "        \"previousblockhash\": \"00040fe8ec8471911baa1db1266ea15dd06b4a8a5c453883c000b031973dce08\",\n" +
            "        \"nextblockhash\": \"0002a26c902619fc964443264feb16f1e3e2d71322fc53dcb81cc5d797e273ed\"\n" +
            "    },\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";


    public static final String getBlockchainInfoResponse = "{\"result\":{\"chain\":\"main\",\"blocks\":378358,\"headers\":378358,\"bestblockhash\":\"00000000008c9baaf66c73b434713306648da5653ab9afa659f970fa8619421c\",\"difficulty\":15571197.86188559,\"verificationprogress\":0.99999895770672,\"chainwork\":\"0000000000000000000000000000000000000000000000000039a96c9ee044d9\",\"pruned\":false,\"commitments\":1143594,\"valuePools\":[{\"id\":\"sprout\",\"monitored\":true,\"chainValue\":318187.34780341,\"chainValueZat\":31818734780341},{\"id\":\"sapling\",\"monitored\":true,\"chainValue\":0.00000000,\"chainValueZat\":0}],\"softforks\":[{\"id\":\"bip34\",\"version\":2,\"enforce\":{\"status\":true,\"found\":4000,\"required\":750,\"window\":4000},\"reject\":{\"status\":true,\"found\":4000,\"required\":950,\"window\":4000}},{\"id\":\"bip66\",\"version\":3,\"enforce\":{\"status\":true,\"found\":4000,\"required\":750,\"window\":4000},\"reject\":{\"status\":true,\"found\":4000,\"required\":950,\"window\":4000}},{\"id\":\"bip65\",\"version\":4,\"enforce\":{\"status\":true,\"found\":4000,\"required\":750,\"window\":4000},\"reject\":{\"status\":true,\"found\":4000,\"required\":950,\"window\":4000}}],\"upgrades\":{\"5ba81b19\":{\"name\":\"Overwinter\",\"activationheight\":347500,\"status\":\"active\",\"info\":\"See https://z.cash/upgrade/overwinter.html for details.\"}},\"consensus\":{\"chaintip\":\"5ba81b19\",\"nextblock\":\"5ba81b19\"}},\"error\":null,\"id\":\"curltest\"}";


    public static final String getBestBlockHashResponse = "{\n" +
            "    \"result\": \"00000000b0e31a8bb7bbcf902e7f854599e369984b9f85f2d2f1e3cfd9c0f265\",\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";


    public static final String getBlockHashResppnse = "{\n" +
            "    \"result\": \"0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283\",\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";


    public static final String getMempoolInfoResponse = "{\n" +
            "    \"result\": {\n" +
            "        \"size\": 0,\n" +
            "        \"bytes\": 0,\n" +
            "        \"usage\": 0\n" +
            "    },\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";

    public static final String getMempoolInfoResponse_New = "{\"result\":{\"size\":2,\"bytes\":13304,\"usage\":28960},\"error\":null,\"id\":\"curltest\"}";


    // from dash client
    public static final String getMempoolResponse = "{\n" +
            "    \"result\": [\"5bc76af67921c657c1c321de16a0403671365c6c07376a814b5de28c02ebe09b\", \"22121a969bd36559f38eb4d01850d044b34d5d75d912ba10969e4f64fd345be9\", \"24219415fc41d5205c455b3e1aef2b9323c3a2f5bc82eb2d08c1ea4336b3d3e5\", \"4607202d56516e4f10af26ada8f210c4802e195e8d43e5ef7f7470d2d0171c9c\"],\n" +
            "    \"error\": null,\n" +
            "    \"id\": \"curltest\"\n" +
            "}";

}
