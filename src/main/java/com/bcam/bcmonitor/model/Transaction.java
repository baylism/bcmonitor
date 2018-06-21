package com.bcam.bcmonitor.model;

import java.util.ArrayList;

public class Transaction {

    private ArrayList<AbstractTransaction> trans;

    private AbstractTransaction t;


    public Transaction(AbstractTransaction t) {
        this.t = t;
    }


}
