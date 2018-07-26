package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.RPCResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class RPCResultDeserializer extends BlockchainDeserializer<RPCResult> {


    @Override
    public RPCResult deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException  {

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        verifyResponse(node);

        JsonNode result = node.get("result");

        RPCResult response = new RPCResult();

        System.out.println("DECODING");

        response.setResponse(result.toString());

        return response;
    }
}
