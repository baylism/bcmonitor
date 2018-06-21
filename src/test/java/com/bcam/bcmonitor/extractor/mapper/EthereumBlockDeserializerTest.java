package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.EthereumBlock;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EthereumBlockDeserializerTest {
    private String json;

    @Before
    public void setUp() throws Exception {
        json = "{\n" +
                "\"id\":1,\n" +
                "\"jsonrpc\":\"2.0\",\n" +
                "\"result\": {\n" +
                "    \"number\": \"0x1b4\",\n" +
                "    \"hash\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n" +
                "    \"parentHash\": \"0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5\",\n" +
                "    \"nonce\": \"0xe04d296d2460cfb8472af2c5fd05b5a214109c25688d3704aed5484f9a7792f2\",\n" +
                "    \"sha3Uncles\": \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n" +
                "    \"logsBloom\": \"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\n" +
                "    \"transactionsRoot\": \"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\n" +
                "    \"stateRoot\": \"0xd5855eb08b3387c0af375e9cdb6acfc05eb8f519e419b874b6ff2ffda7ed1dff\",\n" +
                "    \"miner\": \"0x4e65fda2159562a496f9f3522f89122a3088497a\",\n" +
                "    \"difficulty\": \"0x027f07\",\n" +
                "    \"totalDifficulty\":  \"0x027f07\",\n" +
                "    \"extraData\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n" +
                "    \"size\":  \"0x027f07\",\n" +
                "    \"gasLimit\": \"0x9f759\",\n" +
                "    \"gasUsed\": \"0x9f759\",\n" +
                "    \"timestamp\": \"0x54e34e8e\"\n" +
                // "    \"uncles\": [\"0x1606e5...\", \"0xd5145a9...\"]\n" +
                "  }\n" +
                "}\n";
    }

    @Test
    public void deserialize() throws IOException {
        SimpleModule module = new SimpleModule("CustomBitcoinDeserializer", new Version(1, 0, 0, null, null, null));
        ObjectMapper mapper = new ObjectMapper();
        module.addDeserializer(EthereumBlock.class, new EthereumBlockDeserializer());
        mapper.registerModule(module);

        EthereumBlock b = mapper.readValue(json, EthereumBlock.class);

        // abstract
        assertEquals("0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331", b.getHash());
        assertEquals(163591, b.getSizeBytes());
        assertEquals(1424182926, b.getTimeStamp());
        assertEquals(BigDecimal.valueOf(163591), b.getDifficulty());
        assertEquals("0x9646252be9520f6e71339a8df9c55e4d7619deeb018d2a3f2d21fc165dde5eb5", b.getPrevBlockHash());

        // eth
        assertEquals(BigInteger.valueOf(653145), b.getGasLimit());
        assertEquals(BigInteger.valueOf(653145), b.getGasUsed());
    }
}