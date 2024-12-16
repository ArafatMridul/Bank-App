package com.example.gothamcitybank.Controllers.Client;

import com.example.gothamcitybank.Models.Model;
import com.example.gothamcitybank.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            if(newVal.equals(ClientMenuOptions.TRANSACTIONS)) {
                client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
            } else if(newVal.equals(ClientMenuOptions.ACCOUNTS)) {
                client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
            } else if(newVal.equals(ClientMenuOptions.PROFILE)) {
                client_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
            } else {
                client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
