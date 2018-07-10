package com.bcam.bcmonitor.model;

import java.util.ArrayList;

public class TransactionPool {
    private ArrayList<String> poolHashes;

    public void addTransaction(String hash) {
        poolHashes.add(hash);
    }

}
