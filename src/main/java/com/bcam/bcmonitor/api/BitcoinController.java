package com.bcam.bcmonitor.api;


import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
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

import java.util.ArrayList;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinController {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinController.class);

    private ReactiveBitcoinClient client;

    private BlockRepository<BitcoinBlock> blockRepository;
    private TransactionRepository<BitcoinTransaction> transactionRepository;

    @Autowired
    public BitcoinController(ReactiveBitcoinClient client, BlockRepository<BitcoinBlock> blockRepository, TransactionRepository<BitcoinTransaction> transactionRepository) {

        this.client = client;

        this.blockRepository = blockRepository;
        this.transactionRepository = transactionRepository;
    }

    // parameterised requests
    @GetMapping("/block/{hash}")
    Mono<BitcoinBlock> getBlock(@PathVariable String hash) {

        return blockRepository
                .findById(hash)
                .switchIfEmpty(client.getBlock(hash));
    }

    @GetMapping("/blocks/{fromHeight}/{toHeight}")
    Flux<BitcoinBlock> getBlocks(@PathVariable long fromHeight, @PathVariable long toHeight) {

        return blockRepository
                .findAllByHeightInRange(fromHeight, toHeight);

    }


    @GetMapping("/transaction/{hash}")
    Mono<BitcoinTransaction> getTransaction(@PathVariable String hash) {

        return transactionRepository
                .findById(hash)
                .switchIfEmpty(client.getTransaction(hash));
    }


    @GetMapping("/transactions/{blockHeight}")
    Flux<BitcoinTransaction> getTransactionsInBlock(@PathVariable long height) {

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
