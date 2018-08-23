package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.*;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.Blockchain;
import com.bcam.bcmonitor.model.BlockchainInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.Map;
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
@Component
public class BlockchainTracker {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainTracker.class);

    private ConcurrentHashMap<Blockchain, Long> tips;
    private Map<Blockchain, Boolean> enableTracking;

    private ReactiveBitcoinClient bitcoinClient;
    private ReactiveDashClient dashClient;
    private ReactiveZCashClient zCashClient;

    @Autowired
    public BlockchainTracker(ReactiveBitcoinClient bitcoinClient, ReactiveDashClient dashClient, ReactiveZCashClient zCashClient) {

        this.bitcoinClient = bitcoinClient;
        this.dashClient = dashClient;
        this.zCashClient = zCashClient;

        tips = new ConcurrentHashMap<>();
        tips.put(BITCOIN, -1L);
        tips.put(DASH, -1L);
        tips.put(ZCASH, -1L);

        enableTracking = new HashMap<>();
        enableTracking.put(BITCOIN, Boolean.FALSE);
        enableTracking.put(DASH, Boolean.FALSE);
        enableTracking.put(ZCASH, Boolean.FALSE);

    }

    @Async
    public void updateChainTips() {

        // logger.info("Calling update tips");

        tips.forEachKey(Long.MAX_VALUE, this::updateTip);

    }

    @Async
    public void updateTip(Blockchain blockchain) {

        if (!enableTracking.get(blockchain)) {
            return;
        }

        logger.info("Updating tip for " + blockchain);

        // check for increase?
        try {
            Long newTip = getClient(blockchain).getBlockchainInfo().block().getBlocks();

            tips.put(blockchain, newTip);
            logger.info("Updated tip for " + blockchain + " to " + newTip);

        } catch (NullPointerException e){

            logger.info("Couldn't get tip " + e);
        }

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

    public void enableTrackingFor(Blockchain blockchain) {

        enableTracking.put(blockchain, Boolean.TRUE);
    }

    public void disableTrackingFor(Blockchain blockchain) {

        enableTracking.put(blockchain, Boolean.FALSE);
    }

    public ConcurrentHashMap<Blockchain, Long> getTips() {
        return tips;
    }

    public Map<Blockchain, Boolean> getEnableTracking() {
        return enableTracking;
    }
}
