package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * curl --user dashuser:Y44evktH7CY9Ilvo --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockchaininfo", "params": [] }' -H 'content-type: text/plain;' http://localhost:9998/
 */
@Component
public class ReactiveDashClient {
    private String userName;
    private String password;
    private String hostName;
    private int port;

    protected ReactiveHTTPClient client;

    public ReactiveDashClient() {
        userName = "dashuser1";
        password = "password";
        hostName = "localhost";
        // port = 9998;
        port = 5000;

        client = new ReactiveHTTPClient(hostName, port, userName, password);
    }

    public Mono<BitcoinBlock> getBlock(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);

        System.out.println("CLIENT GETTING BLOCK");

        return client.request(request.toString());
    }

    public Mono<String> getBlockString(String hash) {
        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);

        System.out.println("CLIENT GETTING BLOCK");

        return client.requestString(request.toString());
    }

    public Mono<String> getInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");

        System.out.println("CLIENT GETTING INFO");

        Mono<String> response = client.requestString(request.toString());
        // Mono<String> response = Mono.just("foo");

        System.out.println("request made");

        return response;
    }

    // public Mono<String> getInfo(String hash) {
    //     JSONRPCRequest request = new JSONRPCRequest("getblock");
    //     request.addParam(hash);
    //
    //     System.out.println("CLIENT GETTING INFO");
    //
    //     return client.requestString(request.toString());
    // }

    public Mono<BitcoinBlock> getBetter(String hash) throws IOException {

        return client.req();
    }

}

