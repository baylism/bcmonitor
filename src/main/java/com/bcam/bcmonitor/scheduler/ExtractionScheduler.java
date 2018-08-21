package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.BitcoinBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    // start extracting blocks from this height
    private Long initialOffset;

    private BlockchainTracker blockchainTracker;
    private BulkExtractor<BitcoinBlock> bitcoinBulkExtractor;

    @Autowired
    public ExtractionScheduler(BlockchainTracker blockchainTracker, BulkExtractor<BitcoinBlock> bitcoinBulkExtractor) {

        initialOffset = 100L;

        this.blockchainTracker = blockchainTracker;
        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
    }


    // @Scheduled(fixedRate = 1000L)
    // public void updateTips() {
    //     blockchainTracker.updateChainTips();
    // }


    // separate for each blockchain so have different rates?
    @Scheduled(fixedRate = 2000L)
    public void updateHashes() {

        // get current height
        blockchainTracker.updateChainTips();

        // check whether synced up to best height
        long bestHeight = blockchainTracker.getTipFor(BITCOIN);
        long lastSynced = blockchainTracker.getLastSyncedFor(BITCOIN);

        // if not synced up to best height
        if (lastSynced < bestHeight) {

            long fromHeight = lastSynced > initialOffset ? lastSynced : initialOffset;

            // bitcoinBulkExtractor.saveHashes(fromHeight, bestHeight);
        }
    }

}