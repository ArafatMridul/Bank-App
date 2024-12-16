package com.example.gothamcitybank.Controllers.Client;

import com.example.gothamcitybank.Models.Model;
import com.example.gothamcitybank.Views.ClientMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transactions_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboard_btn.setOnAction(event -> onDashboard());
        transactions_btn.setOnAction(event -> onTransactions());
        accounts_btn.setOnAction(event -> onAccounts());
        logout_btn.setOnAction(event -> onLogout());
        profile_btn.setOnAction(event -> onProfile());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().setClientSelectedMenuItem(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions() {
        Model.getInstance().getViewFactory().setClientSelectedMenuItem(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().setClientSelectedMenuItem(ClientMenuOptions.ACCOUNTS);
    }

    private void onProfile() {
        Model.getInstance().getViewFactory().setClientSelectedMenuItem(ClientMenuOptions.PROFILE);
    }

    private void onLogout() {
//        Get Stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
//        Close client window
        Model.getInstance().getViewFactory().closeStage(stage);
//        Show login window
        Model.getInstance().getViewFactory().showLoginWindow();
//        Set login success flag to false
        Model.getInstance().setClientLoginSuccessFlag(false);
    }
}
