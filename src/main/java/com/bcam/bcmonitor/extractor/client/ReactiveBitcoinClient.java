package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.*;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.TransactionPool;
import com.bcam.bcmonitor.model.TransactionPoolInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Primary
public class ReactiveBitcoinClient {

    protected ReactiveHTTPClient client;

    public ReactiveBitcoinClient() {
        client = buildClient();
    }

    protected ReactiveHTTPClient buildClient() {
        String userName = "bitcoinrpc";
        String password = "123";
        String hostName = "localhost";
        int port = 9998;

        System.out.println("Creating a reactive bitcoin client on port " + port);

        ObjectMapper mapper = buildMapper();

        return new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

    protected ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(
                BitcoinBlock.class, new BitcoinBlockDeserializer());

        module.addDeserializer(
                BitcoinTransaction.class, new BitcoinTransactionDeserializer());

        module.addDeserializer(
                TransactionPoolInfo.class, new BitcoinTransactionPoolInfoDeserializer());

        module.addDeserializer(
                TransactionPool.class, new BitcoinTransactionPoolDeserializer());

        module.addDeserializer(
                String.class, new SingleResultDeserializer());

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

    // single string queries
    public Mono<String> getBlockchainInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");

        return client.requestString(request.toString());
    }


    // TODO fix string deserialisation so it uses SingleResultDeserialiser
    public Mono<String> getBestBlockHash() {
        JSONRPCRequest request = new JSONRPCRequest("getbestblockhash");

        return client.requestString(request.toString());
    }
}

