package com.example.gothamcitybank.Controllers.Admin;

import com.example.gothamcitybank.Models.Model;
import com.example.gothamcitybank.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            if(newVal.equals(AdminMenuOptions.CLIENTS)){
                admin_parent.setCenter(Model.getInstance().getViewFactory().getClientView());
            } else if (newVal.equals(AdminMenuOptions.DEPOSIT)) {
                admin_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
            } else {
                admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        });
    }
}
