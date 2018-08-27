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

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

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


    // should default to latest blocks if second param is empty
    @GetMapping(value = "/blocks/{fromHeight}/{toHeight}", produces = "application/stream+json")
    Flux<BitcoinBlock> getBlocks(@PathVariable long fromHeight, @PathVariable long toHeight) {

        return blockRepository.
                findAllByHeightBetweenOrderByHeightAsc(fromHeight - 1, toHeight + 1);
        // .findAllByHeightInRange(fromHeight, toHeight);

    }

    @GetMapping(value = "/blocks/{fromHeight}", produces = "application/stream+json")
    Flux<BitcoinBlock> getLatestBlocks(@PathVariable long fromHeight) {

        return blockRepository.findAllByHeightGreaterThan(fromHeight);
    }


    // @GetMapping(value = "/blocks/{fromHeight}", produces = "application/stream+json")
    // Flux<BitcoinBlock> getLatestBlocks(@PathVariable long fromHeight) {
    //
    //     // UnicastProcessor<BitcoinBlock> hotSource = UnicastProcessor.create();
    //     //
    //     // Flux<BitcoinBlock> hotFlux = hotSource.publish()
    //     //         .autoConnect();
    //     //
    //     //
    //     //
    //     // return hotFlux;
    //
    //
    //     AtomicReference<Long> reached = new AtomicReference<>(fromHeight);
    //
    //     return Flux.generate(
    //             () -> fromHeight,
    //             (state, sink) -> {
    //
    //                 blockRepository
    //                         .findAllByHeightGreaterThan(state)
    //                         .subscribe(block -> {
    //
    //                             if (block.getHeight() > reached.get()){
    //                                 reached.set(block.getHeight());
    //                                 sink.next(block);
    //                             }
    //                         });
    //
    //                 // if (state == 10) sink.complete();
    //                 return reached.get();
    //             });
    //  // AtomicReference<Long> reached = new AtomicReference<>(fromHeight);
    //  //
    //  //    return Flux.generate(
    //  //            () -> fromHeight,
    //  //            (state, sink) -> {
    //  //
    //  //
    //  //                Flux<BitcoinBlock> newBlocks = blockRepository
    //  //
    //  //                        .findAllByHeightGreaterThan(state)
    //  //                        .doOnNext(block -> {
    //  //
    //  //                            if (block.getHeight() > reached.get()){
    //  //                                reached.set(block.getHeight());
    //  //                            }
    //  //
    //  //                        });
    //  //
    //  //                newBlocks.subscribe(sink::next);
    //  //
    //  //                // if (state == 10) sink.complete();
    //  //                return reached.get();
    //  //            });
    //
    //     // return flux;
    //     // return blockRepository.
    //     //         findAllByHeightGreaterThanOrderByHeightAsc(fromHeight - 1)
    //     //         .switchIfEmpty();
    //
    //     // .findAllByHeightInRange(fromHeight, toHeight);
    //
    // }


    @GetMapping("/transaction/{hash}")
    Mono<BitcoinTransaction> getTransaction(@PathVariable String hash) {

        return transactionRepository
                .findById(hash)
                .switchIfEmpty(client.getTransaction(hash));
    }


    @GetMapping(value = "/transactions/{blockHeight}", produces = "application/stream+json")
    Flux<BitcoinTransaction> getTransactionsInBlock(@PathVariable Long blockHeight) {

        Flux<String> ids = blockRepository
                .findByHeight(blockHeight)
                // .doOnNext( b -> logger.info("found block" + b))
                .map(AbstractBlock::getTxids)
                // .doOnNext( b -> logger.info("got tx" + b))
                .flatMapMany(Flux::fromIterable);
        // .doOnNext( b -> logger.info("tx flux block" + b));

        // return ids;

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
