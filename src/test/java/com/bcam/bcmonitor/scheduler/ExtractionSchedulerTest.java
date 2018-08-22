package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.BlockchainInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import static org.junit.Assert.*;


@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles({"mockedBlockchainClientsWithScheduler", "scheduler"})
public class ExtractionSchedulerTest {

    private static final Logger logger = LoggerFactory.getLogger(ExtractionSchedulerTest.class);

    @Autowired
    ExtractionScheduler scheduler;

    @Autowired
    ReactiveBitcoinClient mockBitcoinClient;

    @Autowired
    ReactiveZCashClient mockZCashClient;

    @Autowired
    ReactiveDashClient mockDashClient;

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

        logger.info("Runnning scheduler");

        // let scheduler run
        Thread.sleep(5000L);

        scheduler.foo();

    }


    // @Test
    // public void testSyncBlocks() throws InterruptedException {
    //
    //     BlockchainInfo info = new BlockchainInfo();
    //     info.setBlocks(0L);
    //
    //     //setup mock client
    //     Mockito.when(mockBitcoinClient.getBlockchainInfo())
    //             .thenAnswer(
    //                     new Answer<Mono<BlockchainInfo>>() {
    //                         @Override
    //                         public Mono<BlockchainInfo> answer(InvocationOnMock invocation) throws InterruptedException {
    //                             logger.info("Bitcoin client about to sleep");
    //                             Thread.sleep(10000);
    //                             return Mono.just(info);
    //                         }
    //                     }
    //             );
    //
    //
    //     Mockito.when(mockDashClient.getBlockchainInfo())
    //             // .thenThrow(new RuntimeException("THRWOM dash"))
    //             .thenReturn(Mono.just(info));
    //
    //     Mockito.when(mockZCashClient.getBlockchainInfo())
    //             // .thenThrow(new RuntimeException("THRWOM zc"))
    //             .thenReturn(Mono.just(info));
    //
    //     logger.info("Runnning");
    //
    //     Thread.sleep(20000L);
    //
    //     scheduler.foo();
    //
    //
    // }
}