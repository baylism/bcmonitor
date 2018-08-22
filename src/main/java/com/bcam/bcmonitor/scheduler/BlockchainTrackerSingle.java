package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.BlockchainInfoClient;
import com.bcam.bcmonitor.extractor.client.ReactiveClient;
import com.bcam.bcmonitor.extractor.client.ReactiveClientImpl;
import com.bcam.bcmonitor.model.AbstractBlock;
import com.bcam.bcmonitor.model.AbstractTransaction;
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
public class BlockchainTrackerSingle<B extends AbstractBlock, T extends AbstractTransaction> {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainTrackerSingle.class);

    private AtomicLong tip;
    private AtomicLong lastSynced;

    private ReactiveClient<B, T> client;


    // @Autowired
    public BlockchainTrackerSingle(ReactiveClient<B, T> client) {

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