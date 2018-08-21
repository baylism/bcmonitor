package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.storage.BlockRepository;
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
public class BulkExtractorImpl<T extends AbstractBlock> implements BulkExtractor<T> {

    private static final Logger logger = LoggerFactory.getLogger(BulkExtractor.class);

    final private BlockRepository<T> repository;

    final private ReactiveBitcoinClient client;

    @Autowired
    public BulkExtractorImpl(BlockRepository<T> repository, ReactiveBitcoinClient bitcoinClient) {
        client = bitcoinClient;
        this.repository = repository;
    }

    @Override
    public Flux<T> saveBlocks(long fromHeight, long toHeight) {
        return null;
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
