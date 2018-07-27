package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.TransactionPool;
import com.bcam.bcmonitor.model.TransactionPoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinController {

    private ReactiveBitcoinClient client;

    @Autowired
    public BitcoinController(ReactiveBitcoinClient client) {
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

    @GetMapping("/blockhash/{height}")
    Mono<String> getBestBlockHash(@PathVariable int height) {
        return client.getBlockHash(height);
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

    @GetMapping("/method/{methodName}/{param:.*[a-z]+.*}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param) {
        System.out.println("USING STRING");

        return client.getCustomResponse(methodName, param);
    }

    @GetMapping("/method/{methodName}/{param:[0-9]+}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() int param) {
        System.out.println("USING INT");

        return client.getCustomResponse(methodName, param);
    }

    // only support double parameter request where the first is a string
    @GetMapping("/method/{methodName}/{param}/{param2:.*[a-z]+.*}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param, @PathVariable() String param2) {
        System.out.println("USING INT");

        return client.getCustomResponse(methodName, param, param2);
    }

    @GetMapping("/method/{methodName}/{param}/{param2:[0-9]+}")
    Mono<String> getCustomResponse(@PathVariable String methodName, @PathVariable() String param, @PathVariable() int param2) {
        System.out.println("USING STRING");

        return client.getCustomResponse(methodName, param, param2);
    }

}
