package com.example.gothamcitybank.Controllers.Client;

import com.example.gothamcitybank.Models.Client;
import com.example.gothamcitybank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sc_acc_date;
    public Label sv_acc_bal;
    public TextField amount_to_sv;
    public Button trans_to_sv_btn;
    public TextField amount_to_ch;
    public Button trans_to_ch_btn;
    public Label err_sv;
    public Label err_ch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ch_acc_num.setText(Model.getInstance().getClient().checkingAccountProperty().get().toString());
        sv_acc_num.setText(Model.getInstance().getClient().savingsAccountProperty().get().toString());

        ch_acc_date.setText(Model.getInstance().getClient().dateProperty().get().toString());
        sc_acc_date.setText(Model.getInstance().getClient().dateProperty().get().toString());

        ch_acc_bal.setText(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().get() + "");
        sv_acc_bal.setText(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().get() + "");

        trans_to_sv_btn.setOnAction(event -> transferToSavingsAccount());
        trans_to_ch_btn.setOnAction(event -> transferToCheckingAccount());
    }

    private void transferToSavingsAccount() {
        try {
            String transactionLimitText = transaction_limit.getText();
            String amountText = amount_to_sv.getText();

            // Parse the amount to a double
            double amount = Double.parseDouble(amountText);
            double tlimit = Double.parseDouble(transactionLimitText);

            // Get the client, checking account, and savings account
            Client client = Model.getInstance().getClient();
            double checkingBalance = client.checkingAccountProperty().get().balanceProperty().get();
            double savingsBalance = client.savingsAccountProperty().get().balanceProperty().get();

            if(amount >= tlimit) {
                err_sv.setText("");
                // Perform the transfer
                double newCBal = checkingBalance - amount;
                double newSBal = savingsBalance + amount;
                client.checkingAccountProperty().get().setBalance(newCBal);
                client.savingsAccountProperty().get().setBalance(newSBal);
                Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(Model.getInstance().getClient().pAddressProperty().get(), newSBal);
                Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(Model.getInstance().getClient().pAddressProperty().get(), newCBal);

                // Format the balances to two decimal points
                DecimalFormat df = new DecimalFormat("#.00");
                ch_acc_bal.setText(df.format(client.checkingAccountProperty().get().balanceProperty().get()));
                sv_acc_bal.setText(df.format(client.savingsAccountProperty().get().balanceProperty().get()));

                // Clear the input field
                amount_to_sv.clear();
            } else {
                amount_to_sv.clear();
                err_sv.setText("Enter Correct Amount");
                err_sv.setStyle("-fx-text-fill: red");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transferToCheckingAccount() {
        try {
            String withdrawalLimitText = withdrawal_limit.getText().strip();
            String amountText = amount_to_ch.getText();

            // Parse the amount to a double
            double amount = Double.parseDouble(amountText);
            double wlimit = Double.parseDouble(withdrawalLimitText);

            // Get the client, checking account, and savings account
            Client client = Model.getInstance().getClient();
            double checkingBalance = client.checkingAccountProperty().get().balanceProperty().get();
            double savingsBalance = client.savingsAccountProperty().get().balanceProperty().get();

            if(amount >= wlimit) {
                err_ch.setText("");
                // Perform the transfer
                double newCBal = checkingBalance + amount;
                double newSBal = savingsBalance - amount;
                client.checkingAccountProperty().get().setBalance(newCBal);
                client.savingsAccountProperty().get().setBalance(newSBal);
                Model.getInstance().getDatabaseDriver().updateSavingsAccountBalance(Model.getInstance().getClient().pAddressProperty().get(), newSBal);
                Model.getInstance().getDatabaseDriver().updateCheckingAccountBalance(Model.getInstance().getClient().pAddressProperty().get(), newCBal);

                // Format the balances to two decimal points
                DecimalFormat df = new DecimalFormat("#.00");
                ch_acc_bal.setText(df.format(client.checkingAccountProperty().get().balanceProperty().get()));
                sv_acc_bal.setText(df.format(client.savingsAccountProperty().get().balanceProperty().get()));

                // Clear the input field
                amount_to_ch.clear();
            } else {
                amount_to_sv.clear();
                err_ch.setText("Enter Correct Amount");
                err_ch.setStyle("-fx-text-fill: red");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
