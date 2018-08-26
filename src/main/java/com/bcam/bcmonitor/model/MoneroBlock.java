package com.bcam.bcmonitor.model;

import ch.qos.logback.core.joran.spi.DefaultClass;
import com.bcam.bcmonitor.api.BitcoinController;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
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
