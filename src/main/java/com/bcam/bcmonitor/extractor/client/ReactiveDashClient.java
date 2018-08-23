package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.*;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

// @Qualifier("ReactiveDashClient")
@Component
public class ReactiveDashClient extends ReactiveClientImpl implements ReactiveClient<DashBlock, DashTransaction>, BlockchainInfoClient {


    @Value("${DASH_HOSTNAME}")
    private String hostName;

    @Value("${DASH_PORT}")
    private int port;

    @Value("${DASH_UN}")
    private String userName;

    @Value("${DASH_PW}")
    private String password;

    public ReactiveDashClient() {
        super();
    }

    @PostConstruct
    protected void buildClient() {
        System.out.println("Building Dash client with hostname " + hostName);

        ObjectMapper mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

    @Override
    ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // module.addDeserializer(String.class, new SingleResultDeserializer());
        module.addDeserializer(TransactionPoolInfo.class, new BitcoinTransactionPoolInfoDeserializer());
        module.addDeserializer(TransactionPool.class, new BitcoinTransactionPoolDeserializer());
        module.addDeserializer(BlockchainInfo.class, new BlockchainInfoDeserializer());
        module.addDeserializer(RPCResult.class, new RPCResultDeserializer());

        // Dash
        module.addDeserializer(DashBlock.class, new DashBlockDeserializer());
        module.addDeserializer(DashTransaction.class, new DashTransactionDeserializer());

        mapper.registerModule(module);

        return mapper;
    }

    @Override
    ReactiveHTTPClient getClient() {
        return client;
    }

    @Override
    public Mono<DashBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);
        request.addParam(Boolean.TRUE);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(DashBlock.class);
    }


    @Override
    public Mono<DashTransaction> getTransaction(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");
        request.addParam(hash);
        request.addParam(Boolean.TRUE);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(DashTransaction.class);
    }

}

