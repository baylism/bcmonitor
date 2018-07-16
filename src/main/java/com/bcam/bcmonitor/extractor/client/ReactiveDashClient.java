package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.BitcoinTransactionDeserializer;
import com.bcam.bcmonitor.extractor.mapper.DashBlockDeserializer;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class ReactiveDashClient extends ReactiveBitcoinClient {


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

    @Override
    @PostConstruct
    protected void buildClient() {
        System.out.println("Building Dash client with hostname " + hostName);

        ObjectMapper mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }


    @Override
    protected ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new DashBlockDeserializer());
        module.addDeserializer(BitcoinTransaction.class, new BitcoinTransactionDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

    @Override
    public Mono<BitcoinBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(BitcoinBlock.class);
    }


    @Override
    public Mono<BitcoinTransaction> getTransaction(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");
        request.addParam(hash);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(BitcoinTransaction.class);
    }

}

