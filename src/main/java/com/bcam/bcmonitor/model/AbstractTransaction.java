package com.bcam.bcmonitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class AbstractTransaction {

    @Id
    private String hash;
    private String blockHash;

    private long timeReceived;
    private long timeInBlock;
    private long timeConfirmed;

    public AbstractTransaction(String hash) {
        this.hash = hash;
    }

    public AbstractTransaction() {
        // initialise attributes currently not supported by mapper
        timeReceived = 0;
        timeInBlock = 0;
        timeConfirmed = 0;
    }

    // abstract methods
    public abstract float calculateValue();
    public abstract float calculateFee();
    public abstract String to();
    public abstract String from();

    // concrete methods
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(long timeReceived) {
        this.timeReceived = timeReceived;
    }

    public long getTimeInBlock() {
        return timeInBlock;
    }

    public void setTimeInBlock(long timeInBlock) {
        this.timeInBlock = timeInBlock;
    }

    public long getTimeConfirmed() {
        return timeConfirmed;
    }

    public void setTimeConfirmed(long timeConfirmed) {
        this.timeConfirmed = timeConfirmed;
    }


    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }


    @Override
    public String toString() {
        return "AbstractTransaction{" +
                "hash='" + hash + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;

        // null check
        if (o == null)
            return false;

        // type check and cast
        if (getClass() != o.getClass())
            return false;

        AbstractTransaction transaction = (AbstractTransaction) o;
        return hash.equals(transaction.getHash());
    }
}
