package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.bcam.bcmonitor.model.Blockchain.*;

/**
 * should take generic bulk extractors in array?
 * or better to wire up each separately?
 */
// @Profile("scheduler")  // prevent scheduler from unless we need it
@Component
public class ExtractionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ExtractionScheduler.class);

    private Long initialBitcoinOffset;
    private Long initialDashOffset;
    private Long initialZCashOffset;

    private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;
    private BulkExtractor<ZCashBlock, ZCashTransaction> zCashBulkExtractor;
    private BulkExtractor<DashBlock, DashTransaction> dashBulkExtractor;

    private BlockchainTracker tracker;

    @Autowired
    public ExtractionScheduler(
            BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor,
            BulkExtractor<ZCashBlock, ZCashTransaction> zCashBulkExtractor,
            BulkExtractor<DashBlock, DashTransaction> dashBulkExtractor,
            BlockchainTracker tracker) {

        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
        this.zCashBulkExtractor = zCashBulkExtractor;
        this.dashBulkExtractor = dashBulkExtractor;
        this.tracker = tracker;
    }

    @Scheduled(initialDelay = 1000L, fixedRate = 2000L)
    public void syncTips() {

        tracker.updateChainTips();

    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void syncBitcoinBlocks() {
        Long tip = tracker.getTipFor(BITCOIN);
        Long synced = tracker.getLastSyncedFor(BITCOIN);

        if (tip > synced) {
            long fromHeight = synced > initialBitcoinOffset ? synced : initialBitcoinOffset;
            bitcoinBulkExtractor.saveBlocksAndTransactions(fromHeight + 1, synced);

            tracker.setLastSyncedFor(BITCOIN, tip);
        }
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void syncZCashBlocks() {
        Long tip = tracker.getTipFor(ZCASH);
        Long synced = tracker.getLastSyncedFor(ZCASH);

        if (tip > synced) {
            long fromHeight = synced > initialZCashOffset ? synced : initialZCashOffset;
            zCashBulkExtractor.saveBlocksAndTransactions(fromHeight + 1, synced);

            tracker.setLastSyncedFor(ZCASH, tip);

        }
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void syncDashBlocks() {
        Long tip = tracker.getTipFor(DASH);
        Long synced = tracker.getLastSyncedFor(DASH);

        if (tip > synced) {
            long fromHeight = synced > initialBitcoinOffset ? synced : initialDashOffset;
            dashBulkExtractor.saveBlocksAndTransactions(fromHeight + 1, synced);

            tracker.setLastSyncedFor(DASH, tip);

        }
    }

    void foo() {
        logger.info(tracker.getClass().toString());
    }

}

// @Profile("scheduler")  // prevent scheduler from unless we need it
// @Component
// public class ExtractionScheduler {
//
//     private static final Logger logger = LoggerFactory.getLogger(ExtractionScheduler.class);
//
//
//     // start extracting blocks from this height
//     private Long initialOffset;
//
//     private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;
//     private BlockchainTrackerSingle<BitcoinBlock, BitcoinTransaction> bitcoinTracker;
//
//
//     @Autowired
//     public ExtractionScheduler(
//             BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor,
//             BlockchainTrackerSingle<BitcoinBlock, BitcoinTransaction> bitcoinTracker) {
//
//
//         this.bitcoinBulkExtractor = bitcoinBulkExtractor;
//         this.bitcoinTracker = bitcoinTracker;
//     }
//
//     @Scheduled(fixedRate = 2000L)
//     public void syncTips() {
//
//         bitcoinTracker.updateChainTip();
//
//     }
//
//     public void foo() {
//         logger.info(bitcoinTracker.getClass().toString());
//
//     }
//
// }