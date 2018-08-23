package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.EthereumTransaction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;



public class EthereumTransactionDeserializer extends BlockchainDeserializer<EthereumTransaction> {

        @Override
        public EthereumTransaction deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
            EthereumTransaction transaction = new EthereumTransaction();

            ObjectCodec codec = parser.getCodec();
            JsonNode result = codec.readTree(parser);

            JsonNode node = result.path("result");

            // general
            transaction.setHash(node.get("hash").asText());
            transaction.setBlockHash(node.get("blockHash").asText());

            transaction.setTimeReceived(new java.util.Date(System.currentTimeMillis()).toInstant().getEpochSecond());

            // eth
            transaction.setTo(node.get("to").asText());
            transaction.setFrom(node.get("from").asText());
            transaction.setGas(hexNodeToLong(node.get("gas")));
            transaction.setGasPrice(hexNodeToLong(node.get("gasPrice")));
            transaction.setValue(hexNodeToLong(node.get("value")));


            return transaction;
        }
}


