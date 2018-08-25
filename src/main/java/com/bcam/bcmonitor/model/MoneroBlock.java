package com.bcam.bcmonitor.model;

import com.bcam.bcmonitor.api.BitcoinController;

public class MoneroBlock extends BitcoinBlock {

    public MoneroBlock() {
    }

    public MoneroBlock(String hash, long height) {
        super(hash, height);
    }

    @Override
    public String toString() {
        return "MoneroBlock{ hash = " + getHash() + ", height = " + getHeight() + "}";
    }
}
