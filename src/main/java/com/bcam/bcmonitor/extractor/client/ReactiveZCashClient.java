package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

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

    public ReactiveZCashClient() {
        super();
    }

    @Override
    protected ReactiveHTTPClient buildClient() {
        String hostName = "localhost";
        int port = 9998;
        // port = 5000;
        String userName = "zcashuser1";
        String password = "password";

        System.out.println("Creating a reactive zcash client on port " + port);

        ObjectMapper mapper = buildMapper();

        return new ReactiveHTTPClient(hostName, port, userName, password, mapper);
    }

}
