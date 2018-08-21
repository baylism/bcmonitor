package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.ArrayList;

// import static org.junit.Assert.assertEquals;

// @ActiveProfiles("mockedBlockchainClients")
@ActiveProfiles("mockBitcoinConfiguration")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class BitcoinBulkExtractorTest {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinBulkExtractorTest.class);

    @Autowired
    ReactiveBitcoinClient mockBitcoinClient;

    @Autowired
    BulkExtractor<BitcoinBlock, BitcoinTransaction> bulkExtractor;

    @Autowired
    BlockRepository<BitcoinBlock> blockRepository;

    @Autowired
    TransactionRepository<BitcoinTransaction> transactionRepository;

    @Autowired
    ReactiveMongoOperations operations;

    @Before
    public void setUp() {

        // check if transaction collection exists
        Mono<Boolean> alreadyExists = operations.collectionExists(BitcoinTransaction.class);

        // if it does, drop collection then recreate. Else just create.
        Mono<MongoCollection<Document>> recreateCollection = alreadyExists
                .flatMap(yes -> yes ? operations.dropCollection(BitcoinTransaction.class) : Mono.just(alreadyExists))
                .then(operations.createCollection(BitcoinTransaction.class, CollectionOptions.empty()
                        .size(1024 * 1024)
                        .maxDocuments(100)
                        .capped()));

        // run recreation test
        StepVerifier
                .create(recreateCollection)
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    public void saveBlocks() {

        //setup mock client
        Mockito.when(mockBitcoinClient.getBlockHash(0L))
                .thenReturn(Mono.just("hash0"));

        Mockito.when(mockBitcoinClient.getBlockHash(1L))
                .thenReturn(Mono.just("hash1"));

        Mockito.when(mockBitcoinClient.getBlockHash(2L))
                .thenReturn(Mono.just("hash2"));


        BitcoinBlock block0 = new BitcoinBlock("hash0", 0L);
        block0.setConfirmations(2);

        BitcoinBlock block1 = new BitcoinBlock("hash1", 1L);
        block1.setConfirmations(1);

        BitcoinBlock block2 = new BitcoinBlock("hash2", 2L);
        block2.setConfirmations(0);


        Mockito.when(mockBitcoinClient.getBlock("hash0"))
                .thenReturn(Mono.just(block0));

        Mockito.when(mockBitcoinClient.getBlock("hash1"))
                .thenReturn(Mono.just(block1));

        Mockito.when(mockBitcoinClient.getBlock("hash2"))
                .thenReturn(Mono.just(block2));


        Flux<BitcoinBlock> save = bulkExtractor.saveBlocks(0L, 2L);

        save.blockLast();

        Flux<BitcoinBlock> insertedBlocks = blockRepository.findAllByHeightInRange(0L, 2L);

        StepVerifier
                .create(insertedBlocks)
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock + "confirmations " + insertedBlock.getConfirmations());
                    assertEquals("hash0", insertedBlock.getHash());
                    assertEquals(0L, insertedBlock.getHeight());
                    assertEquals(2, insertedBlock.getConfirmations());
                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock + "confirmations " + insertedBlock.getConfirmations());
                    assertEquals("hash1", insertedBlock.getHash());
                    assertEquals(1L, insertedBlock.getHeight());
                    assertEquals(1, insertedBlock.getConfirmations());

                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock + "confirmations " + insertedBlock.getConfirmations());
                    assertEquals("hash2", insertedBlock.getHash());
                    assertEquals(2L, insertedBlock.getHeight());
                    assertEquals(0, insertedBlock.getConfirmations());
                })
                .expectComplete()
                .verify();
    }

    // @Test
    // public void saveTransactions() {
    //
    //     BitcoinTransaction transaction0 = new BitcoinTransaction("aaa");
    //
    //     BitcoinTransaction transaction1 = new BitcoinTransaction("bbb");
    //
    //     BitcoinTransaction transaction2 = new BitcoinTransaction("ccc");
    //
    //
    //     Mockito.when(mockBitcoinClient.getTransaction("aaa"))
    //             .thenReturn(Mono.just(transaction0));
    //
    //     Mockito.when(mockBitcoinClient.getTransaction("bbb"))
    //             .thenReturn(Mono.just(transaction1));
    //
    //     Mockito.when(mockBitcoinClient.getTransaction("ccc"))
    //             .thenReturn(Mono.just(transaction2));
    //
    //
    //     BitcoinBlock block = new BitcoinBlock();
    //     ArrayList<String> txids = new ArrayList<>();
    //     txids.add("aaa");
    //     txids.add("bbb");
    //     txids.add("ccc");
    //     block.setTxids(txids);
    //
    //
    //     Flux<BitcoinTransaction> save = bulkExtractor.saveTransactions(block);
    //
    //     save.blockLast();
    //
    //     Sort sort = new Sort(Sort.Direction.ASC, "hash");
    //
    //     Flux<BitcoinTransaction> insertedTransactions = transactionRepository.findAll(sort);
    //
    //     StepVerifier
    //             .create(insertedTransactions)
    //             .assertNext(insertedTransaction -> {
    //                 logger.info("Got transaction " + insertedTransaction);
    //                 assertEquals("aaa", insertedTransaction.getHash());
    //             })
    //             .assertNext(insertedTransaction -> {
    //                 logger.info("Got transaction " + insertedTransaction);
    //                 assertEquals("bbb", insertedTransaction.getHash());
    //
    //             })
    //             .assertNext(insertedTransaction -> {
    //                 logger.info("Got transaction " + insertedTransaction);
    //                 assertEquals("ccc", insertedTransaction.getHash());
    //             })
    //             .expectComplete()
    //             .verify();
    // }

    @Test
    public void saveTransactionsFromFlux() {

        BitcoinTransaction transaction0 = new BitcoinTransaction("aaa");
        BitcoinTransaction transaction1 = new BitcoinTransaction("bbb");
        BitcoinTransaction transaction2 = new BitcoinTransaction("ccc");
        BitcoinTransaction transaction3 = new BitcoinTransaction("ddd");
        BitcoinTransaction transaction4 = new BitcoinTransaction("eee");
        BitcoinTransaction transaction5 = new BitcoinTransaction("fff");


        Mockito.when(mockBitcoinClient.getTransaction("aaa"))
                .thenReturn(Mono.just(transaction0));
        Mockito.when(mockBitcoinClient.getTransaction("bbb"))
                .thenReturn(Mono.just(transaction1));
        Mockito.when(mockBitcoinClient.getTransaction("ccc"))
                .thenReturn(Mono.just(transaction2));

        Mockito.when(mockBitcoinClient.getTransaction("ddd"))
                .thenReturn(Mono.just(transaction3));
        Mockito.when(mockBitcoinClient.getTransaction("eee"))
                .thenReturn(Mono.just(transaction4));
        Mockito.when(mockBitcoinClient.getTransaction("fff"))
                .thenReturn(Mono.just(transaction5));


        BitcoinBlock block1 = new BitcoinBlock("blockhash1", 0L);
        ArrayList<String> txids = new ArrayList<>();
        txids.add("aaa");
        txids.add("bbb");
        txids.add("ccc");
        block1.setTxids(txids);

        BitcoinBlock block2 = new BitcoinBlock("blockhash2", 1L);
        ArrayList<String> txids2 = new ArrayList<>();
        txids.add("ddd");
        txids.add("eee");
        txids.add("fff");
        block2.setTxids(txids2);

        Flux<BitcoinBlock> blockFlux = Flux.just(block1, block2);

        Flux<BitcoinTransaction> save = bulkExtractor.saveTransactions(blockFlux);
        save.blockLast();

        Sort sort = new Sort(Sort.Direction.ASC, "hash");
        Flux<BitcoinTransaction> insertedTransactions = transactionRepository.findAll(sort);

        // StepVerifier
        //         .create(insertedTransactions)
        //         .expectNext(transaction0, transaction1, transaction2, transaction3, transaction4, transaction5)
        //         .expectComplete()
        //         .verify();

        StepVerifier
                .create(insertedTransactions)
                .assertNext(insertedTransaction -> {
                    logger.info("Got transaction " + insertedTransaction);
                    assertEquals("aaa", insertedTransaction.getHash());
                })
                .assertNext(insertedTransaction -> {
                    logger.info("Got transaction " + insertedTransaction);
                    assertEquals("bbb", insertedTransaction.getHash());
                })
                .assertNext(insertedTransaction -> {
                    logger.info("Got transaction " + insertedTransaction);
                    assertEquals("ccc", insertedTransaction.getHash());
                })
                .assertNext(insertedTransaction -> {
                    logger.info("Got transaction " + insertedTransaction);
                    assertEquals("ddd", insertedTransaction.getHash());
                })
                .assertNext(insertedTransaction -> {
                    logger.info("Got transaction " + insertedTransaction);
                    assertEquals("eee", insertedTransaction.getHash());
                })
                .assertNext(insertedTransaction -> {
                    logger.info("Got transaction " + insertedTransaction);
                    assertEquals("fff", insertedTransaction.getHash());
                })
                .expectComplete()
                .verify();
    }
}

