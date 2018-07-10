package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.TransactionPool;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class BitcoinTransactionPoolDeserializer extends BlockchainDeserializer<TransactionPool> {
    @Override
    public TransactionPool deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        TransactionPool pool = new TransactionPool();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        verifyResponse(node);

        JsonNode result = node.get("result");

        try {
            result.forEach(jsonNode -> pool.addTransaction(jsonNode.textValue()));
        } catch (NullPointerException e) {
            throw new RPCResponseException("Transaction pool empty?");
        }

        return pool;
    }
}
