package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.*;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;


@Component
@Primary
public class ReactiveBitcoinClient {

    @Value("${BITCOIN_HOSTNAME}")
    private String hostName;

    @Value("${BITCOIN_PORT}")
    private int port;

    @Value("${BITCOIN_UN}")
    private String userName;

    @Value("${BITCOIN_PW}")
    private String password;

    protected ReactiveHTTPClient client;

    public ReactiveBitcoinClient() {

    }

    @PostConstruct
    protected void buildClient() {
        System.out.println("Building Bitcoin client with hostname " + hostName);

        ObjectMapper mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

    protected ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // module.addDeserializer(String.class, new SingleResultDeserializer());
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        module.addDeserializer(BitcoinTransaction.class, new BitcoinTransactionDeserializer());
        module.addDeserializer(TransactionPoolInfo.class, new BitcoinTransactionPoolInfoDeserializer());
        module.addDeserializer(TransactionPool.class, new BitcoinTransactionPoolDeserializer());
        module.addDeserializer(RPCResult.class, new RPCResultDeserializer());

        mapper.registerModule(module);

        return mapper;
    }

    // parameterised queries
    public Mono<BitcoinBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);
        request.addParam(2); // always request decoded JSON with transactions

        System.out.println(request);

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

    // basic queries
    public Mono<TransactionPool> getTransactionPool() {
        JSONRPCRequest request = new JSONRPCRequest("getrawmempool");

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(TransactionPool.class);
    }

    public Mono<TransactionPoolInfo> getTransactionPoolInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getmempoolinfo");

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(TransactionPoolInfo.class);
    }

    // public Mono<RPCResult> getBlockchainInfo() {
    //     JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");
    //
    //     return client
    //             .requestResponseSpec(request.toString())
    //             .bodyToMono(RPCResult.class);
    // }

    public Mono<String> getBlockchainInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(RPCResult.class)
                .map(RPCResult::toString);
    }

    // single string queries
    public Mono<String> getRawResponse(String jsonQuery) {

        return client.requestString(jsonQuery);
    }


    // public Mono<String> getBlockchainInfo() {
    //     JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");
    //
    //     return client.requestString(request.toString());
    // }


    // TODO fix string deserialisation so it uses SingleResultDeserialiser
    public Mono<String> getBestBlockHash() {
        JSONRPCRequest request = new JSONRPCRequest("getbestblockhash");

        return client.requestString(request.toString());
    }
}

