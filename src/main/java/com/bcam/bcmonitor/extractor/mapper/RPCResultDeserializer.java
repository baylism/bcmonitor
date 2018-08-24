package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.api.BitcoinController;
import com.bcam.bcmonitor.model.RPCResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RPCResultDeserializer extends BlockchainDeserializer<RPCResult> {

    private static final Logger logger = LoggerFactory.getLogger(RPCResultDeserializer.class);

    @Override
    public RPCResult deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException  {

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        verifyResponse(node);

        JsonNode result = node.get("result");
        // logger.info("RPCResult deserializer got " + result);


        RPCResult response = new RPCResult();

        // System.out.println("DECODING");

        try {
            response.setResponse(result.textValue());
            assert response.getResponse() != null;
        } catch (AssertionError e) {
            response.setResponse(result.toString());
        }

        // logger.info("Final response is " + response);

        return response;
    }
}
