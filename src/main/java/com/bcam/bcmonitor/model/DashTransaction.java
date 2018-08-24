package com.bcam.bcmonitor.model;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DashTransaction extends BitcoinTransaction {

    public DashTransaction() {
    }

    public DashTransaction(String hash) {
        super(hash);
    }
}
