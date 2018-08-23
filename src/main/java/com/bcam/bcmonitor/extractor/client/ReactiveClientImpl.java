package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.*;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public abstract class ReactiveClientImpl {

    protected ReactiveHTTPClient client;

    abstract ObjectMapper buildMapper();

    abstract ReactiveHTTPClient getClient();

    // other objects
    public Mono<TransactionPool> getTransactionPool() {
        JSONRPCRequest request = new JSONRPCRequest("getrawmempool");

        return getClient()
                .requestResponseSpec(request.toString())
                .bodyToMono(TransactionPool.class);
    }

    public Mono<TransactionPoolInfo> getTransactionPoolInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getmempoolinfo");

        return getClient()
                .requestResponseSpec(request.toString())
                .bodyToMono(TransactionPoolInfo.class);
    }

    // alt: get RPCresult; get string; map to object
    public Mono<BlockchainInfo> getBlockchainInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");

        BlockchainInfo info = new BlockchainInfo();
        info.setBlocks(0L);

        throw new RuntimeException("CALLED REAL CLIENT");

        // return Mono.just(info);
        // return getClient()
        //         .requestResponseSpec(request.toString())
        //         .bodyToMono(BlockchainInfo.class);
    }


    public Mono<String> getBestBlockHash() {
        JSONRPCRequest request = new JSONRPCRequest("getbestblockhash");

        return getClient().requestString(request.toString());
    }


    public Mono<String> getBlockHash(long height) {
        System.out.println("RBC Getting hash");
        JSONRPCRequest request = new JSONRPCRequest("getblockhash");

        request.addParam(height);

        return getClient().requestString(request.toString());
    }

    // getClient() provided requests
    public Mono<String> getRawResponse(String jsonQuery) {

        return getClient().requestString(jsonQuery);
    }

    public Mono<String> getCustomResponse(String methodName) {
        JSONRPCRequest request = new JSONRPCRequest(methodName);

        return getClient().requestString(request.toString());
    }

    public Mono<String> getCustomResponse(String methodName, String param) {
        JSONRPCRequest request = new JSONRPCRequest(methodName);

        request.addParam(param);

        return getClient().requestString(request.toString());
    }

    public Mono<String> getCustomResponse(String methodName, int param) {
        JSONRPCRequest request = new JSONRPCRequest(methodName);

        request.addParam(param);

        return getClient().requestString(request.toString());
    }

    public Mono<String> getCustomResponse(String methodName, String param, String param2) {
        JSONRPCRequest request = new JSONRPCRequest(methodName);

        request.addParam(param);
        request.addParam(param2);

        return getClient().requestString(request.toString());
    }

    public Mono<String> getCustomResponse(String methodName, String param, int param2) {
        JSONRPCRequest request = new JSONRPCRequest(methodName);

        request.addParam(param);
        request.addParam(param2);

        return getClient().requestString(request.toString());
    }

}

