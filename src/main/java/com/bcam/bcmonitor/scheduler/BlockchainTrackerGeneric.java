package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.BlockchainInfoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * Class to keep track of current blockchain status
 *
 */
@Component
public class BlockchainTrackerGeneric<C extends BlockchainInfoClient> {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainTrackerGeneric.class);


    private AtomicLong tip;
    private AtomicLong lastSynced;

    private C client;

    @Autowired
    public BlockchainTrackerGeneric(C client) {

        this.client = client;

        tip = new AtomicLong(0);
        lastSynced = new AtomicLong(0);

    }


    public void updateChainTip() {

        // tip.set(client.getBlockchainInfo().block().getBlocks());

        Long newTip = client.getBlockchainInfo().block().getBlocks();

        if (tip != null) {
            logger.info("Updating tip to " + newTip);

            tip.set(newTip);
        }

        logger.info("Updated tip to " + tip);

    }


    public long getTip() {
        return tip.get();
    }

    public long getLastSynced() {
        return lastSynced.get();
    }
}
