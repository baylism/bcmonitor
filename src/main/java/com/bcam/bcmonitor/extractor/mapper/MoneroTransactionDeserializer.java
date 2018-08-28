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

        JsonNode result = node.get("result");

        transaction.setHash(result.get("txid").asText());  // fine where "hash" and "txid" are same (change for segwit)
        transaction.setSizeBytes(result.get("size").asInt());
        transaction.setBlockHash(result.get("blockhash").asText());

        transaction.setVin(readInputs(result));
        transaction.setVout(readOutputs(result));


        transaction.setTimeReceived(new Date(System.currentTimeMillis()).toInstant().getEpochSecond());


        return transaction;
    }


}
