package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.storage.BitcoinBlockRepository;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinController {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinController.class);

    private ReactiveBitcoinClient client;

    private ReactiveMongoTemplate template;
    private BlockRepository<BitcoinBlock> blockRepository;
    // private BitcoinBlockRepository blockRepository;
    private TransactionRepository<BitcoinTransaction> transactionRepository;


    @Autowired
    public BitcoinController(ReactiveBitcoinClient client, BitcoinBlockRepository blockRepository, TransactionRepository<BitcoinTransaction> transactionRepository) {

        this.client = client;

        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
    }

    // ============ block/transaction requests ============
    @GetMapping(value = "/block/{hash:.*[a-z]+.*}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {

        return blockRepository
                .findById(hash)
                .switchIfEmpty(client.getBlock(hash));

    }

    @GetMapping("/block/{height:[0-9]+}")
    Mono<BitcoinBlock> getBlock(@PathVariable Long height) {

        return blockRepository
                .findByHeight(height);

    }

    @GetMapping(value = "/blocks/{toHeight}", produces = "application/stream+json")
    Flux<BitcoinBlock> getBlocksUnbounded(@PathVariable long fromHeight) {

        Flux changeStream = template
                .changeStream(newAggregation(match(where("operationType").is("insert"))),
                        BitcoinBlock.class, ChangeStreamOptions.empty(), "person");

        // changeStream.doOnNext(event -> System.out.println("Hello " + event.getBody().getFirstname()))
        //         .subscribe();

    }

    // should default to latest blocks if second param is empty
    @GetMapping(value = "/blocks/{fromHeight}/{toHeight}", produces = "application/stream+json")
    Flux<BitcoinBlock> getBlocks(@PathVariable long fromHeight, @PathVariable long toHeight) {

        return blockRepository.
            findAllByHeightBetweenOrderByHeightAsc(fromHeight - 1, toHeight + 1);
                // .findAllByHeightInRange(fromHeight, toHeight);

    }

    @GetMapping("/transaction/{hash}")
    Mono<BitcoinTransaction> getTransaction(@PathVariable String hash) {

        return transactionRepository
                .findById(hash)
                .switchIfEmpty(client.getTransaction(hash));
    }


    @GetMapping(value = "/transactions/{blockHeight}", produces = "application/stream+json")
    Flux<BitcoinTransaction> getTransactionsInBlock(@PathVariable Long height) {

        Flux<String> ids = blockRepository
                .findByHeight(height)
                .doOnNext( b -> logger.info("found block" + b))
                .map(AbstractBlock::getTxids)
                .doOnNext( b -> logger.info("got tx" + b))
                .flatMapMany(Flux::fromIterable)
                .doOnNext( b -> logger.info("tx flux block" + b));

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
