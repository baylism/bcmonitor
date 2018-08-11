package com.bcam.bcmonitor.model;

public abstract class AbstractTransaction {
    private String hash;
    private String blockHash;

    private int timeReceived;
    private int timeInBlock;
    private int timeConfirmed;


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

    public int getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(int timeReceived) {
        this.timeReceived = timeReceived;
    }

    public int getTimeInBlock() {
        return timeInBlock;
    }

    public void setTimeInBlock(int timeInBlock) {
        this.timeInBlock = timeInBlock;
    }

    public int getTimeConfirmed() {
        return timeConfirmed;
    }

    public void setTimeConfirmed(int timeConfirmed) {
        this.timeConfirmed = timeConfirmed;
    }


    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
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
