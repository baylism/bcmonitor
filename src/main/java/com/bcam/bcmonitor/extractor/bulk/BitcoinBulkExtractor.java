package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BlockchainInfo;
import com.bcam.bcmonitor.storage.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
 *
 *
 * can make a repo by creating client the to construtor
 *
 *
 *
 */
@Component
public class BitcoinBulkExtractor implements BulkExtractor {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinBulkExtractor.class);

    final private BlockRepository repository;

    final private ReactiveBitcoinClient client;

    @Autowired
    public BitcoinBulkExtractor(BlockRepository repository, ReactiveBitcoinClient bitcoinClient) {
        // client = new ReactiveBitcoinClient();
        client = bitcoinClient;
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

    // private Mono<Void> saveHash(long height) {
    //     logger.info("Saving hash for height " + height);
    //
    //     return client
    //             .getBlockHash(height)
    //             .map(
    //                     hash -> {
    //                         logger.info("Got hash" + hash);
    //                         return hash;
    //                     }
    //             )
    //             .map(hash -> new BitcoinBlock(hash, height))
    //             .flatMap(repository::save)
    //             .then();
    // }

    private Mono<Void> saveHash(long height) {
        logger.info("Saving hash for height " + height);

        return client
                .getBlockHash(height)
                .map(
                        hash -> {
                            logger.info("Got hash" + hash);
                            return hash;
                        }
                )
                .map(hash -> new BitcoinBlock(hash, height))
                .flatMap(repository::save)
                .then();
    }

    public void saveHashes(long fromHeight, long toHeight) {

        logger.debug("About to save hashes between height " + fromHeight + " - " + toHeight);

        toHeight = validateHeight(toHeight);

        for (long i = fromHeight; i < toHeight; i++) {

            String hash = client.getBlockHash(i).block();

            logger.debug("Got hash from client " + hash);

            repository
                    .save(new BitcoinBlock(hash, i))
                    .subscribe(bitcoinBlock -> logger.info("Inserted block" + bitcoinBlock));
        }
    }


    // public void saveHashes(long fromHeight, long toHeight) {
    //
    //     logger.info("About to save hashes between height " + fromHeight + " - " + toHeight);
    //
    //     int fromInt = (int) fromHeight;
    //     int count = (int) (fromHeight - fromInt) + 1;
    //     //
    //     // return Flux.range(fromInt, count)
    //     //         .map(height -> getStubBlock(height))
    //     //         .flatMap()
    //
    //     // Mono<String> hash = client.getBlockHash(fromInt);
    //     //
    //     // return hash
    //     //         .flatMap(h -> Mono.just(new BitcoinBlock(h, fromInt)))
    //     //         .subscribe(
    //     //                 s -> repository.save(s)
    //     //         );
    //     // ;
    //
    //     while (fromHeight != toHeight) {
    //         getStubBlock(fromHeight)
    //                 .subscribe(repository::save);
    //
    //         fromHeight ++;
    //     }
    // }
    //
    private Mono<BitcoinBlock> getStubBlock(long height) {
        return client.getBlockHash(height)
                .flatMap(h -> Mono.just(new BitcoinBlock(h, height)));

    }

    // private Mono<BitcoinBlock> getStubBlock(long height) {
    //     return client.getBlockHash(height)
    //             .flatMap(h -> Mono.just(new BitcoinBlock(h, height)));
    //
    // }

    //
    // public void saveHashes(long fromHeight, long toHeight) {
    //
    //
    //     toHeight = validateHeight(toHeight);
    //
    //     for (long i = fromHeight; i < toHeight; i++) {
    //
    //         long finalI = i;
    //
    //         Mono<BitcoinBlock> block = client
    //                 .getBlockHash(i)
    //                 .map(hash -> new BitcoinBlock(hash, finalI))
    //                 .map(repository::save)
    //
    //                 // subscribes Mono and blocks the current thread until a result is available
    //                 .block();
    //
    //     }
    // }

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
                .flatMap(repository::saveAll)
                // .doOnNext(repository::saveAll)
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
