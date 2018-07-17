package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.TransactionInput;
import com.bcam.bcmonitor.model.TransactionOutput;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;

public class BitcoinTransactionDeserializer extends StdDeserializer<BitcoinTransaction> {


    public BitcoinTransactionDeserializer() {
        this(null);
    }


    public BitcoinTransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    public static ArrayList<TransactionInput> readInputs(JsonNode result){
        ArrayList<TransactionInput> vin = new ArrayList<>();

        try {
            result.get("vin").forEach(jsonNode -> {

                TransactionInput in = new TransactionInput();
                in.setTxid(jsonNode.get("txid").textValue());
                in.setVout(jsonNode.get("vout").intValue());
                vin.add(in);
            });

        } catch (NullPointerException e) {
            // if we can't find any inputs this must be a coinbase transaction
            assert !result.get("vin").get(0).get("coinbase").asText().isEmpty();
        }

        return vin;
    }

    @Override
    public BitcoinTransaction deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        BitcoinTransaction transaction = new BitcoinTransaction();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        if (! node.get("error").isNull()) {
            throw new RPCResponseException("Error received from RPC. Message: " + node.get("error").get("message").textValue());
        }

        else {
            JsonNode result = node.get("result");

            transaction.setHash(result.get("txid").asText());  // fine where "hash" and "txid" are same (change for segwit)
            transaction.setSizeBytes(result.get("size").asInt());
            transaction.setBlockHash(result.get("blockhash").asText());

            transaction.setVin(readInputs(result));

            // // get inputs
            // try {
            //
            //     ArrayList<TransactionInput> vin = new ArrayList<>();
            //
            //     result.get("vin").forEach(jsonNode -> {
            //         System.out.println("THROUGH VIN");
            //         TransactionInput in = new TransactionInput();
            //         in.setTxid(jsonNode.get("txid").textValue());
            //         in.setVout(jsonNode.get("vout").intValue());
            //         vin.add(in);
            //     });
            //
            //     transaction.setVin(vin);
            //
            // } catch (NullPointerException e) {
            //     // if we can't find any inputs this must be a coinbase transaction
            //     assert ! result.get("vin").get(0).get("coinbase").asText().isEmpty();
            // }


            // get outputs
            ArrayList<TransactionOutput> vout = new ArrayList<>();

            result.get("vout").forEach(jsonNode -> {
                // System.out.println("THROUGH VOUT");
                TransactionOutput out = new TransactionOutput();
                out.setValue(jsonNode.get("value").floatValue());
                out.setIndex(jsonNode.get("n").intValue());
                vout.add(out);
                // System.out.println("SET part VOUT");

            });

            // System.out.println("ADDING VOUT");

            transaction.setVout(vout);

            // System.out.println("ADDED VOUT");

        }


        //  ArrayList<BitcoinTransactionInput> vin = mapper.readValue(node.get("vin").asText(), new TypeReference<ArrayList<BitcoinTransactionInput>>(){});
        //
        //  ArrayList<BitcoinTransactionOutput> vout;
        //
        //
        // List<> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});

        return transaction;
    }

    /**
     * alternative:
     * create a dto
     * https://stackoverflow.com/questions/20832015/how-do-i-iterate-over-a-json-response-using-jackson-api-of-a-list-inside-a-list
     *
     * could be a private static class json inside this class
     *
     *https://stackoverflow.com/questions/19158345/custom-json-deserialization-with-jackson
     */

}
