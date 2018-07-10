package com.bcam.bcmonitor.extractor.mapper;



import com.bcam.bcmonitor.model.BitcoinBlock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class DashBlockDeserializer extends BlockchainDeserializer<BitcoinBlock> {

    @Override
    public BitcoinBlock deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        BitcoinBlock block = new BitcoinBlock();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        System.out.println(node);

        if (! node.get("error").isNull()) {
            throw new RPCResponseException("Error received from RPC. Message: " + node.get("error").get("message").textValue());
        }

        else {
            JsonNode result = node.get("result");

            // abstract
            block.setHash(result.get("hash").asText());
            block.setDifficulty(BigDecimal.valueOf(result.get("difficulty").asLong()));
            block.setHeight(result.get("height").asInt());
            block.setSizeBytes(result.get("size").asInt());
            block.setTimeStamp(result.get("time").asLong());

            // Dash
            block.setConfirmations(result.get("confirmations").asInt());
            block.setMedianTime(result.get("mediantime").asLong());
            block.setDifficulty(new BigDecimal(result.get("difficulty").asText()));
            block.setChainWork(hexNodeToBigInt(result.get("chainwork")));
            // block.setNextBlockHash(result.get("nextblockhash").asText());

        }
        return block;
    }



}

