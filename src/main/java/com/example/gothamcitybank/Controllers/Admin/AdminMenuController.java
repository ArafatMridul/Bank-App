package com.example.gothamcitybank.Controllers.Admin;

import com.example.gothamcitybank.Models.Model;
import com.example.gothamcitybank.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        create_client_btn.setOnAction(e -> onCreateClient());
        clients_btn.setOnAction(e -> onClients());
        deposit_btn.setOnAction(e -> onDeposit());
        logout_btn.setOnAction(e -> onLogout());
    }

    private void onCreateClient() {
        Model.getInstance().getViewFactory().setAdminSelectedMenuItem(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onClients() {
        Model.getInstance().getViewFactory().setAdminSelectedMenuItem(AdminMenuOptions.CLIENTS);
    }

    private void onDeposit() {
        Model.getInstance().getViewFactory().setAdminSelectedMenuItem(AdminMenuOptions.DEPOSIT);
    }

    private void onLogout() {
//        Get Stage
        Stage stage = (Stage) clients_btn.getScene().getWindow();
//        Close admin window
        Model.getInstance().getViewFactory().closeStage(stage);
//        Show login window
        Model.getInstance().getViewFactory().showLoginWindow();
//        Set login success flag to false
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}
