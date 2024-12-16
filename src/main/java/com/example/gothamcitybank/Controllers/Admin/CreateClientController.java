package com.example.gothamcitybank.Controllers.Admin;

import com.example.gothamcitybank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.constant.ModuleDesc;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public CheckBox pAddress_box;
    public Label pAddress_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;

    private String payeeAddress;
    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(event -> createClient());
        pAddress_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        });

        ch_acc_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                createCheckingAccountFlag = true;
            }
        });

        sv_acc_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                createSavingsAccountFlag = true;
            }
        });
    }

    private void createClient() {
//        Create the checking account
        if(createCheckingAccountFlag) {
            createAccount("Checking");
        }
//        Create the savings account
        if(createSavingsAccountFlag) {
            createAccount("Savings");
        }
//        Create client
        String fname = fName_fld.getText();
        String lname = lName_fld.getText();
        String password = password_fld.getText();
        Model.getInstance().getDatabaseDriver().createClient(fname, lname, payeeAddress, password, LocalDate.now());
        error_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold;");
        error_lbl.setText("Client created successfully!");
        emptyFields();

    }

    private void createAccount(String accountType) {
//        generate account number
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999) + 1000);
        String accountNumber = firstSection + " " + lastSection;

//        Create the account
        if(accountType.equals("Checking")) {
            double balance = Double.parseDouble(ch_amount_fld.getText());
            Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber, 10, balance);
        } else {
            double balance = Double.parseDouble(sv_amount_fld.getText());
            Model.getInstance().getDatabaseDriver().createSavingsAccount(payeeAddress, accountNumber, 2000, balance);
        }
    }

    private void onCreatePayeeAddress() {
        if(fName_fld.getText() != null && lName_fld.getText() != null) {
            pAddress_lbl.setText(payeeAddress);
        }
    }

    private String createPayeeAddress() {
        int id = Model.getInstance().getDatabaseDriver().getLastClientsId()+1;
        char fChar = Character.toLowerCase(fName_fld.getCharacters().charAt(0));

        String newPAdd = "@"+fChar+lName_fld.getText()+id;
        return newPAdd;
    }

    private void emptyFields() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        pAddress_box.setSelected(false);
        ch_acc_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_acc_box.setSelected(false);
        sv_amount_fld.setText("");
    }

}
