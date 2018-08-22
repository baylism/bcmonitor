package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.*;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.Blockchain;
import com.bcam.bcmonitor.model.BlockchainInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

import static com.bcam.bcmonitor.model.Blockchain.BITCOIN;
import static com.bcam.bcmonitor.model.Blockchain.DASH;
import static com.bcam.bcmonitor.model.Blockchain.ZCASH;

/**
 * Class to keep track of current blockchain status
 * <p>
 * <p>
 * blocks:
 * current height
 * last synced
 * <p>
 * transactions:
 * transactions synced up to block height
 */
public class BlockchainTracker {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainTracker.class);

    Mono<Long> tip;

    private ConcurrentHashMap<Blockchain, Long> tips;
    private ConcurrentHashMap<Blockchain, Long> lastSynced;

    private ReactiveBitcoinClient bitcoinClient;
    private ReactiveDashClient dashClient;
    private ReactiveZCashClient zCashClient;

    @Autowired
    public BlockchainTracker(ReactiveBitcoinClient bitcoinClient, ReactiveDashClient dashClient, ReactiveZCashClient zCashClient) {

        this.bitcoinClient = bitcoinClient;
        this.dashClient = dashClient;
        this.zCashClient = zCashClient;

        tips = new ConcurrentHashMap<>();
        tips.put(BITCOIN, 0L);
        tips.put(DASH, 0L);
        tips.put(ZCASH, 0L);

        lastSynced = new ConcurrentHashMap<>();
        lastSynced.put(BITCOIN, 0L);
        lastSynced.put(DASH, 0L);
        lastSynced.put(ZCASH, 0L);
    }

    @Async
    public void updateChainTips() {
        // Mono<BlockchainInfo> bcinfo = bitcoinClient.getBlockchainInfo();

        logger.info("Calling update tips");

        tips.forEachKey(Long.MAX_VALUE, this::updateTip);

    }

    @Async
    public void updateTip(Blockchain blockchain) {

        logger.info("Updating client for " + blockchain);

        // check for increase?
        Long newTip = getClient(blockchain).getBlockchainInfo().block().getBlocks();
        tips.put(blockchain, newTip);

        logger.info("Updated client for " + blockchain + " to " + newTip);

    }


    private ReactiveClientImpl getClient(Blockchain blockchain) {
        switch (blockchain) {
            case DASH:
                return dashClient;
            case ZCASH:
                return zCashClient;
            case BITCOIN:
                return bitcoinClient;
            default:
                throw new RuntimeException("can't find client");
        }
    }

    public Long getTipFor(Blockchain blockchain) {
        return tips.get(blockchain);
    }

    public long getLastSyncedFor(Blockchain blockchain) {
        return lastSynced.get(blockchain);
    }


    public void setLastSyncedFor(Blockchain blockchain, Long tip) {

        lastSynced.put(blockchain, tip);
    }

}
