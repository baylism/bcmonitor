package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DashController.class);

    private ReactiveDashClient client;

    private BlockRepository<DashBlock> blockRepository;
    private TransactionRepository<DashTransaction> transactionRepository;

    @Autowired
    public DashController(ReactiveDashClient client, BlockRepository<DashBlock> blockRepository, TransactionRepository<DashTransaction> transactionRepository) {

        this.client = client;

        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
    }

    // ============ parameterised requests ============
    @GetMapping("/block/{hash}")
    Mono<DashBlock> getBlock(@PathVariable String hash) {

        return blockRepository
                .findById(hash)
                .switchIfEmpty(client.getBlock(hash));
    }

    @GetMapping("/transaction/{hash}")
    Mono<DashTransaction> getTransaction(@PathVariable String hash) {

        return transactionRepository
                .findById(hash)
                .switchIfEmpty(client.getTransaction(hash));
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
