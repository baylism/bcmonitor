package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.TransactionPoolInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class BitcoinTransactionPoolInfoDeserializer extends BlockchainDeserializer<TransactionPoolInfo> {


    @Override
    public TransactionPoolInfo deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        TransactionPoolInfo info = new TransactionPoolInfo();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        verifyResponse(node);

        JsonNode result = node.get("result");

        info.setMemoryUsage(result.get("usage").asLong());
        // info.setMinFeePerKB(result.get("mempoolminfee").floatValue());
        info.setSize(result.get("size").asInt());
        info.setSizeBytes(result.get("bytes").asInt());

        return info;
    }
}
