package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.MoneroBlock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


public class MoneroBlockDeserializer extends BlockchainDeserializer<MoneroBlock> {

    private static final Logger logger = LoggerFactory.getLogger(MoneroBlockDeserializer.class);


    @Override
    public MoneroBlock deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        MoneroBlock block = new MoneroBlock();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        JsonNode result = node.get("result");

        JsonNode header = result.get("block_header");


        // header
        block.setHash(header.get("hash").asText());
        block.setDifficulty(BigDecimal.valueOf(header.get("difficulty").asLong()));
        block.setHeight(header.get("height").asInt());
        block.setSizeBytes(header.get("block_size").asInt());
        block.setTimeStamp(header.get("timestamp").asLong());

        block.setDifficulty(new BigDecimal(header.get("difficulty").asText()));
        // block.setChainWork(hexNodeToBigInt(result.get("chainwork")));


        // block.setMedianTime(header.get("mediantime").asLong()); TODO

        try {
            block.setPrevBlockHash(header.get("prev_hash").asText());
        } catch (NullPointerException e) {
            logger.info("Block 0?" + block.getHeight() + " error: " + e);
        }

        block.setTimeReceived(new java.util.Date(System.currentTimeMillis()).toInstant().getEpochSecond());


        // internal JSON
        String json =  result.get("json").asText();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode internalJson = mapper.readTree(json);

        // logger.info("JSON STRING " + internalJson);

        // for now, just add TXIDs
        ArrayList<String> txids = new ArrayList<>();
        internalJson.get("tx_hashes").forEach(jsonNode -> txids.add(jsonNode.asText()));
        block.setTxids(txids);

        // TODO miner tx


        return block;
    }
}
