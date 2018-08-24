package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.api.ZCashController;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.ZCashBlock;
import com.bcam.bcmonitor.model.DashBlock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


public class ZCashBlockDeserializer extends BlockchainDeserializer<ZCashBlock> {

    private static final Logger logger = LoggerFactory.getLogger(ZCashBlockDeserializer.class);


    @Override
    public ZCashBlock deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        ZCashBlock block = new ZCashBlock();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        if (! node.get("error").isNull()) {
            throw new RPCResponseException("Error received from RPC. Message: " + node.get("error").get("message").textValue());
        }

        JsonNode result = node.get("result");

        // abstract
        block.setHash(result.get("hash").asText());
        block.setDifficulty(BigDecimal.valueOf(result.get("difficulty").asLong()));
        block.setHeight(result.get("height").asInt());
        block.setSizeBytes(result.get("size").asInt());
        block.setTimeStamp(result.get("time").asLong());
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
