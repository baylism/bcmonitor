package com.bcam.bcmonitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public abstract class AbstractBlock {

    @Id
    @Indexed
    private String hash; // in RPC byte order

    private String prevBlockHash;

    // @Indexed
    private long height;
    private long timeStamp;
    private long sizeBytes;

    private BigDecimal difficulty;
    private long timeReceived;
    private ArrayList<String> minerHashes;

    private ArrayList<AbstractTransaction> transactions;
    private ArrayList<String> txids;

    public AbstractBlock(){
        minerHashes = new ArrayList<>();
        transactions = new ArrayList<>();
        txids = new ArrayList<>();
    }

    public AbstractBlock(String hash){
        this.hash = hash;
        minerHashes = new ArrayList<>();
        transactions = new ArrayList<>();
        txids = new ArrayList<>();
    }

    public AbstractBlock(String hash, long height){
        this.hash = hash;
        this.height = height;
        minerHashes = new ArrayList<>();
        transactions = new ArrayList<>();
        txids = new ArrayList<>();
    }


    public long getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(long timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevBlockHash() {
        return prevBlockHash;
    }

    public void setPrevBlockHash(String prevBlockHash) {
        this.prevBlockHash = prevBlockHash;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<String> getTxids() {
        return txids;
    }

    public void setTxids(ArrayList<String> txids) {
        this.txids = txids;
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

        AbstractBlock block = (AbstractBlock) o;
        return hash.equals(block.getHash());
    }
}
