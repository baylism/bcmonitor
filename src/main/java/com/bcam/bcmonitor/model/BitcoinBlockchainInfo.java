package com.bcam.bcmonitor.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BitcoinBlockchainInfo {

        private String chain;
        private long blocks;
        private long headers;
        private String bestblockhash;
        private BigDecimal difficult;
        private long mediantime;
        private BigDecimal verificationprogress;
        private boolean initialblockdownload;
        private BigInteger chainwork;
        private long size_on_disk;
        private boolean pruned;

}
