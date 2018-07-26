package com.bcam.bcmonitor.extractor.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class SingleResultDeserializer extends BlockchainDeserializer<String> {


    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {


        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // verifyResponse(node);

        return node.get("result").asText();
    }
}
