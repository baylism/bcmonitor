package com.bcam.bcmonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class EthereumTransaction extends AbstractTransaction {

    private String from;
    private String to;
    private long value; // in wei
    private long gasPrice; // in wei
    private long gas; // in wei

    @Override
    public float calculateValue() {
        return value;
    }

    @Override
    public float calculateFee() {
        return gas;
    }

    @Override
    public String to() {
        return to;
    }

    @Override
    public String from() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(long gasPrice) {
        this.gasPrice = gasPrice;
    }

    public long getGas() {
        return gas;
    }

    public void setGas(long gas) {
        this.gas = gas;
    }

    @Override
    public String toString() {
        return "EthereumTransaction{" +
                "hash='" + getHash() + '\'' +
                '}';
    }
}
