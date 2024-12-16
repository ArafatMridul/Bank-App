package com.example.gothamcitybank.Controllers.Client;

import com.example.gothamcitybank.Models.Model;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable {
    public Text fName_lbl;
    public Text lName_lbl;
    public Text acc_num;
    public Text creation_date;
    public Text profile_name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profile_name.textProperty().bind(Bindings.concat(Model.getInstance().getClient().firstNameProperty()).concat("'s Account"));
        fName_lbl.textProperty().bind(Model.getInstance().getClient().firstNameProperty());
        lName_lbl.textProperty().bind(Model.getInstance().getClient().lastNameProperty());

        creation_date.textProperty().bind(Model.getInstance().getClient().dateProperty().asString());
        acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().asString());
    }
}
