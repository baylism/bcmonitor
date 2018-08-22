package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * should take generic bulk extractors in array?
 * or better to wire up each separately?
 *
 */
@Profile("scheduler")  // prevent scheduler from unless we need it
@Component
public class ExtractionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ExtractionScheduler.class);


    // start extracting blocks from this height
    private Long initialOffset;

    private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;
    private BlockchainTracker tracker;


    @Autowired
    public ExtractionScheduler(
            BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor,
            BlockchainTracker tracker) {


        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
        this.tracker = tracker;
    }

    @Scheduled(fixedRate = 2000L)
    public void updateTrackers() {

        tracker.updateChainTips();

    }

    public void foo() {
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
//     public void updateTrackers() {
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