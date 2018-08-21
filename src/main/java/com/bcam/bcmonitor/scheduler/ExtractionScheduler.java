package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BitcoinBulkExtractor;
import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.model.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.bcam.bcmonitor.model.Blockchain.*;

/**
 *
 * should take generic bulk extractors in array?
 * or better to wire up each separately?
 *
 */
@Profile("scheduler")  // prevent scheduler from unless we need it
@Component
public class ExtractionScheduler {

    private BlockchainTrackerGeneric<ReactiveBitcoinClient> biTracker;

    // start extracting blocks from this height
    private Long initialOffset;

    private BlockchainTracker blockchainTracker;
    private BitcoinBulkExtractor bitcoinBulkExtractor;

    @Autowired
    public ExtractionScheduler(BlockchainTrackerGeneric<ReactiveBitcoinClient> biTracker, BlockchainTracker blockchainTracker, BitcoinBulkExtractor bitcoinBulkExtractor) {

        this.biTracker = biTracker;

        initialOffset = 100L;

        this.blockchainTracker = blockchainTracker;
        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
    }

    @Scheduled(fixedRate = 2000L)
    public void updateHashes() {

        // get current height
        biTracker.updateChainTip();

        // check whether synced up to best height
        long bestHeight = biTracker.getTip();
        long lastSynced = biTracker.getLastSynced();

        // if not synced up to best height
        if (lastSynced < bestHeight) {

            long fromHeight = lastSynced > initialOffset ? lastSynced : initialOffset;

            Mono<Void> completed = bitcoinBulkExtractor.saveBlocks(fromHeight, bestHeight).then();

        }
    }

    // // separate for each blockchain so have different rates?
    // @Scheduled(fixedRate = 2000L)
    // public void updateHashes() {
    //
    //     // get current height
    //     blockchainTracker.updateChainTips();
    //
    //     // check whether synced up to best height
    //     long bestHeight = blockchainTracker.getTipFor(BITCOIN);
    //     long lastSynced = blockchainTracker.getLastSyncedFor(BITCOIN);
    //
    //     // if not synced up to best height
    //     if (lastSynced < bestHeight) {
    //
    //         long fromHeight = lastSynced > initialOffset ? lastSynced : initialOffset;
    //
    //         Mono<Void> completed = bitcoinBulkExtractor.saveBlocks(fromHeight, bestHeight).then();
    //
    //     }
    // }

}