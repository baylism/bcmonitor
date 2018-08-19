package com.bcam.bcmonitor.model;

import com.bcam.bcmonitor.extractor.client.BitcoinClient;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.IOException;

public class TransactionInput {

    private String txid;
    private int vout;

    /**
     * strategy:
     * 1. look up transaction txid with getrawtransaction
     * 2. get output number vout
     * 3. get value from output vout
     *
     */
    @JsonIgnore
    public float getValue() throws IOException {
        // System.out.println("CALLING GETVALUE");
        BitcoinClient client = new BitcoinClient();
        BitcoinTransaction transaction = client.getTransaction(txid);
        TransactionOutput v = transaction.getVout(vout);
        return v.getValue();
    }


     // do value calculation later and resort to APIs if necessary.
     // monero API has for both input and output


    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getVout() {
        return vout;
    }

    public void setVout(int vout) {
        this.vout = vout;
    }
}
