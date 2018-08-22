package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.EthereumBlock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class EthereumBlockDeserializer extends BlockchainDeserializer<EthereumBlock> {

    @Override
    public EthereumBlock deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        EthereumBlock block = new EthereumBlock();
        ObjectCodec codec = parser.getCodec();
        JsonNode result = codec.readTree(parser);

        JsonNode node = result.path("result");


        // abstracts
        block.setHash(node.get("hash").asText());
        block.setDifficulty(new BigDecimal(hexNodeToBigInt(node.get("difficulty")))); // TODO change this horrible conversion (difficulty is BD for compatibility with other chains, but eth returns in hex)
        // block.setHeight(node.get("height").asInt());
        block.setSizeBytes(hexNodeToLong(node.get("size")));
        block.setTimeStamp(hexNodeToLong(node.get("timestamp")));
        block.setPrevBlockHash(node.get("parentHash").textValue());

        block.setTimeReceived(new java.util.Date(System.currentTimeMillis()).toInstant().getEpochSecond());

        // eth
        block.setGasLimit(hexNodeToBigInt(node.get("gasLimit")));
        block.setGasUsed(hexNodeToBigInt(node.get("gasUsed")));

        return block;

    }

}
