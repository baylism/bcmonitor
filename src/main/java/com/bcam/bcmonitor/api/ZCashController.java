package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/zcash")
public class ZCashController {

    private ReactiveZCashClient client;

    private BlockRepository<ZCashBlock> blockRepository;
    private TransactionRepository<ZCashTransaction> transactionRepository;

    @Autowired
    public ZCashController(ReactiveZCashClient client, BlockRepository<ZCashBlock> blockRepository, TransactionRepository<ZCashTransaction> transactionRepository) {
        this.client = client;
        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
    }


    // ============ parameterised requests ============
    @GetMapping("/block/{hash}")
    Mono<ZCashBlock> getBlock(@PathVariable String hash) {

        return blockRepository
                .findById(hash)
                .doOnNext(b -> System.out.println("block from repo" + b))
                .switchIfEmpty(client.getBlock(hash))
                .doOnNext(b -> System.out.println("block from client" + b));

        // return client.getBlock(hash);
    }

    @GetMapping("/transaction/{hash}")
    Mono<ZCashTransaction> getTransaction(@PathVariable String hash) {

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

