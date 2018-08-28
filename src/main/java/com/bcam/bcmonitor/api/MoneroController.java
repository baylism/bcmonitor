package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveMoneroClient;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.scheduler.BlockchainTracker;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bcam.bcmonitor.model.Blockchain.MONERO;

@RestController
@RequestMapping("/api/monero")
public class MoneroController {

    private static final Logger logger = LoggerFactory.getLogger(MoneroController.class);

    private ReactiveMoneroClient client;
    private BlockRepository<MoneroBlock> blockRepository;
    private TransactionRepository<MoneroTransaction> transactionRepository;
    private BlockchainTracker tracker;


    @Autowired
    public MoneroController(ReactiveMoneroClient client, BlockRepository<MoneroBlock> blockRepository, TransactionRepository<MoneroTransaction> transactionRepository, BlockchainTracker tracker) {

        this.client = client;

        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
        this.tracker = tracker;
    }

    // ============ block/transaction requests ============
    @GetMapping(value = "/block/{hash:.*[a-z]+.*}")
    Mono<MoneroBlock> getBlock(@PathVariable String hash) {

        return blockRepository
                .findById(hash)
                .switchIfEmpty(client.getBlock(hash));
    }

    @GetMapping("/block/{height:[0-9]+}")
    Mono<MoneroBlock> getBlock(@PathVariable Long height) {

        return blockRepository.findByHeight(height);
    }


    @GetMapping(value = "/blocks/{fromHeight}/{toHeight}", produces = "application/stream+json")
    Flux<MoneroBlock> getBlocks(@PathVariable long fromHeight, @PathVariable long toHeight) {

        return blockRepository.
                findAllByHeightBetweenOrderByHeightAsc(fromHeight - 1, toHeight + 1);

    }

    @GetMapping(value = "/blocks/{fromHeight}", produces = "application/stream+json")
    Flux<MoneroBlock> getLatestBlocks(@PathVariable long fromHeight) {

        return blockRepository.findAllByHeightGreaterThan(fromHeight - 1);
    }


    @GetMapping("/transaction/{hash}")
    Mono<MoneroTransaction> getTransaction(@PathVariable String hash) {

        return transactionRepository
                .findById(hash)
                .switchIfEmpty(client.getTransaction(hash));
    }


    @GetMapping(value = "/transactions/{blockHeight}", produces = "application/stream+json")
    Flux<MoneroTransaction> getTransactionsInBlock(@PathVariable Long blockHeight) {

        Flux<String> ids = blockRepository
                .findByHeight(blockHeight)
                .map(AbstractBlock::getTxids)
                .flatMapMany(Flux::fromIterable);

        return transactionRepository.findAllById(ids);
    }

    @GetMapping("bestblockheight")
    Mono<Long> getBestHeight() {

        return Mono.justOrEmpty(tracker.getTipFor(MONERO));
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
