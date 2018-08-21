package com.bcam.bcmonitor.scheduler;

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

    // private BlockchainTrackerGeneric<BitcoinBlock> bitcoinTracker;
    // private BlockchainTrackerGeneric<ReactiveZCashClient> zCashTracker;

    private BlockchainTracker blockchainTracker;
    // start extracting blocks from this height
    private Long initialOffset;

    private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;

    @Autowired
    public ExtractionScheduler(
            BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor,
            BlockchainTracker blockchainTracker
            // BlockchainTrackerGeneric<BitcoinBlock> bitcoinTracker
    ) {
        this.blockchainTracker = blockchainTracker;

        initialOffset = 100L;

        this.bitcoinBulkExtractor = bitcoinBulkExtractor;

    }

    @Scheduled(fixedRate = 2000L)
    public void syncBlocks() {

        // get current height
        blockchainTracker.updateChainTips();

        // check whether synced up to best height
        // long bestHeight = blockchainTracker.getTips();
        // long lastSynced = blockchainTracker.getLastSynced();

        // // if not synced up to best height
        // if (lastSynced < bestHeight) {
        //
        //     long fromHeight = lastSynced > initialOffset ? lastSynced : initialOffset;
        //
        //     Mono<Void> completed = bitcoinBulkExtractor.saveBlocks(fromHeight, bestHeight).then();
        //
        // }
    }

}