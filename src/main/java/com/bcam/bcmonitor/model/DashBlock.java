package com.bcam.bcmonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class DashBlock extends BitcoinBlock {

    public DashBlock() {
    }

    public DashBlock(String hash, long height) {
        super(hash, height);
    }

    @Override
    public String toString() {
        return "DashBlock{ hash = " + getHash() + ", height = " + getHeight() + "}";
    }
}
