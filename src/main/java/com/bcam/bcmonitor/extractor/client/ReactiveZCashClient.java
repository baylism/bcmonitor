package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.mapper.BitcoinBlockDeserializer;
import com.bcam.bcmonitor.extractor.mapper.ZCashTransactionDeserializer;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.ZCashTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 *
 * https://github.com/zcash/zcash/blob/v1.1.1/doc/payment-api.md
 *
 * better: https://github.com/zcash/zcash/blob/master/src/rpcblockchain.cpp
 * https://github.com/zcash/zcash/blob/master/src/rpcrawtransaction.cpp
 *
 * the account parameter exists in the API, please use “” as its value, otherwise an error will be returned
 */
@Component
public class ReactiveZCashClient extends ReactiveBitcoinClient {

    @Value("${ZCASH_HOSTNAME}")
    private String hostName;

    @Value("${ZCASH_PORT}")
    private int port;

    @Value("${ZCASH_UN}")
    private String userName;

    @Value("${ZCASH_PW}")
    private String password;

    public ReactiveZCashClient() {
        super();
    }

    @Override
    @PostConstruct
    protected void buildClient() {
        System.out.println("Building Zcash client with hostname " + hostName);

        ObjectMapper mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }


    @Override
    protected ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new BitcoinBlockDeserializer());
        module.addDeserializer(ZCashTransaction.class, new ZCashTransactionDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

    public Mono<ZCashTransaction> getZCashTransaction(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");

        request.addParam(hash);
        request.addParam(Boolean.TRUE);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(ZCashTransaction.class);
    }



}
