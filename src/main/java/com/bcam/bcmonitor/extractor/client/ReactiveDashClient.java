package com.bcam.bcmonitor.extractor.client;


import com.bcam.bcmonitor.extractor.mapper.DashBlockDeserializer;
import com.bcam.bcmonitor.extractor.rpc.JSONRPCRequest;
import com.bcam.bcmonitor.extractor.rpc.ReactiveHTTPClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class ReactiveDashClient {
    private String hostName;
    private int port;
    private String userName;
    private String password;


    protected ReactiveHTTPClient client;

    public ReactiveDashClient() {
        hostName = "localhost";
        // port = 9998;
        port = 5000;
        userName = "dashuser1";
        password = "password";

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

    public Mono<BitcoinBlock> getBlockFromString(String hash) {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitcoinBlock.class, new DashBlockDeserializer());
        mapper.registerModule(module);

        JSONRPCRequest request = new JSONRPCRequest("getblock");
        request.addParam(hash);

        System.out.println("CLIENT GETTING BLOCK");

        return client
                .requestResponseSpec(request.toString())
                .bodyToMono(BitcoinBlock.class);
    }

    public Mono<String> getInfo() {
        JSONRPCRequest request = new JSONRPCRequest("getblockchaininfo");

        System.out.println("CLIENT GETTING INFO");

        Mono<String> response = client.requestString(request.toString());
        // Mono<String> response = Mono.just("foo");

        System.out.println("request made");

        return response;
    }

}

