package com.example.gothamcitybank.Models;

import com.example.gothamcitybank.Views.AccountType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String fname, String lname, String pAddress, Account cAccount, Account sAccount, LocalDate date ) {
        this.firstName = new SimpleStringProperty(this, "First Name", fname);
        this.lastName = new SimpleStringProperty(this, "Last Name", lname);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", pAddress);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", cAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", sAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date", date);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty pAddressProperty() {
        return payeeAddress;
    }
    public ObjectProperty<Account> checkingAccountProperty() {
        return checkingAccount;
    }
    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return dateCreated;
    }
}