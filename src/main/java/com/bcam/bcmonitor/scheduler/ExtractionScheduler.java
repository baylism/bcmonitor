package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BitcoinBulkExtractor;
import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.bcam.bcmonitor.model.Blockchain.BITCOIN;

/**
 *
 * should take generic bulk extractors in array?
 * or better to wire up each separately?
 *
 */
@Profile("scheduler")  // prevent scheduler from unless we need it
@Component
public class ExtractionScheduler {

    private BlockchainTrackerGeneric<ReactiveBitcoinClient> bitcoinTracker;
    private BlockchainTrackerGeneric<ReactiveDashClient> dashTracker;
    private BlockchainTrackerGeneric<ReactiveZCashClient> zCashTracker;


    // start extracting blocks from this height
    private Long initialOffset;

    private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;



    @Autowired
    public ExtractionScheduler(BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor
            BlockchainTrackerGeneric<ReactiveBitcoinClient> bitcoinTracker,
            BlockchainTrackerGeneric<ReactiveDashClient> dashTracker,
            BlockchainTrackerGeneric<ReactiveZCashClient> zCashTracker,
    ) {
        this.bitcoinTracker = bitcoinTracker;
        this.dashTracker = dashTracker;
        this.zCashTracker = zCashTracker;

        initialOffset = 100L;

        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
        this.dashBulkExtractor = dashBulkExtractor;
        this.zCashBulkExtractor = zCashBulkExtractor;
    }

    @Scheduled(fixedRate = 2000L)
    public void syncBlocks() {

        // get current height
        bitcoinTracker.updateChainTip();

        // check whether synced up to best height
        long bestHeight = bitcoinTracker.getTip();
        long lastSynced = bitcoinTracker.getLastSynced();

        // if not synced up to best height
        if (lastSynced < bestHeight) {

            long fromHeight = lastSynced > initialOffset ? lastSynced : initialOffset;

            Mono<Void> completed = bitcoinBulkExtractor.saveBlocks(fromHeight, bestHeight).then();

        }
    }

}