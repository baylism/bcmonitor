package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.BitcoinBlockDeserializer;
import com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class ReactiveBitcoinClient {

    private final ObjectMapper mapper;

    private final String hostName;
    private final int port;
    private final String userName;
    private final String password;

    protected ReactiveHTTPClient client;

    public ReactiveBitcoinClient() {
        hostName = "localhost";
        // port = 9998;
        port = 5000;
        userName = "dashuser1";
        password = "password";

        mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        module.addDeserializer(BitcoinTransaction.class, new BitcoinTransactionDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

    // parameterised queries
    public Mono<BitcoinBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);
        request.addParam(2); // always request decoded JSON with transactions

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(BitcoinBlock.class);
    }

    public Mono<BitcoinBlock> getBlockHash(int height) {
        JSONRPCRequest request = new JSONRPCRequest("getblockhash");
        request.addParam(height);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(BitcoinBlock.class);
    }


    public Mono<BitcoinTransaction> getTransaction(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");

        request.addParam(hash);
        request.addParam(Boolean.TRUE);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(BitcoinTransaction.class);
    }

    // single queries
    public Mono<String> getBlockchainInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");

        return client.requestString(request.toString());
    }

    public Mono<String> getMempoolInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getmempoolinfo");

        return client.requestString(request.toString());
    }

    public Mono<String> getBestBlockHash() {
        JSONRPCRequest request = new JSONRPCRequest("getbestblockhash");

        return client.requestString(request.toString());
    }
}

