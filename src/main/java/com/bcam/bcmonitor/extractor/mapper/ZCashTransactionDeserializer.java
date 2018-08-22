package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.ZCashTransaction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import static com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer.readInputs;
import static com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer.readOutputs;

public class ZCashTransactionDeserializer extends BlockchainDeserializer<ZCashTransaction> {


    @Override
    public ZCashTransaction deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        ZCashTransaction transaction = new ZCashTransaction();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        verifyResponse(node);

        JsonNode result = node.get("result");

        transaction.setTimeReceived(new java.util.Date(System.currentTimeMillis()).toInstant().getEpochSecond());

        // Bitcoin data
        transaction.setHash(result.get("txid").asText());  // fine where "hash" and "txid" are same (change for segwit)
        // transaction.setSizeBytes(result.get("size").asInt()); TODO zcash transaction size
        transaction.setBlockHash(result.get("blockhash").asText());
        transaction.setVin(readInputs(result));
        transaction.setVout(readOutputs(result));


        return transaction;
    }

}


