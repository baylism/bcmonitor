package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        // String userName = "bitcoinrpc";
        // String password = "123";
        // String hostName = "localhost";
        // int port = 9998;
        //
        // System.out.println("Creating a reactive bitcoin client on port " + port);

        System.out.println("Building Zcash client with hostname " + hostName);

        // System.out.println("THIS IS IT! from dash" + hostName);

        ObjectMapper mapper = buildMapper();

        client = new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

    // @Override
    // protected ReactiveHTTPClient buildClient() {
    //     // String hostName = "localhost";
    //     // int port = 9998;
    //     // // port = 5000;
    //     // String userName = "zcashuser1";
    //     // String password = "password";
    //
    //     System.out.println("Creating a reactive zcash client on port " + port);
    //
    //     ObjectMapper mapper = buildMapper();
    //
    //     return new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    // }

}
