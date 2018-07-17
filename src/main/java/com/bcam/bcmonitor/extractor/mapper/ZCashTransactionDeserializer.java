package com.bcam.bcmonitor.extractor.mapper;

import com.bcam.bcmonitor.model.TransactionOutput;
import com.bcam.bcmonitor.model.ZCashTransaction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;

import static com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer.readInputs;

public class ZCashTransactionDeserializer extends BlockchainDeserializer<ZCashTransaction> {

    // public static ArrayList<TransactionInput> readInputs(JsonNode result){
    //     ArrayList<TransactionInput> vin = new ArrayList<>();
    //
    //     try {
    //         result.get("vin").forEach(jsonNode -> {
    //
    //             TransactionInput in = new TransactionInput();
    //             in.setTxid(jsonNode.get("txid").textValue());
    //             in.setVout(jsonNode.get("vout").intValue());
    //             vin.add(in);
    //         });
    //
    //     } catch (NullPointerException e) {
    //         // if we can't find any inputs this must be a coinbase transaction
    //         assert !result.get("vin").get(0).get("coinbase").asText().isEmpty();
    //     }
    //
    //     return vin;
    // }

    @Override
    public ZCashTransaction deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        ZCashTransaction transaction = new ZCashTransaction();

        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        verifyResponse(node);

        JsonNode result = node.get("result");

        transaction.setHash(result.get("txid").asText());  // fine where "hash" and "txid" are same (change for segwit)
        transaction.setSizeBytes(result.get("size").asInt());
        transaction.setBlockHash(result.get("blockhash").asText());


        transaction.setVin(readInputs(result));
        // // get inputs
        // try {
        //     ArrayList<TransactionInput> vin = new ArrayList<>();
        //
        //     result.get("vin").forEach(jsonNode -> {
        //
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
        //     assert !result.get("vin").get(0).get("coinbase").asText().isEmpty();
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

        return transaction;
    }

}


