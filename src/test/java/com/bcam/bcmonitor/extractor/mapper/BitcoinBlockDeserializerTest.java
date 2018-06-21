package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.BitcoinBlock;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class BitcoinBlockDeserializerTest {
    // ObjectMapper mapper;
    // SimpleModule module;
    String json;


    @Before
    public void setUp() throws Exception {
        json = "{\n" +
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
    }

    @Test
    public void deserialize() throws IOException {
        SimpleModule module = new SimpleModule("CustomBitcoinDeserializer", new Version(1, 0, 0, null, null, null));
        ObjectMapper mapper = new ObjectMapper();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        mapper.registerModule(module);

        // BitcoinBlock b = mapper.readValue(new File("bitcoin_getblock.json"), BitcoinBlock.class);
        BitcoinBlock b = mapper.readValue(json, BitcoinBlock.class);
        assertEquals("00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048", b.getHash());
        assertEquals(527114, b.getConfirmations());
        assertEquals(215, b.getSizeBytes());
        assertEquals(1, b.getHeight());
        assertEquals(1231469665, b.getTimeStamp());
        assertEquals(BigDecimal.valueOf(1), b.getDifficulty());
        assertEquals("000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f", b.getPrevBlockHash());
        assertEquals("000000006a625f06636b8bb6ac7b960a8d03705d1ace08b1a19da3fdcc99ddbd", b.getNextBlockHash());
        assertEquals(BigInteger.valueOf(8590065666L), b.getChainWork());
        assertEquals(1231469665L, b.getMedianTime());
    }

}