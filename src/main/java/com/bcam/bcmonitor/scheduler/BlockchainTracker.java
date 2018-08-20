package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.bcam.bcmonitor.model.Blockchain.BITCOIN;
import static com.bcam.bcmonitor.model.Blockchain.DASH;
import static com.bcam.bcmonitor.model.Blockchain.ZCASH;

/**
 * Class to keep track of current blockchain status
 */
@Component
public class BlockchainTracker {

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

    public void updateChainTips() {
        tips.put(BITCOIN, bitcoinClient.getBlockchainInfo().block().getBlocks());
        tips.put(DASH, dashClient.getBlockchainInfo().block().getBlocks());
        tips.put(ZCASH, zCashClient.getBlockchainInfo().block().getBlocks());
    }


    public Long getTipFor(Blockchain blockchain) {
        return tips.get(blockchain);
    }

    public long getLastSyncedFor(Blockchain blockchain) {
        return lastSynced.get(blockchain);
    }
}
