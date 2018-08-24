package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveClient;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
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
public class BulkExtractorImpl<B extends AbstractBlock, T extends AbstractTransaction> implements BulkExtractor<B, T> {

    private static final Logger logger = LoggerFactory.getLogger(BulkExtractorImpl.class);

    final private BlockRepository<B> blockRepository;
    final private TransactionRepository<T> transactionRepository;

    final private ReactiveClient<B, T> client;

    public BulkExtractorImpl(
            BlockRepository<B> repository,
            TransactionRepository<T> transactionRepository,
            ReactiveClient<B, T> client) {

        this.blockRepository = repository;
        this.transactionRepository = transactionRepository;
        this.client = client;
    }


    public Flux<B> saveBlocks(long fromHeight, long toHeight) {

        int fromInt = (int) fromHeight;
        int count = (int) (toHeight - fromInt) + 1;

        logger.info("Count: " + count);

        return Flux.range(fromInt, count)
                .flatMap(client::getBlockHash)
                .doOnNext(hash -> logger.info("Got block hash from client " + hash))
                // .flatMap(source -> source) // == merge()
                .flatMap(hash -> client.getBlock(hash))
                .doOnNext(bitcoinBlock -> logger.info("Created block " + bitcoinBlock))
                .flatMap(blockRepository::save);
                // .doOnNext(bitcoinBlock -> logger.info("Saved block " + bitcoinBlock));

    }

    public Flux<T> saveTransactions(B block) {
        return Flux.fromIterable(block.getTxids())
                .doOnNext(txids -> logger.info("Got txids " + txids))
                .flatMap(client::getTransaction)
                .doOnNext(txids -> logger.info("Created transactions from client " + txids))
                // .flatMap(source -> source) // == merge()
                .flatMap(transactionRepository::save)
                .doOnNext(txids -> logger.info("Saved txids " + txids));

    }


    public Flux<T> saveTransactions(Flux<B> blocks) {

        return blocks
                .map(AbstractBlock::getTxids)
                .flatMap(Flux::fromIterable)
                .flatMap(client::getTransaction)
                .flatMap(transactionRepository::save);
    }


    public Disposable saveBlocksAndTransactions(long fromHeight, long toHeight) {

        return saveBlocks(fromHeight, toHeight)
                .doOnNext(
                        block -> {
                            saveTransactions(block)
                                    .subscribe(transaction -> logger.info("Saved transaction " + transaction));
                        }
                )
                .subscribe(block -> logger.info("Saved block " + block));

    }

    public Flux<B> saveBlocksAndTransactionsForward(long fromHeight, long toHeight) {

        logger.info("About to save and forward blocks and transactions from " + fromHeight + " to " + toHeight);

        return saveBlocks(fromHeight, toHeight)
                .doOnNext(
                        block -> {
                            saveTransactions(block)
                                    .subscribe(transaction -> logger.info("Saved transaction " + transaction));
                        }
                );
    }

    public Flux<B> saveBlocksAndTransactionsForward2(long fromHeight, long toHeight) {

        logger.info("About to save and forward blocks and transactions from " + fromHeight + " to " + toHeight);

        return saveBlocks(fromHeight, toHeight)
                .doOnNext(
                        block -> {
                            saveTransactions(block)
                                    .subscribe(transaction -> logger.info("Saved transaction " + transaction));
                        }
                );
    }


    /**
     * Flux<String> ids = ifhrIds();
     *
     * Flux<String> combinations =
     *                 ids.flatMap(id -> {
     *                         Mono<String> nameTask = ifhrName(id);
     *                         Mono<Integer> statTask = ifhrStat(id);
     *
     *                         return nameTask.zipWith(statTask,
     *                                         (name, stat) -> "Name " + name + " has stats " + stat);
     *                 });
     *
     * Mono<List<String>> result = combinations.collectList();
     *
     * List<String> results = result.block();
     * assertThat(results).containsExactly(
     *                 "Name NameJoe has stats 103",
     *                 "Name NameBart has stats 104",
     *                 "Name NameHenry has stats 105",
     *                 "Name NameNicole has stats 106",
     *
     *
     *                 also :
     *                 Example of Reactor code with timeout and fallback
     */
}
