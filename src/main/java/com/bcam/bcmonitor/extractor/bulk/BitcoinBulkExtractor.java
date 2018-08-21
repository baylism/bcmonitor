package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.AbstractBlock;
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
    public BitcoinBulkExtractor(BlockRepository blockRepository,
                                TransactionRepository transactionRepository,
                                ReactiveBitcoinClient bitcoinClient) {

        this.transactionRepository = transactionRepository;
        client = bitcoinClient;
        this.blockRepository = blockRepository;
    }


    public Flux<BitcoinTransaction> saveTransactions(BitcoinBlock block) {
        return Flux.fromIterable(block.getTxids())
                .map(client::getTransaction)
                .flatMap(source -> source) // == merge()
                .flatMap(transactionRepository::save);
    }

    public Flux<BitcoinTransaction> saveTransactions(Flux<BitcoinBlock> blocks) {
        return blocks
                .map(AbstractBlock::getTxids)
                .flatMap(Flux::fromIterable)
                .flatMap(client::getTransaction)
                .flatMap(transactionRepository::save);
    }

    public Flux<BitcoinBlock> saveBlocks(long fromHeight, long toHeight) {

        int fromInt = (int) fromHeight;
        int count = (int) (toHeight - fromInt) + 1;

        return Flux.range(fromInt, count)
                .map(client::getBlockHash)
                .flatMap(source -> source) // == merge()
                .flatMap(client::getBlock)
                .flatMap(blockRepository::save);

    }
}
