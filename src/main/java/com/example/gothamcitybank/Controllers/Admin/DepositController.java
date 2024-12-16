package com.example.gothamcitybank.Controllers.Admin;

import com.example.gothamcitybank.Models.Client;
import com.example.gothamcitybank.Models.Model;
import com.example.gothamcitybank.Views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    public TextField pAddress_fld;
    public Button search_btn;
    public ListView<Client> result_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(e -> onClientSearch());
        deposit_btn.setOnAction(e -> onDeposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResults = Model.getInstance().searchClient(pAddress_fld.getText());
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(e -> new ClientCellFactory());
        client = searchResults.getFirst();
    }

    private void onDeposit() {
        try {
            // Parse the deposit amount
            double amount = Double.parseDouble(amount_fld.getText());

            // Get the current balance from the Client object
            double currentBalance = client.savingsAccountProperty().get().balanceProperty().get();

            // Calculate the new balance
            double newBalance = currentBalance + amount;

            // Update the balance in the database
            Model.getInstance().getDatabaseDriver().depositSavings(client.pAddressProperty().get(), newBalance);

            // Fetch the updated balance from the database
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().getSavingsAccountsData(client.pAddressProperty().get());
            if (resultSet.next()) {
                double updatedBalance = resultSet.getDouble("Balance");
                client.savingsAccountProperty().get().balanceProperty().set(updatedBalance);
            }

            // Clear input fields
            emptyFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void emptyFields() {
        pAddress_fld.setText("");
        amount_fld.setText("");
    }

}
