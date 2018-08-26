package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.bulk.BulkExtractorImpl;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/zcash")
public class ZCashController {

    private static final Logger logger = LoggerFactory.getLogger(ZCashController.class);

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
    @GetMapping("/block/{hash:.*[a-z]+.*}")
    Mono<ZCashBlock> getBlock(@PathVariable String hash) {

        return blockRepository
                .findById(hash)
                .switchIfEmpty(client.getBlock(hash));

    }

    @GetMapping("/block/{height:[0-9]+}")
    Mono<ZCashBlock> getBlock(@PathVariable Long height) {

        return blockRepository
                .findByHeight(height);

    }

    @GetMapping("/blocks/{fromHeight}/{toHeight}")
    Flux<ZCashBlock> getBlocks(@PathVariable long fromHeight, @PathVariable long toHeight) {

        return blockRepository
                .findAllByHeightBetweenOrderByHeightAsc(fromHeight - 1, toHeight + 1);

        // .findAllByHeightInRange(fromHeight, toHeight);

    }

    @GetMapping("/transaction/{hash}")
    Mono<ZCashTransaction> getTransaction(@PathVariable String hash) {

        return transactionRepository
                .findById(hash)
                .switchIfEmpty(client.getTransaction(hash));
    }

    @GetMapping("/transactions/{blockHeight}")
    Flux<ZCashTransaction> getTransactionsInBlock(@PathVariable long height) {

        Flux<String> ids = blockRepository
                .findByHeight(height)
                .map(AbstractBlock::getTxids)
                .flatMapMany(Flux::fromIterable);

        return transactionRepository.findAllById(ids);
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

