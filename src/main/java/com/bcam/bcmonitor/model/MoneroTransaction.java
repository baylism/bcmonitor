package com.bcam.bcmonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MoneroTransaction extends BitcoinTransaction {


    public MoneroTransaction() {
    }

    public MoneroTransaction(String hash) {
        super(hash);
    }
    @Override
    public String toString() {
        return "MoneroTransaction{" +
                "hash='" + getHash() + '\'' +
                '}';
    }
}
