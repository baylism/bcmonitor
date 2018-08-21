package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public DashController( ReactiveDashClient client) {
        this.client = client;
    }

    // public DashController(@Qualifier("ReactiveDashClient") ReactiveDashClient client) {
    //     this.client = client;
    // }

    // ============ parameterised requests ============
    @GetMapping("/block/{hash}")
    Mono<DashBlock> getBlock(@PathVariable String hash) {

        return client.getBlock(hash);
    }

    @GetMapping("/transaction/{hash}")
    Mono<DashTransaction> getTransaction(@PathVariable String hash) {
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
