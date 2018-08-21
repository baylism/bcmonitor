package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.ZCashBlock;
import com.bcam.bcmonitor.model.ZCashTransaction;
import com.bcam.bcmonitor.storage.BlockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ActiveProfiles("mockZCashConfiguration")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
public class ZCashBulkExtractorTest {

    private static final Logger logger = LoggerFactory.getLogger(ZCashBulkExtractorTest.class);

    @Autowired
    ReactiveZCashClient mockZCashClient;

    @Autowired
    BulkExtractor<ZCashBlock, ZCashTransaction> bulkExtractor;

    @Autowired
    BlockRepository<ZCashBlock> blockRepository;

    @Test
    public void saveBlocks() {

        //setup mock client
        Mockito.when(mockZCashClient.getBlockHash(0L))
                .thenReturn(Mono.just("hash0"));

        Mockito.when(mockZCashClient.getBlockHash(1L))
                .thenReturn(Mono.just("hash1"));

        Mockito.when(mockZCashClient.getBlockHash(2L))
                .thenReturn(Mono.just("hash2"));



        ZCashBlock block0 = new ZCashBlock("hash0", 0L);
        block0.setConfirmations(2);

        ZCashBlock block1 = new ZCashBlock("hash1", 1L);
        block1.setConfirmations(1);

        ZCashBlock block2 = new ZCashBlock("hash2", 2L);
        block2.setConfirmations(0);



        Mockito.when(mockZCashClient.getBlock("hash0"))
                .thenReturn(Mono.just(block0));

        Mockito.when(mockZCashClient.getBlock("hash1"))
                .thenReturn(Mono.just(block1));

        Mockito.when(mockZCashClient.getBlock("hash2"))
                .thenReturn(Mono.just(block2));


        Flux<ZCashBlock> save = bulkExtractor.saveBlocks(0L, 2L);

        save.blockLast();

        Flux<ZCashBlock> insertedBlocks = blockRepository.findAllByHeightInRange(0L, 2L);

        StepVerifier
                .create(insertedBlocks)
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock + "confirmations " + insertedBlock.getConfirmations());
                    // assertEquals("hash0", insertedBlock.getHash());
                    // assertEquals(0L, insertedBlock.getHeight());
                    // assertEquals(2, insertedBlock.getConfirmations());
                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock + "confirmations " + insertedBlock.getConfirmations());
                    // assertEquals("hash1", insertedBlock.getHash());
                    // assertEquals(1L, insertedBlock.getHeight());
                    // assertEquals(1, insertedBlock.getConfirmations());

                })
                .assertNext(insertedBlock -> {
                    logger.info("Got block " + insertedBlock + "confirmations " + insertedBlock.getConfirmations());
                    // assertEquals("hash2", insertedBlock.getHash());
                    // assertEquals(2L, insertedBlock.getHeight());
                    // assertEquals(0, insertedBlock.getConfirmations());
                })
                .expectComplete()
                .verify();
    }
}

