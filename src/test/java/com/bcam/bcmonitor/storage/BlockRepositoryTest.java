package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.extractor.bulk.BitcoinBulkExtractor;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class BlockRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(BlockRepositoryTest.class);


    @Autowired
    BlockRepository blockRepository;

    @Autowired
    ReactiveMongoOperations operations;



    // @Before
    // public void before() {
    //     StepVerifier.create(operations.execute(it -> it.serverCommands().flushDb())).expectNext("OK").verifyComplete();
    // }


    @Before
    public void setUp() {

        // check if block collection exists
        Mono<Boolean> alreadyExists = operations.collectionExists(BitcoinBlock.class);

        // if it does, drop collection then recreate. Else just create.
        Mono<MongoCollection<Document>> recreateCollection = alreadyExists
                .flatMap(yes -> yes ? operations.dropCollection(BitcoinBlock.class) : Mono.just(alreadyExists))
                .then(operations.createCollection(BitcoinBlock.class, CollectionOptions.empty()
                        .size(1024 * 1024)
                        .maxDocuments(100)
                        .capped()));

        // run recreation test
        StepVerifier
                .create(recreateCollection)
                .expectNextCount(1)
                .verifyComplete();

        // insert some blocks
        Flux<BitcoinBlock> insertAll = operations.insertAll(
                Flux.just(
                        new BitcoinBlock("foo", 0L),
                        new BitcoinBlock("bar", 1L),
                        new BitcoinBlock("baz", 2L)
                ).collectList());

        // run insertion test
        StepVerifier
                .create(insertAll)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    public void shouldInsertAndCountData() {

        Mono<Long> saveAndCount = blockRepository.count()
                .doOnNext(System.out::println)
                .thenMany(
                        blockRepository.saveAll(
                                Flux.just(
                                        new BitcoinBlock("add1", 3L),
                                        new BitcoinBlock("add2", 4L),
                                        new BitcoinBlock("add3", 5L)
                                )
                        )
                )
                .last()
                .flatMap(v -> blockRepository.count())
                .doOnNext(System.out::println);

        StepVerifier
                .create(saveAndCount)
                .expectNext(6L)
                .verifyComplete();
    }


    @Test
    public void findByHeight() {


        Mono<BitcoinBlock> insertedBlockMono = blockRepository.findByHeight(1L);

        StepVerifier
                .create(insertedBlockMono)
                .assertNext(insertedBlock -> {
                    assertEquals("bar", insertedBlock.getHash());
                    assertEquals(1L, insertedBlock.getHeight());
                })
                .expectComplete()
                .verify();

    }

    @Test
    public void findAllByHeightInRange() {

        Flux<BitcoinBlock> insertedBlockFlux = blockRepository.findAllByHeightInRange(0L, 2L);

        StepVerifier
                .create(insertedBlockFlux)
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock);
                    assertEquals("foo", insertedBlock.getHash());
                    assertEquals(0L, insertedBlock.getHeight());
                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock);
                    assertEquals("bar", insertedBlock.getHash());
                    assertEquals(1L, insertedBlock.getHeight());
                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock);
                    assertEquals("baz", insertedBlock.getHash());
                    assertEquals(2L, insertedBlock.getHeight());
                })
                .expectComplete()
                .verify();

    }

    @Test
    public void insertBlockAndVerify() {

        BitcoinBlock block = new BitcoinBlock("abc", 7L);

        block.setChainWork(new BigInteger("001"));

        ArrayList<String> txids = new ArrayList<>();
        txids.add("tx1");
        txids.add("tx2");
        txids.add("tx3");

        block.setTxids(txids);

        blockRepository.save(block).block();

        Mono<BitcoinBlock> insertedBlockMono = blockRepository.findById("abc");

        StepVerifier
                .create(insertedBlockMono)
                .assertNext(insertedBlock -> {
                    assertEquals("abc", insertedBlock.getHash());
                    assertEquals(new BigInteger("001"), insertedBlock.getChainWork());
                    assertEquals(txids, insertedBlock.getTxids());
                    assertEquals(7L, insertedBlock.getHeight());
                })
                .expectComplete()
                .verify();
    }
}