package com.bcam.bcmonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Input treestate of a block is the output treestate of the final transaction in the previous block
 * Output treestate of a block is the output treestate of its final transaction
 *
 */
@Document
public class ZCashBlock extends BitcoinBlock {

    public ZCashBlock() {
    }

    public ZCashBlock(String hash, long height) {
        super(hash, height);
    }

    @Override
    public String toString() {
        return "ZCashBlock{ hash = " + getHash() + ", height = " + getHeight() + "}";
    }
}
