package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/zcash")
public class ZCashController {

    @Qualifier("reactiveDashClient")
    private ReactiveZCashClient client;

    @Autowired
    public ZCashController(@Qualifier("ReactiveZCashClient") ReactiveZCashClient client) {
        this.client = client;
    }

    // ============ parameterised requests ============
    @GetMapping("/block/{hash}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {
        return client.getBlock(hash);
    }

    @GetMapping("/transaction/{hash}")
    Mono<ZCashTransaction> getTransaction(@PathVariable String hash) {

        return client.getZCashTransaction(hash);
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

    @GetMapping("/blockchaininfo")
    Mono<BlockchainInfo> getInfo() {
        return client.getBlockchainInfo();
    }


    // ============ other string requests ============
    @GetMapping("/bestblockhash")
    Mono<String> getBestBlockHash() {
        return client.getBestBlockHash();
    }

    @GetMapping("/blockhash/{height}")
    Mono<String> getBestBlockHash(@PathVariable long height) {
        return client.getBlockHash(height);
    }

}

