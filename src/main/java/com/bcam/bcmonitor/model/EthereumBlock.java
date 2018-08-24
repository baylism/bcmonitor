package com.bcam.bcmonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

/**
 * Ethereum: https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_gettransactionbyhash
 *
 *
 */
@Document
public class EthereumBlock extends AbstractBlock {
    private BigInteger gasLimit;
    private BigInteger gasUsed;

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public BigInteger getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(BigInteger gasUsed) {
        this.gasUsed = gasUsed;
    }
}
