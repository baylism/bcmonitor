package com.bcam.bcmonitor.extractor;

import com.bcam.bcmonitor.extractor.client.BitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BlockchainInfo;
import com.bcam.bcmonitor.storage.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Parameter;
import java.util.ArrayList;


/**
 *
 * https://github.com/spring-projects/spring-data-examples/blob/master/redis/reactive/src/test/java/example/springdata/redis/operations/ValueOperationsTests.java
 *
 * batch downloads https://dzone.com/articles/bulk-operations-in-mongodb
 * api to select attributes for bulk downloads
 *
 *
 * 20 mins. cache
 *
 *
 * .delete .then(mono.just) https://codereview.stackexchange.com/questions/159139/is-my-implementation-of-a-simple-crud-service-with-spring-webflux-correct
 *
 */
@Component
public class BulkExtractor {

    private static final Logger logger = LoggerFactory.getLogger(BulkExtractor.class);


    final private BlockRepository repository;

    final private ReactiveBitcoinClient client;

    @Autowired
    public BulkExtractor(BlockRepository repository) {
        client = new ReactiveBitcoinClient();
        this.repository = repository;
    }


    // TODO also check fromHeight
    private long validateHeight(long toHeight) {

        BlockchainInfo info = client.getBlockchainInfo().block();

        assert info != null;

        if (toHeight > info.getBlocks()) {

            logger.info("Only " + info.getBlocks() + " blocks found. Requesting to block " + info.getBlocks() + " instead of block " + toHeight);

            toHeight = info.getBlocks();
        }

        return toHeight;

    }

    public void saveHashes(long fromHeight, long toHeight) {

        toHeight = validateHeight(toHeight);

        for (long i = fromHeight; i < toHeight; i++) {

            long finalI = i;

            Mono<BitcoinBlock> block = client
                    .getBlockHash(i)
                    .map(hash -> new BitcoinBlock(hash, finalI))
                    .map(repository::save)

                    // subscribes Mono and blocks the current thread until a result is available
                    .block();
        }
    }

    private Mono<Void> saveBlocksFromHashes(long fromHeight, long toHeight) {

        // Flux<String> hashes =
        //         repository
        //                 .findAllByHeightBetween(fromHeight, toHeight)
        //                 .map(AbstractBlock::getHash);
        //
        // hashes
        //         .map(client::getBlock)
        //         .doOnNext(repository::saveAll)
        //         .subscribe();

        return repository
                .findAllByHeightBetween(fromHeight, toHeight)
                .map(AbstractBlock::getHash)
                .map(client::getBlock)
                .doOnNext(repository::saveAll)
                .then();
    }


    public Mono<Void> batchedSaveBlocksFromHashes(long fromHeight, long toHeight, int batchSize) {

        // TODO fix with proper reactive types. Compose result of saveBlocks... into return value
        Mono<Void> result = null;

        toHeight = validateHeight(toHeight);
        long i = fromHeight + batchSize;

        while (i != toHeight) {

            result = saveBlocksFromHashes(fromHeight, i);

            fromHeight += batchSize;
            i += batchSize;

            if (i > toHeight) {
                i = toHeight;
            }
        }

        return result;
    }
}
