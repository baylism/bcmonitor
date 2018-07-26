package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dash")
public class DashController {

    private ReactiveDashClient client;

    @Autowired
    public DashController(ReactiveDashClient client) {
        this.client = client;
    }

    // parameterised requests
    @GetMapping("/block/{hash}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {

        return client.getBlock(hash);
    }

    @GetMapping("/transaction/{hash}")
    Mono<BitcoinTransaction> getTransaction(@PathVariable String hash) {
        return client.getTransaction(hash);
    }

    // other objects
    //TODO
    @GetMapping("/transactionpool")
    Mono<TransactionPool> getTransactionPool() {
        return client.getTransactionPool();
    }

    @GetMapping("/transactionpoolinfo")
    Mono<TransactionPoolInfo> getTransactionPoolInfo() {
        return client.getTransactionPoolInfo();
    }


    // other string requests
    @GetMapping("/blockchaininfo")
    Mono<String> getInfo() {
        return client.getBlockchainInfo();
    }

    @GetMapping("/bestblockhash")
    Mono<String> getBestBlockHash() {
        return client.getBestBlockHash();
    }

    // client provided requests

    @GetMapping("/raw/{jsonQuery}")
    Mono<String> getRawResponse(@PathVariable String jsonQuery) {
        return client.getRawResponse(jsonQuery);
    }


    @GetMapping("/method/{methodName}")
    Mono<String> getCustomResponse(@PathVariable String methodName) {

        return client.getCustomResponse(methodName);
    }

    @GetMapping("/method/{methodName}/{param}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param) {

        return client.getCustomResponse(methodName, param);
    }

    @GetMapping("/method/{methodName}/{param}/{param2}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param, @PathVariable() String param2) {

        return client.getCustomResponse(methodName, param, param2);
    }

}
