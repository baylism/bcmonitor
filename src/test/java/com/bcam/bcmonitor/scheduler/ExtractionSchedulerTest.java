package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.Assert.*;


@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles({"mockedBlockchainClientsWithScheduler", "scheduler"})
public class ExtractionSchedulerTest {

    private static final Logger logger = LoggerFactory.getLogger(ExtractionSchedulerTest.class);

    @Autowired
    BlockchainTracker tracker;

    @Autowired
    ExtractionScheduler scheduler;

    @Autowired
    ReactiveBitcoinClient mockBitcoinClient;

    @Autowired
    ReactiveZCashClient mockZCashClient;

    @Autowired
    ReactiveDashClient mockDashClient;

    @Autowired
    BlockRepository<BitcoinBlock> bitcoinBlockRepository;

    @Autowired
    TransactionRepository<BitcoinTransaction> bitcoinTransactionRepository;

    @Test
    public void testSyncTips() throws InterruptedException {

        BlockchainInfo info = new BlockchainInfo();
        info.setBlocks(0L);

        //setup mock client
        Mockito.when(mockBitcoinClient.getBlockchainInfo())
                .thenAnswer(
                        new Answer<Mono<BlockchainInfo>>() {
                            @Override
                            public Mono<BlockchainInfo> answer(InvocationOnMock invocation) throws InterruptedException {
                                logger.info("Bitcoin client about to sleep");
                                Thread.sleep(2000);
                                return Mono.just(info);
                            }
                        }
                );

        Mockito.when(mockDashClient.getBlockchainInfo())
                // .thenThrow(new RuntimeException("THRWOM dash"))
                .thenReturn(Mono.just(info));

        Mockito.when(mockZCashClient.getBlockchainInfo())
                // .thenThrow(new RuntimeException("THRWOM zc"))
                .thenReturn(Mono.just(info));

        tracker.enableTrackingFor(Blockchain.BITCOIN);
        tracker.enableTrackingFor(Blockchain.DASH);
        tracker.enableTrackingFor(Blockchain.ZCASH);

        logger.info("Runnning scheduler");

        // let scheduler run
        Thread.sleep(5000L);
    }


    @Test
    public void testSyncBlocks() throws InterruptedException {

        BlockchainInfo info = new BlockchainInfo();
        info.setBlocks(0L);

        BlockchainInfo info1 = new BlockchainInfo();
        info1.setBlocks(1L);

        // blockchain info
        Mockito.when(mockBitcoinClient.getBlockchainInfo())
                .thenReturn(Mono.just(info));
        // .thenAnswer(
                //         (Answer<Mono<BlockchainInfo>>) invocation -> {
                //             logger.info("Bitcoin client about to sleep");
                //             Thread.sleep(10000);
                //             return Mono.just(info);
                //         }
                // );

        Mockito.when(mockDashClient.getBlockchainInfo())
                .thenReturn(Mono.just(info));

        Mockito.when(mockZCashClient.getBlockchainInfo())
                .thenReturn(Mono.just(info));


        // get block hash
        Mockito.when(mockBitcoinClient.getBlockHash(0L))
                .thenReturn(Mono.just("blockhash0"));

        Mockito.when(mockBitcoinClient.getBlockHash(1L))
                .thenReturn(Mono.just("blockhash1"));

        Mockito.when(mockDashClient.getBlockHash(0L))
                .thenReturn(Mono.just("blockhash0"));

        Mockito.when(mockDashClient.getBlockHash(1L))
                .thenReturn(Mono.just("blockhash1"));

        Mockito.when(mockZCashClient.getBlockHash(0L))
                .thenReturn(Mono.just("blockhash0"));

        Mockito.when(mockZCashClient.getBlockHash(1L))
                .thenReturn(Mono.just("blockhash1"));


        // get block
        BitcoinBlock block0 = new BitcoinBlock("blockhash0", 0L);
        ArrayList<String> txids = new ArrayList<>();
        txids.add("aaa");
        txids.add("bbb");
        txids.add("ccc");
        block0.setTxids(txids);

        BitcoinBlock block1 = new BitcoinBlock("blockhash1", 1L);
        ArrayList<String> txids2 = new ArrayList<>();
        txids.add("ddd");
        txids.add("eee");
        txids.add("fff");
        block1.setTxids(txids2);

        Mockito.when(mockBitcoinClient.getBlock("blockhash0"))
                .thenReturn(Mono.just(block0));

        Mockito.when(mockBitcoinClient.getBlock("blockhash1"))
                .thenReturn(Mono.just(block1));


        DashBlock dashBlock0 = new DashBlock("blockhash0", 0L);
        dashBlock0.setTxids(txids);

        DashBlock dashBlock1 = new DashBlock("blockhash1", 1L);
        dashBlock1.setTxids(txids2);

        Mockito.when(mockDashClient.getBlock("blockhash0"))
                .thenReturn(Mono.just(dashBlock0));

        Mockito.when(mockDashClient.getBlock("blockhash1"))
                .thenReturn(Mono.just(dashBlock1));


        ZCashBlock zCashBlock0 = new ZCashBlock("blockhash0", 0L);
        zCashBlock0.setTxids(txids);

        ZCashBlock zCashBlock1 = new ZCashBlock("blockhash1", 1L);
        zCashBlock1.setTxids(txids2);

        Mockito.when(mockZCashClient.getBlock("blockhash0"))
                .thenReturn(Mono.just(zCashBlock0));

        Mockito.when(mockZCashClient.getBlock("blockhash1"))
                .thenReturn(Mono.just(zCashBlock1));


        // get transaction
        BitcoinTransaction transaction0 = new BitcoinTransaction("aaa");
        BitcoinTransaction transaction1 = new BitcoinTransaction("bbb");
        BitcoinTransaction transaction2 = new BitcoinTransaction("ccc");
        BitcoinTransaction transaction3 = new BitcoinTransaction("ddd");
        BitcoinTransaction transaction4 = new BitcoinTransaction("eee");
        BitcoinTransaction transaction5 = new BitcoinTransaction("fff");

        ZCashTransaction zCashTransaction0 = new ZCashTransaction("aaa");
        ZCashTransaction zCashTransaction1 = new ZCashTransaction("bbb");
        ZCashTransaction zCashTransaction2 = new ZCashTransaction("ccc");
        ZCashTransaction zCashTransaction3 = new ZCashTransaction("ddd");
        ZCashTransaction zCashTransaction4 = new ZCashTransaction("eee");
        ZCashTransaction zCashTransaction5 = new ZCashTransaction("fff");


        DashTransaction dashTransaction0 = new DashTransaction("aaa");
        DashTransaction dashTransaction1 = new DashTransaction("bbb");
        DashTransaction dashTransaction2 = new DashTransaction("ccc");
        DashTransaction dashTransaction3 = new DashTransaction("ddd");
        DashTransaction dashTransaction4 = new DashTransaction("eee");
        DashTransaction dashTransaction5 = new DashTransaction("fff");


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

        Mockito.when(mockZCashClient.getTransaction("aaa"))
                .thenReturn(Mono.just(zCashTransaction0));
        Mockito.when(mockZCashClient.getTransaction("bbb"))
                .thenReturn(Mono.just(zCashTransaction1));
        Mockito.when(mockZCashClient.getTransaction("ccc"))
                .thenReturn(Mono.just(zCashTransaction2));
        Mockito.when(mockZCashClient.getTransaction("ddd"))
                .thenReturn(Mono.just(zCashTransaction3));
        Mockito.when(mockZCashClient.getTransaction("eee"))
                .thenReturn(Mono.just(zCashTransaction4));
        Mockito.when(mockZCashClient.getTransaction("fff"))
                .thenReturn(Mono.just(zCashTransaction5));


        Mockito.when(mockDashClient.getTransaction("aaa"))
                .thenReturn(Mono.just(dashTransaction0));
        Mockito.when(mockDashClient.getTransaction("bbb"))
                .thenReturn(Mono.just(dashTransaction1));
        Mockito.when(mockDashClient.getTransaction("ccc"))
                .thenReturn(Mono.just(dashTransaction2));
        Mockito.when(mockDashClient.getTransaction("ddd"))
                .thenReturn(Mono.just(dashTransaction3));
        Mockito.when(mockDashClient.getTransaction("eee"))
                .thenReturn(Mono.just(dashTransaction4));
        Mockito.when(mockDashClient.getTransaction("fff"))
                .thenReturn(Mono.just(dashTransaction5));


        tracker.disableTrackingFor(Blockchain.DASH);
        tracker.disableTrackingFor(Blockchain.ZCASH);

        tracker.enableTrackingFor(Blockchain.BITCOIN);
        scheduler.enableSyncFor(Blockchain.BITCOIN);

        logger.info("Runnning");

        Thread.sleep(20000L);


        Sort sort = new Sort(Sort.Direction.ASC, "hash");

        Flux<BitcoinTransaction> insertedTransactions = bitcoinTransactionRepository.findAll(sort);

        Flux<BitcoinBlock> insertedBlocks = bitcoinBlockRepository.findAllByHeightInRange(0L, 1L);

        StepVerifier
                .create(insertedTransactions)
                .expectNext(transaction0, transaction1, transaction2, transaction3, transaction4, transaction5)
                .expectComplete()
                .verify();


        StepVerifier
                .create(insertedBlocks)
                .expectNext(block0, block1);




    }
}