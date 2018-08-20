package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BlockchainInfo;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.BlockRepositoryCustom;
import com.bcam.bcmonitor.storage.BlockRepositoryTest;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class BitcoinBulkExtractorTest {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinBulkExtractorTest.class);

//     // you can mock concrete classes, not only interfaces
//     LinkedList mockedList = mock(LinkedList.class);
//
//     // stubbing appears before the actual execution
//     when(mockedList.get(0)).thenReturn("first");
//
// // the following prints "first"
// System.out.println(mockedList.get(0));
//
// // the following prints "null" because get(999) was not stubbed
// System.out.println(mockedList.get(999));

    @Autowired
    ReactiveBitcoinClient mockBitcoinClient;

    @Autowired
    BitcoinBulkExtractor bulkExtractor;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    ReactiveMongoOperations operations;


    // @Test
    // public void name() {
    //     BlockchainInfo info1 = new BlockchainInfo();
    //     info1.setBestblockhash("hash1");
    //     info1.setBlocks(1L);
    //
    //     BlockchainInfo info2 = new BlockchainInfo();
    //     info2.setBestblockhash("hash1");
    //     info2.setBlocks(1L);
    //
    //     BlockchainInfo info3 = new BlockchainInfo();
    //     info3.setBestblockhash("hash1");
    //     info3.setBlocks(1L);
    //
    //
    //     Mockito.when(mockBitcoinClient.getBlockchainInfo())
    //             .thenReturn(Mono.just(info1))
    //             .thenReturn(Mono.just(info2))
    //             .thenReturn(Mono.just(info3));
    // }

    // @Test
    // public void shouldInsertAndCountData() {
    //
    //     Mono<Long> saveAndCount = blockRepository.count()
    //             .doOnNext(System.out::println)
    //             .thenMany(
    //                     blockRepository.saveAll(
    //                             Flux.just(
    //                                     new BitcoinBlock("add1", 4L),
    //                                     new BitcoinBlock("add2", 5L),
    //                                     new BitcoinBlock("add3", 6L)
    //                             )
    //                     )
    //             )
    //             .last()
    //             .flatMap(v -> blockRepository.count())
    //             .doOnNext(System.out::println);
    //
    //     StepVerifier
    //             .create(saveAndCount)
    //             .expectNext(3L)
    //             .verifyComplete();
    // }

    @Test
    public void testSaveHashes() {

        BlockchainInfo info1 = new BlockchainInfo();
        info1.setBestblockhash("hash1");
        info1.setBlocks(3L);

        Mockito.when(mockBitcoinClient.getBlockchainInfo())
                .thenReturn(Mono.just(info1));


        Mockito.when(mockBitcoinClient.getBlockHash(0L))
                .thenReturn(Mono.just("hash0"));

        Mockito.when(mockBitcoinClient.getBlockHash(1L))
                .thenReturn(Mono.just("hash1"));

        Mockito.when(mockBitcoinClient.getBlockHash(2L))
                .thenReturn(Mono.just("hash2"));


        bulkExtractor.saveHashes(0L, 3L);

        // // perform extract/save
        // StepVerifier
        //         .create(save)
        //         .expectNextCount(1)
        //         .verifyComplete();

        // Mono<BitcoinBlock> insertedBlockMono = blockRepository.findById("hash1");

        Flux<BitcoinBlock> insertedBlocks = blockRepository.findAllByHeightInRange(0L, 3L);

        StepVerifier
                .create(insertedBlocks)
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock);
                    assertEquals("hash0", insertedBlock.getHash());
                    assertEquals(0L, insertedBlock.getHeight());
                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock);
                    assertEquals("hash1", insertedBlock.getHash());
                    assertEquals(1L, insertedBlock.getHeight());
                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock);
                    assertEquals("hash2", insertedBlock.getHash());
                    assertEquals(2L, insertedBlock.getHeight());
                })
                .expectComplete()
                .verify();

    }
}