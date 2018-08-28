package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.MoneroTransaction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;

import static com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer.readInputs;
import static com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer.readOutputs;

public class MoneroTransactionDeserializer extends StdDeserializer<MoneroTransaction> {


    public MoneroTransactionDeserializer() {
        this(null);
    }


    public MoneroTransactionDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public MoneroTransaction deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        MoneroTransaction transaction = new MoneroTransaction();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        JsonNode result = node.get("txs").get(0);

        transaction.setHash(result.get("tx_hash").asText());

        return transaction;
    }


}
