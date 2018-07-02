package com.bcam.bcmonitor.extractor.client;

import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;

import java.io.IOException;

/**
 * curl --user dashuser:Y44evktH7CY9Ilvo --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockchaininfo", "params": [] }' -H 'content-type: text/plain;' http://localhost:9998/
 */
public class DashClient extends BitcoinClient {

    public DashClient(String userName, String password, String hostName, int port) {
        super(userName, password, hostName, port);
    }

    @Override
    public BitcoinBlock getBlock(String hash) throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);

        String response = client.request(request.toString());

        return mapper.readValue(response, BitcoinBlock.class);
    }


    @Override
    public BitcoinTransaction getTransaction(String hash) throws IOException {
        JSONRPCRequest request = new JSONRPCRequest("getrawtransaction");
        request.addParam(hash);
        request.addParam(1);

        String response = client.request(request.toString());

        return mapper.readValue(response, BitcoinTransaction.class);
    }

    // public String getBlockString(String hash) throws IOException {
    //     JSONRPCRequest request = new JSONRPCRequest("getblock");
    //     request.addParam(hash);
    //     // request.addParam(1); // always request decoded JSON with transactions
    //
    //     String response = client.request(request.toString());
    //     // return response;
    //
    //     return mapper.readValue(response, String.class);
    // }
}
