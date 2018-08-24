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
import java.io.IOException;

/**
 *
 * https://github.com/zcash/zcash/blob/v1.1.1/doc/payment-api.md
 *
 * better: https://github.com/zcash/zcash/blob/master/src/rpcblockchain.cpp
 * https://github.com/zcash/zcash/blob/master/src/rpcrawtransaction.cpp
 *
 * the account parameter exists in the API, please use “” as its value, otherwise an error will be returned
 */
// @Qualifier("ReactiveZCashClient")
@Component
public class ReactiveZCashClient extends ReactiveClientImpl implements ReactiveClient<ZCashBlock, ZCashTransaction>, BlockchainInfoClient {

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

        // bitcoin
        module.addDeserializer(TransactionPoolInfo.class, new BitcoinTransactionPoolInfoDeserializer());
        module.addDeserializer(TransactionPool.class, new BitcoinTransactionPoolDeserializer());
        module.addDeserializer(RPCResult.class, new RPCResultDeserializer());
        module.addDeserializer(BlockchainInfo.class, new BlockchainInfoDeserializer());

        // zcash
        module.addDeserializer(ZCashBlock.class, new ZCashBlockDeserializer());
        module.addDeserializer(ZCashTransaction.class, new ZCashTransactionDeserializer());

        mapper.registerModule(module);

        return mapper;
    }

    @Override
    ReactiveHTTPClient getClient() {
        return client;
    }

    public Mono<ZCashTransaction> getTransaction(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");

        request.addParam(hash);
        request.addParam(1);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(ZCashTransaction.class);
    }

    public Mono<ZCashBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);
        request.addParam(true); // always request decoded JSON with transactions

        // System.out.println(request);

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(ZCashBlock.class);
    }

    @Override
    public Mono<TransactionPoolInfo> getTransactionPoolInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getmempoolinfo");

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(TransactionPoolInfo.class);

    }
}
