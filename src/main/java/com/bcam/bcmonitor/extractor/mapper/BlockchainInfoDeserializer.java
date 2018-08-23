package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.BlockchainInfo;
import com.bcam.bcmonitor.model.RPCResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class BlockchainInfoDeserializer extends BlockchainDeserializer<BlockchainInfo> {

    @Override
    public BlockchainInfo deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        verifyResponse(node);

        JsonNode result = node.get("result");

        BlockchainInfo response = new BlockchainInfo();

        response.setBlocks(result.get("blocks").asLong());
        response.setBestblockhash(result.get("bestblockhash").asText());

        // response.setMediantime(result.get("mediantime").asLong());


        return response;
    }
}
