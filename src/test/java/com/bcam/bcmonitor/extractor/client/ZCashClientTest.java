package com.bcam.bcmonitor.extractor.client;

class ZCashClientTest {
    private String shieldedTransaction = "{\n" +
            "   \"hash\":\"4f5006195e7f02519283f943da0f500360fd86129b5f9438744440fe03ccf613\",\n" +
            "   \"mainChain\":true,\n" +
            "   \"fee\":0.0001,\n" +
            "   \"type\":\"valueTransfer\",\n" +
            "   \"shielded\":true,\n" +
            "   \"index\":11,\n" +
            "   \"blockHash\":\"000000000376253c9e0dead5636dc1ee0ea6f71fe99e902af224c889fb6b4888\",\n" +
            "   \"blockHeight\":348885,\n" +
            "   \"version\":3,\n" +
            "   \"lockTime\":0,\n" +
            "   \"timestamp\":1530183891,\n" +
            "   \"time\":567,\n" +
            "   \"vin\":[\n" +
            "\n" +
            "   ],\n" +
            "   \"vout\":[\n" +
            "      {\n" +
            "         \"n\":0,\n" +
            "         \"scriptPubKey\":{\n" +
            "            \"addresses\":[\n" +
            "               \"t1aE77JRrq3DbPLLKu5GTibnqu1uXHUZmNP\"\n" +
            "            ],\n" +
            "            \"asm\":\"OP_DUP OP_HASH160 b362a028838c9578ccda950be55914b88860a9d8 OP_EQUALVERIFY OP_CHECKSIG\",\n" +
            "            \"hex\":\"76a914b362a028838c9578ccda950be55914b88860a9d888ac\",\n" +
            "            \"reqSigs\":1,\n" +
            "            \"type\":\"pubkeyhash\"\n" +
            "         },\n" +
            "         \"value\":20.03950785,\n" +
            "         \"valueZat\":2003950785\n" +
            "      }\n" +
            "   ],\n" +
            "   \"vjoinsplit\":[\n" +
            "      {\n" +
            "         \"anchor\":\"1aee2a20288bb23e8527d3de235c48cf153b77d58e4023eb89fc4f08ddf7f235\",\n" +
            "         \"ciphertexts\":[\n" +
            "            \"11ae871f744eb5872fe1cb62b81e4b761b1768305d3c3f284214d86a043d071253ada7c0387e7b939fe91d3591f7124ac85d0d83bf24073a5d384572d6fb368b64ab45eecab06d9eb6f463f7b384cbbe15874e973a49e700d7defdf713101cd3d3c29799a8c965db09832221dc08199fdba8056d15c30d621f33c253f86db6984a4ef66a4d924feb73f0259ed258f7c1789b650a9563bce2b8cfd2eeb134b2486772fb797eb5bf50af5e7e4a9d578c66e24bfcd09430894479256196a88592787c2bf56a47737a2fe2e379a4618afb85487d9880fe974ce511959ca589fbf21518c4482decb2db18d38b862207c428147f60c6c20d86c9ed61bc685dc3e2cfb64fa84421859528aa2100615e696a6da148d03bdb95a2a0dd4b7497d22840b4b551b17cb64ad19299e0d927b660d619be3744198a739f95142c38280a212792bd9f22113232eccfa75ec3812e68ca19703e7ac097e06dc00079c98ef1c1e07808b5742dfff80bebd43d7be0ab0b1420061b4ce36b0238eae280fb7789e5bdc1768a153b19a169e8a1592ed191c520b4018cd922c08cef0b8f0cde7da0465aa7a3df262a031347eac97a844d3b6127e97c96dd0267f9f6d6d287eb1f9818c6613f2e110cf05a9b923fba7926c7fb1633f9d6e2171b300bc8b240e8b647770fbfa212b91d6fd1b6f23a83151e48306c5be551bc55316d2bb2c35357c20a79ff24e49eb2bdb8d344def8d9114dbc675a03fb32275eed70b53cee82bdc55ee684d8218a2765868c7d77135cb3b004f8829bc9e5ff39cdb76d6d8890e484a264b1fce197c1a15a6fa5d96c8b59c98a00b23d4124bfcd1842a52a3108\",\n" +
            "            \"352431d2d75a18db3c8cdcccd2d8c1944d064aa31da75f8d4eb9327e79f32a49181f0b029aa3e4c384711044387f060915d3f681da3007f4067eeb4037c5b185baa3671805bd563dcea3352374d2fb8c6ab321f45036c0144f4cad4262e11b7c5d6a8148e5e2cd80c9b764a843930130a78014362ef616c978cbe22f47b4b5c07bc8891967fb3a74920ba320c4edd17b4266d3cda545abf83ae76108923a0dcf6d8612f3d6d8ee16604b0e5a3b869ee4c6d243dfb9411451a80dfe5021517d1b540d36fdb426dd17820929e73fc8f74cb72cbd3df398f60b93db03b63fa1f62bdd9c903eba4810ed06a7d731c1906f8614e78a94bf3974786bcfc720335031892c929a30f44003f56dfd081a9c6b4ba39a26fb47f80b85825ba4b39446854cebbbad5141c75bf790e87e5604df46640b559257bc5c0cbd48c2fab035174f08d078a6157252a7e9e99817d446fbffb36420daa8a48141f1fdc6ec12c5e428b0a8148ecfa276a819ec3c03a38e255e38d853529b152c069fd9cd1949689fe4f5c08d79e968b727e93dc23e8b40c5a4398b5aae9ae1f062dccbe9493fe55ff0871a420be4016f02d51e319bd18188dd8aa46d67dc56a573e01de439363fc7f49ecf703da10ac44268f665877ac5e66ed358336c72c33e6689d323ca9277485a99bfd9f7c4cd45773380decc5c20f2893e3ad422d691247cc94ccb5044b7382bb85e5c8d1b6a8855ce2ae463cde8279c7197b77e35a1b730fe8aa05cefc36cfca3783e14a23a4aab5f70cb5e78dc104fe0546cc3c488d0e71fc3e191c51ba6adde5bcac450a85e3f8cdee31f381a1c0eb2c9ce5373e81816c3b844\"\n" +
            "         ],\n" +
            "         \"commitments\":[\n" +
            "            \"448b55444f7a3da45818903cbb83cf9cdb19b52217d0554366118efd54702501\",\n" +
            "            \"9b0f3fd85f4a937832ffbad8a77d0a9c8823e69fe103653f4fb1c88810856edf\"\n" +
            "         ],\n" +
            "         \"macs\":[\n" +
            "            \"ea7c45db9bd2a51bf63ba067bbb8346a629c95dff2e6ef8a5c9b89c494b4e646\",\n" +
            "            \"399e913e3765a628c7fa074eb4e741485d180367fd2b79f580d02725bac97866\"\n" +
            "         ],\n" +
            "         \"nullifiers\":[\n" +
            "            \"a271ca229b20cd2d49b7e909329d74a6296a4bfab1f6830e3172b1c3b66f7197\",\n" +
            "            \"5437e38d35c0f6e6bc739df3106bebfe4096bc83daf252aa5677635ce6de4526\"\n" +
            "         ],\n" +
            "         \"onetimePubKey\":\"3639e1ac301434d7c05b8fc6bff5a8a100ae74be09525790c2f322f345026f2c\",\n" +
            "         \"proof\":\"020bbf0dd08d4899d9ee3ecb401970489013ceb294207fefd9e9134564b9ace6630323f076e14626fc2a1d50aa0fd48a326e8e459b4944113772e5e7ffe480d491940a02a001208cc1b3b748ae3e163a06ef98b6f16f64f5f4cd3ba6fe3dcb1f8d935b298791411735f633f69f989740e45ef73f989aa58f37b1267e90415960989e6e020f7db905b8c829099c12dd4d5e02311f351c0c939ec8778d4d4fa13e6671cb1e020a33957c608fedd8ad7f4c3a2a5524111b981a675baed78bd34097d295c4ff0b022f58aa392f29775f66c83d32e1c863b22032c8f3f6f6e74fb44ceee729c7b321032841b73a883ca3793b603fcd2d08842d077b4d039fecd0db193d0bd96e36f43803049f94e797598a61917e2d818f820c8d641b4bd3a265bb06af4aefb5c3b698c2\",\n" +
            "         \"randomSeed\":\"5bed39a967acf28437bba3ea45980879b3403e8d96e82feee8261bbba3da754d\",\n" +
            "         \"vpub_new\":20.03960785,\n" +
            "         \"vpub_old\":0\n" +
            "      }\n" +
            "   ],\n" +
            "   \"value\":20.03950785,\n" +
            "   \"outputValue\":20.03950785,\n" +
            "   \"shieldedValue\":20.03960785,\n" +
            "   \"overwintered\":true\n" +
            "}";

}