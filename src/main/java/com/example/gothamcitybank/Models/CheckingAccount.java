package com.example.gothamcitybank.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account{

//    The number of transactions a client is allowed to do per day
    private final DoubleProperty transactionLimit;

    public CheckingAccount(String owner, String accountNumber, double balance, int tLimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleDoubleProperty(this, "Transaction limit", tLimit);
    }

    public DoubleProperty transactionLimitProperty() {
        return transactionLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
