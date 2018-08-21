package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveClient;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.storage.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigInteger;
import java.util.ArrayList;


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
public class BulkExtractorImpl<B extends AbstractBlock, T extends AbstractTransaction> implements BulkExtractor<B, T> {

    final private BlockRepository<B> repository;

    final private ReactiveClient<B, T> client;

    @Autowired
    public BulkExtractorImpl(BlockRepository<B> repository, ReactiveClient<B, T> client) {

        this.client = client;
        this.repository = repository;
    }


    public Flux<B> saveBlocks(long fromHeight, long toHeight) {

        int fromInt = (int) fromHeight;
        int count = (int) (toHeight - fromInt) + 1;

        // logger.info("Count: " + count);

        return Flux.range(fromInt, count)
                .map(height -> client.getBlockHash(height))
                // .doOnNext(hash -> logger.info("Got hash " + hash))
                .flatMap(source -> source) // == merge()
                .flatMap(hash -> client.getBlock(hash))
                // .doOnNext(bitcoinBlock -> logger.info("Created block " + bitcoinBlock))
                .flatMap(block -> repository.save(block));

        // .doOnNext(bitcoinBlock -> logger.info("Saved block " + bitcoinBlock));

    }


    // public Flux<BitcoinBlock> saveBlocks(long fromHeight, long toHeight) {
    //
    //     int fromInt = (int) fromHeight;
    //     int count = (int) (toHeight - fromInt) + 1;
    //
    //     System.out.println("Count: " + count);
    //
    //     Flux<BitcoinBlock> blockFlux = Flux.range(fromInt, count)
    //             .map(client::getBlockHash)
    //             .flatMap(source -> source) // == merge()
    //             .flatMap(hash -> client.getBlock(hash));
    //
    //     return blockFlux;
    //
    //     // blockFlux
    //     //         .map(block -> repository.save());
    //     // return repository.saveAll(blockFlux);
    // }
}
