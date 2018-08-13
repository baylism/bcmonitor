package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


// TODO refactor out common code with BitcoinController
@RestController
@RequestMapping("/api/dash")
public class DashController {

    private ReactiveDashClient client;

    @Autowired
    public DashController(ReactiveDashClient client) {
        this.client = client;
    }

    // ============ parameterised requests ============
    @GetMapping("/block/{hash}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {

        return client.getBlock(hash);
    }

    @GetMapping("/transaction/{hash}")
    Mono<BitcoinTransaction> getTransaction(@PathVariable String hash) {
        return client.getTransaction(hash);
    }


    // ============ other objects ============
    @GetMapping("/transactionpool")
    Mono<TransactionPool> getTransactionPool() {
        return client.getTransactionPool();
    }

    @GetMapping("/transactionpoolinfo")
    Mono<TransactionPoolInfo> getTransactionPoolInfo() {
        return client.getTransactionPoolInfo();
    }


    // ============ other string requests ============
    @GetMapping("/blockchaininfo")
    Mono<BlockchainInfo> getInfo() {
        return client.getBlockchainInfo();
    }

    @GetMapping("/bestblockhash")
    Mono<String> getBestBlockHash() {
        return client.getBestBlockHash();
    }

    @GetMapping("/blockhash/{height}")
    Mono<String> getBestBlockHash(@PathVariable int height) {
        return client.getBlockHash(height);
    }


    // ============ client provided requests ============
    @GetMapping("/raw/{jsonQuery}")
    Mono<String> getRawResponse(@PathVariable String jsonQuery) {
        return client.getRawResponse(jsonQuery);
    }

    @GetMapping("/method/{methodName}")
    Mono<String> getCustomResponse(@PathVariable String methodName) {

        return client.getCustomResponse(methodName);
    }

    // match requests where param contains a letter (e.g. a hash)
    @GetMapping("/method/{methodName}/{param:.*[a-z]+.*}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param) {

        return client.getCustomResponse(methodName, param);
    }

    // match requests where param contains only numbers (e.g. a block height)
    @GetMapping("/method/{methodName}/{param:[0-9]+}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() int param) {

        return client.getCustomResponse(methodName, param);
    }

    // only support double parameter request where the first is a string
    @GetMapping("/method/{methodName}/{param}/{param2:.*[a-z]+.*}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param, @PathVariable() String param2) {

        return client.getCustomResponse(methodName, param, param2);
    }

    @GetMapping("/method/{methodName}/{param}/{param2:[0-9]+}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param, @PathVariable() int param2) {

        return client.getCustomResponse(methodName, param, param2);
    }

}
