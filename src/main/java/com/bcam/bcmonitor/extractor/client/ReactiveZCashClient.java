package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.mapper.BitcoinBlockDeserializer;
import com.bcam.bcmonitor.extractor.mapper.ZCashTransactionDeserializer;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.ZCashTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Value("${BITCOIN_HOSTNAME}")
    private String hostName;

    @Value("${BITCOIN_PORT}")
    private int port;

    @Value("${BITCOIN_UN}")
    private String userName;

    @Value("${BITCOIN_PW}")
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


}
