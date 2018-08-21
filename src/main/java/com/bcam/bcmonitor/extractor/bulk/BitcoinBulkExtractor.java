package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


/**
 * https://github.com/spring-projects/spring-data-examples/blob/master/redis/reactive/src/test/java/example/springdata/redis/operations/ValueOperationsTests.java
 * <p>
 * batch downloads https://dzone.com/articles/bulk-operations-in-mongodb
 * api to select attributes for bulk downloads
 * <p>
 * <p>
 * 20 mins. cache
 * <p>
 * <p>
 * .delete .then(mono.just) https://codereview.stackexchange.com/questions/159139/is-my-implementation-of-a-simple-crud-service-with-spring-webflux-correct
 * <p>
 * <p>
 * <p>
 * can make a repo by creating client the to construtor
 */
@Component
public class BitcoinBulkExtractor implements BulkExtractor {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinBulkExtractor.class);

    final private BlockRepository blockRepository;
    final private TransactionRepository transactionRepository;

    final private ReactiveBitcoinClient client;

    @Autowired
    public BitcoinBulkExtractor(BlockRepository blockRepository, TransactionRepository transactionRepository, ReactiveBitcoinClient bitcoinClient) {
        this.transactionRepository = transactionRepository;
        client = bitcoinClient;
        this.blockRepository = blockRepository;
    }


    /**
     * @param block containing hashes of transactions to be saved
     * @return a flux of the saved transactions for further processing
     */
    public Flux<BitcoinTransaction> saveTransactions(BitcoinBlock block) {
        return Flux.fromIterable(block.getTxids())
                .map(x -> client.getTransaction(x))
                .flatMap(source -> source)
                .flatMap(bitcoinTransaction -> transactionRepository.save(bitcoinTransaction));
    }

    public Flux<BitcoinTransaction> saveTransactions(Flux<BitcoinBlock> blocks) {
        return blocks
                .map(block -> block.getTxids())
                .flatMap(listIds -> Flux.fromIterable(listIds))
                .flatMap(id -> client.getTransaction(id))
                .flatMap(bitcoinTransaction -> transactionRepository.save(bitcoinTransaction));
    }

    public Flux<BitcoinBlock> saveBlocks(long fromHeight, long toHeight) {

        int fromInt = (int) fromHeight;
        int count = (int) (toHeight - fromInt) + 1;

        // logger.info("Count: " + count);

        return Flux.range(fromInt, count)
                .map(height -> client.getBlockHash(height))
                // .doOnNext(hash -> logger.info("Got hash " + hash))
                .flatMap(source -> source) // == merge()
                .flatMap(hash -> client.getBlock(hash))
                // .doOnNext(bitcoinBlock -> logger.info("Created block " + bitcoinBlock))
                .flatMap(block -> blockRepository.save(block));
                // .doOnNext(bitcoinBlock -> logger.info("Saved block " + bitcoinBlock));

    }


    public Flux<BitcoinBlock> updateBlocksBetween(long fromHeight, long toHeight) {

        return blockRepository
                .findAllByHeightInRange(fromHeight, toHeight)
                .map(bitcoinBlock -> bitcoinBlock.getHash())
                .map(hash -> client.getBlock(hash))
                .flatMap(source -> source)
                .flatMap(block -> blockRepository.save(block));
    }
}
