package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.BlockchainInfo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@ActiveProfiles("mockedBlockchainClients")
@TestPropertySource(properties = {
        "BITCOIN_HOSTNAME=localhost", "BITCOIN_PORT=9998", "BITCOIN_UN=bitcoinuser1", "BITCOIN_PW=password", "DASH_HOSTNAME=localhost", "DASH_PORT=9998", "DASH_UN=dashuser1", "DASH_PW=password", "ZCASH_HOSTNAME=localhost", "ZCASH_PORT=9998", "ZCASH_UN=dashuser1", "ZCASH_PW=password"})
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlockchainTrackerGenericTest {

    @Autowired
    BlockchainTrackerGeneric<ReactiveBitcoinClient> tracker;

    @Autowired
    ReactiveBitcoinClient mockBitcoinClient;

    @Test
    public void testAutowire() {

        Assertions.assertEquals(tracker.getLastSynced(), 0L);
    }


    @Test
    public void updateTips() {

        BlockchainInfo info1 = new BlockchainInfo();
        info1.setBlocks(21L);

        BlockchainInfo info2 = new BlockchainInfo();
        info2.setBlocks(22L);

        Mockito.when(mockBitcoinClient.getBlockchainInfo())
                .thenReturn(Mono.just(info1))
                .thenReturn(Mono.just(info2));

        tracker.updateChainTip();
        Assertions.assertEquals(21L, tracker.getTip());

        tracker.updateChainTip();
        Assertions.assertEquals(22L, tracker.getTip());

    }
}