package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.mapper.BitcoinBlockDeserializer;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class ReactiveBitcoinClient {

    private String userName;
    private String password;
    private String hostName;
    private int port;

    protected ReactiveHTTPClient client;

    protected ObjectMapper mapper;


    public ReactiveBitcoinClient(String userName, String password, String hostName, int port) {
        this.userName = userName;
        this.password = password;
        this.hostName = hostName;
        this.port = port;

        client = new ReactiveHTTPClient(hostName, port, userName, password);
        mapper = new ObjectMapper();
        addDeserializers();
    }

    public ReactiveBitcoinClient() {
        // TODO add properties config file http://www.baeldung.com/properties-with-spring
        userName = "bitcoinrpc";
        password = "123";
        hostName = "localhost";
        port = 28332;
    }

    private void addDeserializers() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        mapper.registerModule(module);
    }

    /**
     * Return a block with hash
     * @param hash of the block to be requested
     * @return a Bitcoin block deserialized as a BitcoinBlock object
     * @throws IOException
     */
    // public Mono<BitcoinBlock> getBlock(String hash) throws IOException {
    //     JSONRPCRequest request = new JSONRPCRequest("getblock");
    //     request.addParam(hash);
    //     request.addParam(2); // always request decoded JSON with transactions
    //
    //     System.out.println("making request");
    //
    //     Mono<BitcoinBlock> b = client.request(request.toString());
    //
    //     return b;
    //     // response.map(r -> System.out.println(r));
    //
    //     // return mapper.readValue(response.flatMap(), BitcoinBlock.class);
    // }


}

