package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.bulk.BitcoinBulkExtractor;
import com.bcam.bcmonitor.model.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.bcam.bcmonitor.model.Blockchain.*;

@Profile("scheduler")  // prevent scheduler from unless we need it
@Component
public class ExtractionScheduler {

    // start extracting blocks from this height
    private Long initialOffset;

    private BlockchainTracker blockchainTracker;
    private BitcoinBulkExtractor bitcoinBulkExtractor;

    @Autowired
    public ExtractionScheduler(BlockchainTracker blockchainTracker, BitcoinBulkExtractor bitcoinBulkExtractor) {
        this.blockchainTracker = blockchainTracker;
        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
    }


    @Scheduled(fixedRate = 1000L)
    public void updateTips() {
        blockchainTracker.updateChainTips();
    }


    @Scheduled(fixedRate = 10000L)
    public void updateBlocks() {

        long currentHeight = blockchainTracker.getTipFor(BITCOIN);
        long lastSynced = blockchainTracker.getLastSyncedFor(BITCOIN);

        long fromHeight = lastSynced > initialOffset ? lastSynced : initialOffset;

        bitcoinBulkExtractor.saveHashes(fromHeight, currentHeight);
    }

}