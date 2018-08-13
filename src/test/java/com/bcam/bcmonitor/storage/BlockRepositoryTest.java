package com.bcam.bcmonitor.storage;

import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class BlockRepositoryTest {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    ReactiveMongoOperations operations;


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
                        new BitcoinBlock("foo"),
                        new BitcoinBlock("bar"),
                        new BitcoinBlock("baz")
                ).collectList());

        // run insertion test
        StepVerifier
                .create(insertAll)
                .expectNextCount(3)
                .verifyComplete();

    }

    /**
     * This sample performs a count, inserts data and performs a count again using reactive operator chaining. It prints
     * the two counts ({@code 4} and {@code 6}) to the console.
     */
    @Test
    public void shouldInsertAndCountData() {

        Mono<Long> saveAndCount = blockRepository.count()
                .doOnNext(System.out::println)
                .thenMany(
                        blockRepository.saveAll(
                                Flux.just(
                                        new BitcoinBlock("add1"),
                                        new BitcoinBlock("add2"),
                                        new BitcoinBlock("add3")
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
}