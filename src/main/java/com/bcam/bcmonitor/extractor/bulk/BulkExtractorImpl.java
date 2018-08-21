package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveClient;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
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
public class BulkExtractorImpl<B extends AbstractBlock, T extends AbstractTransaction> implements BulkExtractor<B, T> {

    private static final Logger logger = LoggerFactory.getLogger(BulkExtractorImpl.class);

    final private BlockRepository<B> blockRepository;
    final private TransactionRepository<T> transactionRepository;

    final private ReactiveClient<B, T> client;

    public BulkExtractorImpl(
            BlockRepository<B> repository,
            TransactionRepository<T> transactionRepository,
            ReactiveClient<B, T> client
    ) {

        this.transactionRepository = transactionRepository;
        this.blockRepository = repository;

        this.client = client;
    }


    public Flux<B> saveBlocks(long fromHeight, long toHeight) {

        int fromInt = (int) fromHeight;
        int count = (int) (toHeight - fromInt) + 1;

        logger.info("Count: " + count);

        return Flux.range(fromInt, count)
                .map(client::getBlockHash)
                .doOnNext(hash -> logger.info("Got hash " + hash))
                .flatMap(source -> source) // == merge()
                .flatMap(client::getBlock)
                .doOnNext(bitcoinBlock -> logger.info("Created block " + bitcoinBlock))
                .flatMap(blockRepository::save)
                .doOnNext(bitcoinBlock -> logger.info("Saved block " + bitcoinBlock));

    }

    public Flux<T> saveTransactions(Flux<B> blocks) {

        return blocks
                .map(block -> block.getTxids())
                .flatMap(listIds -> Flux.fromIterable(listIds))
                .flatMap(id -> client.getTransaction(id))
                .flatMap(bitcoinTransaction -> transactionRepository.save(bitcoinTransaction));
    }

}
