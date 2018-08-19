package com.bcam.bcmonitor.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BlockchainInfo {

        // private String chain;
        private long blocks;
        // private long headers;
        private String bestblockhash;
        // private BigDecimal difficult;
        // private long mediantime;
        // private BigDecimal verificationprogress;
        // private boolean initialblockdownload;
        // private BigInteger chainwork;
        // private long sizeOnDisk;
        // private boolean pruned;


    public String getBestblockhash() {
        return bestblockhash;
    }

    public void setBestblockhash(String bestblockhash) {
        this.bestblockhash = bestblockhash;
    }

    public long getBlocks() {
        return blocks;
    }

    public void setBlocks(long blocks) {
        this.blocks = blocks;
    }
}
