package com.bcam.bcmonitor.model;

import java.util.ArrayList;
import java.util.Objects;

public class TransactionPool {

    private ArrayList<String> poolHashes;

    public TransactionPool() {
        this.poolHashes = new ArrayList<>();
    }

    public void addTransaction(String hash) {
        poolHashes.add(hash);
    }

    public ArrayList<String> getPoolHashes() {
        return poolHashes;
    }

    public void setPoolHashes(ArrayList<String> poolHashes) {
        this.poolHashes = poolHashes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionPool that = (TransactionPool) o;
        return Objects.equals(poolHashes, that.poolHashes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(poolHashes);
    }

    @Override
    public String toString() {
        return "TransactionPool{" +
                "poolHashes=" + poolHashes +
                '}';
    }
}
