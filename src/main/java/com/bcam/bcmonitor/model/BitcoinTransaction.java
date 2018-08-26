package com.bcam.bcmonitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.util.ArrayList;

@Document()
public class BitcoinTransaction extends AbstractTransaction {
    private float inputsSum;
    private float outputsSum;
    private int sizeBytes;

    private ArrayList<TransactionInput> vin;
    private ArrayList<TransactionOutput> vout;

    public BitcoinTransaction() {
        vin = new ArrayList<>();
        vout = new ArrayList<>();
    }

    public BitcoinTransaction(String hash) {
        super(hash);
    }

    // abstract method implementations
    @Override
    public float calculateValue() {
        return outputsSum;
    }

    @Override
    public float calculateFee() {
        return inputsSum - outputsSum;
    }

    @Override
    public String to() {
        return "TODO";
    }

    @Override
    public String from() {
        return "TODO";
    }


    // helpers
    void sumInputs() throws IOException {
        inputsSum = 0;
        for (TransactionInput i : vin) {
            inputsSum += i.getValue();
        }
    }

    void sumOutputs() {
        outputsSum = 0;
        for (TransactionOutput o : vout) {
            outputsSum += o.getValue();
        }
    }


    public int getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(int sizeBytes) {
        this.sizeBytes = sizeBytes;
    }


    /**
     * Get the transaction output with index
     * @param index the 'n' field in the transaction output
     * @return the TransactionOutput
     */
    public TransactionOutput getVout(int index) {
        // return vout.get(index);

        for (TransactionOutput t : vout) {
            if (t.getIndex() == index)
                return t;
        }

        throw new IndexOutOfBoundsException("Can't find transaction output " + index + "in transaction " + getHash());
    }

    public ArrayList<TransactionOutput> getVout() {
        return vout;
    }

    public ArrayList<TransactionInput> getVin() {
        return vin;
    }

    public void setVin(ArrayList<TransactionInput> vin) {
        this.vin = vin;
    }

    public void setVout(ArrayList<TransactionOutput> vout) {
        this.vout = vout;
    }

    @Override
    public String toString() {
        return "BitcoinTransaction{" +
                "hash='" + getHash() + '\'' +
                '}';
    }
}
