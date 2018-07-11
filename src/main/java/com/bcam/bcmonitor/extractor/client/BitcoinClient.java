package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.mapper.BitcoinBlockDeserializer;
import com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer;
import com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionPoolInfoDeserializer;
import com.bcam.bcmonitor.extractor.mapper.SingleResultDeserializer;
import com.bcam.bcmonitor.extractor.rpc.HTTPClient;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.TransactionPoolInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class BitcoinClient {

    private String userName;
    private String password;
    private String hostName;
    private int port;

    protected HTTPClient client;

    protected ObjectMapper mapper;


    public BitcoinClient(String userName, String password, String hostName, int port) {
        this.userName = userName;
        this.password = password;
        this.hostName = hostName;
        this.port = port;

        client = new HTTPClient(hostName, port, userName, password);
        mapper = new ObjectMapper();
        addDeserializers();
    }

    public BitcoinClient() {
        // TODO add properties config file http://www.baeldung.com/properties-with-spring
        userName = "bitcoinrpc";
        password = "123";
        hostName = "localhost";
        port = 28332;
    }

    private void addDeserializers() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        module.addDeserializer(BitcoinTransaction.class, new BitcoinTransactionDeserializer());
        module.addDeserializer(TransactionPoolInfo.class, new BitcoinTransactionPoolInfoDeserializer());
        module.addDeserializer(String.class, new SingleResultDeserializer());
        mapper.registerModule(module);
    }

    /**
     * Return a block with hash
     * @param hash of the block to be requested
     * @return a Bitcoin block deserialized as a BitcoinBlock object
     * @throws IOException
     */
    public BitcoinBlock getBlock(String hash) throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);
        request.addParam(2); // always request decoded JSON with transactions

        String response = client.request(request.toString());

        return mapper.readValue(response, BitcoinBlock.class);
    }

    /**
     * Return the hash of a block at a given height
     * @return a block hash
     */
    public String getBlockHash(int height) throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getblockhash");
        request.addParam(height);

        String response = client.request(request.toString());

        return mapper.readValue(response, String.class);
    }

    /**
     * Return a transaction with hash
     * @param hash of the transaction to be requested
     * @return a deserialized BitcoinTransaction object
     */
    public BitcoinTransaction getTransaction(String hash) throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");
        request.addParam(hash);
        request.addParam(Boolean.TRUE);

        String response = client.request(request.toString());

        return mapper.readValue(response, BitcoinTransaction.class);
    }

    /**
     * Return the hash of the best block in the chain
     * @return a String represention of the hash
     */
    public String getBestBlockHash() throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getbestblockhash");

        String response = client.request(request.toString());

        return mapper.readValue(response, String.class);
    }


    /**
     * Strategy: get has of best block. Get prevBlockHash.
     * Or same in reverse.
     */
    public void getAllBlocks() {
        // TODO add method
    }

    public TransactionPoolInfo getTransactionPoolInfo() throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getmempoolinfo");

        String response = client.request(request.toString());

        return mapper.readValue(response, TransactionPoolInfo.class);
    }
}
