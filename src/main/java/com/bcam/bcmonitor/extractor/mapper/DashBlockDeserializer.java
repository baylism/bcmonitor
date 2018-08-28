package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.DashBlock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class DashBlockDeserializer extends BlockchainDeserializer<DashBlock> {

    private static final Logger logger = LoggerFactory.getLogger(DashBlockDeserializer.class);

    @Override
    public DashBlock deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        DashBlock block = new DashBlock();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // System.out.println(node);

        if (!node.get("error").isNull()) {
            throw new RPCResponseException("Error received from RPC. Message: " + node.get("error").get("message").textValue());
        }


        JsonNode result = node.get("result");

        // abstract
        block.setHash(result.get("hash").asText());
        block.setDifficulty(BigDecimal.valueOf(result.get("difficulty").asLong()));
        block.setHeight(result.get("height").asInt());
        block.setSizeBytes(result.get("size").asInt());
        block.setTimeStamp(result.get("time").asLong());
        block.setMedianTime(result.get("mediantime").asLong());

        try {
            block.setPrevBlockHash(result.get("previousblockhash").asText());
        } catch (NullPointerException e) {
            logger.info("Block 0?" + block.getHeight() + " error: " + e);
        }

        block.setPrevBlockHash(result.get("previousblockhash").asText());

        block.setTimeReceived(new java.util.Date(System.currentTimeMillis()).toInstant().getEpochSecond());

        // for now, just add TXIDs
        ArrayList<String> txids = new ArrayList<>();
        result.get("tx").forEach(jsonNode -> txids.add(jsonNode.asText()));
        block.setTxids(txids);

        // bitcoin
        block.setConfirmations(result.get("confirmations").asInt());
        block.setDifficulty(new BigDecimal(result.get("difficulty").asText()));
        block.setChainWork(hexNodeToBigInt(result.get("chainwork")));


        return block;
    }


}

