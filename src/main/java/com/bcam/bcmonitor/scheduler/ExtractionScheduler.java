package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.bcam.bcmonitor.model.Blockchain.*;

/**
 *
 * genreic with a mandatory notnull config?
 *
 * should take generic bulk extractors in array?
 * or better to wire up each separately?
 */
// @Profile("scheduler")  // prevent scheduler from unless we need it
@Component
public class ExtractionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ExtractionScheduler.class);

    private Map<Blockchain, Boolean> enableSyncing;
    private Map<Blockchain, Long> initialHeights;
    private ConcurrentHashMap<Blockchain, Long> lastSynced;

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

        this.enableSyncing = new HashMap<>();
        enableSyncing.put(BITCOIN, Boolean.FALSE);
        enableSyncing.put(DASH, Boolean.FALSE);
        enableSyncing.put(ZCASH, Boolean.FALSE);

        initialHeights = new HashMap<>();
        initialHeights.put(BITCOIN, -1L);
        initialHeights.put(DASH, -1L);
        initialHeights.put(ZCASH, -1L);

        lastSynced = new ConcurrentHashMap<>();
        lastSynced.put(BITCOIN, -1L);
        lastSynced.put(DASH, -1L);
        lastSynced.put(ZCASH, -1L);
    }

    @Scheduled(initialDelay = 1000L, fixedRate = 2000L)
    public void syncTips() {

        tracker.updateChainTips();
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void syncBitcoinBlocks() {

        syncBlocks(BITCOIN, bitcoinBulkExtractor);
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void syncDashBlocks() {

        syncBlocks(DASH, dashBulkExtractor);
    }

    @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    public void syncZCashBlocks() {

        syncBlocks(ZCASH, zCashBulkExtractor);
    }


    private void syncBlocks(Blockchain blockchain, BulkExtractor extractor) {

        if (!syncEnabledFor(blockchain)) {
            return;
        }

        Long tip = tracker.getTipFor(blockchain);
        Long synced = getLastSyncedFor(blockchain);
        Long initialHeight = initialHeights.get(blockchain);

        long fromHeight = synced > initialHeight ? synced : initialHeight;

        if (tip > fromHeight) {

            extractor.saveBlocksAndTransactions(fromHeight + 1, tip);

            // assumes ^ always succeeds
            setLastSyncedFor(blockchain, tip);
        }
    }

    public Boolean syncEnabledFor(Blockchain blockchain) {

        return enableSyncing.get(blockchain);
    }

    public void enableSyncFor(Blockchain blockchain) {

        enableSyncing.put(blockchain, Boolean.TRUE);
    }

    public void disableSyncFor(Blockchain blockchain) {

        enableSyncing.put(blockchain, Boolean.FALSE);
    }

    private long getLastSyncedFor(Blockchain blockchain) {

        return lastSynced.get(blockchain);
    }

    private void setLastSyncedFor(Blockchain blockchain, Long tip) {

        lastSynced.put(blockchain, tip);
    }

    public Map<Blockchain, Boolean> getEnableSyncing() {
        return enableSyncing;
    }

    public Map<Blockchain, Long> getInitialHeights() {
        return initialHeights;
    }

    public ConcurrentHashMap<Blockchain, Long> getLastSynced() {
        return lastSynced;
    }


    // @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    // public void syncBitcoinBlocks() {
    //
    //     Long tip = tracker.getTipFor(BITCOIN);
    //     Long synced = tracker.getLastSyncedFor(BITCOIN);
    //
    //     if (tip > synced) {
    //         long fromHeight = synced > initialBitcoinOffset ? synced : initialBitcoinOffset;
    //         bitcoinBulkExtractor.saveBlocksAndTransactions(fromHeight + 1, synced);
    //
    //         tracker.setLastSyncedFor(BITCOIN, tip);
    //     }
    // }

    // @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    // public void syncZCashBlocks() {
    //
    //     Long tip = tracker.getTipFor(ZCASH);
    //     Long synced = tracker.getLastSyncedFor(ZCASH);
    //
    //     if (tip > synced) {
    //         long fromHeight = synced > initialHeights.get(ZCASH) ? synced : initialHeights.get(ZCASH);
    //         zCashBulkExtractor.saveBlocksAndTransactions(fromHeight + 1, synced);
    //
    //         tracker.setLastSyncedFor(ZCASH, tip);
    //
    //     }
    // }
    //
    // @Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
    // public void syncDashBlocks() {
    //     Long tip = tracker.getTipFor(DASH);
    //     Long synced = tracker.getLastSyncedFor(DASH);
    //
    //     if (tip > synced) {
    //         long fromHeight = synced > initialHeights.get(DASH) ? synced : initialHeights.get(DASH);
    //         dashBulkExtractor.saveBlocksAndTransactions(fromHeight + 1, synced);
    //
    //         tracker.setLastSyncedFor(DASH, tip);
    //
    //     }
    // }
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