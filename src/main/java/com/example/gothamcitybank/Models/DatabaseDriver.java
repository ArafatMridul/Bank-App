package com.example.gothamcitybank.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:bankData.db");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Client Section
     * */

    public ResultSet getClientData(String pAddress, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM  Clients WHERE PayeeAddress='"+pAddress+"' AND Password='"+password+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getTransactions(String pAddress, int limit) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender='"+pAddress+"' OR Receiver='"+pAddress+"' LIMIT "+limit+";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

//    Method to return Savings account Balance
    public double getSavingsAccountBalance(String pAddress) {
        Statement statement;
        ResultSet resultSet;
        double balance = 0.0;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
            balance = resultSet.getDouble("Balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

//    Method to Add or Subtract Balance
    public void updateBalance(String pAddress, double amount, String operation) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
            double newBalance;
            if (operation.equals("ADD")) {
                 newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE SavingsAccounts SET Balance="+newBalance+" WHERE Owner='"+pAddress+"';");
            } else {
                if(resultSet.getDouble("Balance") >= amount) {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("UPDATE SavingsAccounts SET Balance="+newBalance+" WHERE Owner='"+pAddress+"';");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    Creates and Records new transaction
    public void newTransaction(String sender, String receiver, double amount, String message) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            LocalDate date = LocalDate.now();
            statement.executeUpdate("INSERT INTO " +
                    "Transactions(Sender, Receiver, Amount, Date, Message)" +
                    "VALUES('"+sender+"', '"+receiver+"', "+amount+", '"+date+"', '"+message+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Admin Section
     * */

    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username='"+username+"' AND Password='"+password+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeQuery("INSERT INTO " +
                    "Clients(FirstName, LastName, PayeeAddress, Password, Date)" +
                    "VALUES('"+fName+"', '"+lName+"', '"+pAddress+"', '"+password+"', '"+date.toString()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String number, double tlimit, double balance) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeQuery("INSERT INTO " +
                    "CheckingAccounts(Owner, AccountNumber, TransactionLimit, Balance)" +
                    "VALUES ('"+owner+"', '"+number+"', '"+tlimit+"', '"+balance+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSavingsAccount(String owner, String number, double wlimit, double balance) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.executeQuery("INSERT INTO " +
                    "SavingsAccounts(Owner, AccountNumber, WithdrawalLimit, Balance)" +
                    "VALUES ('"+owner+"', '"+number+"', '"+wlimit+"', '"+balance+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllClientsData() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void depositSavings(String pAddress, double amount) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("UPDATE SavingsAccounts SET Balance='"+amount+"' WHERE Owner = '"+pAddress+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Utility Method Section
     * */
    public ResultSet searchClient(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress = '"+pAddress+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int getLastClientsId() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ResultSet getCheckingAccountsData(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner='"+pAddress+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateCheckingAccountBalance(String pAddress, double amount) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("UPDATE CheckingAccounts SET Balance='"+amount+"' WHERE Owner = '"+pAddress+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getSavingsAccountsData(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateSavingsAccountBalance(String pAddress, double amount) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("UPDATE SavingsAccounts SET Balance='"+amount+"' WHERE Owner = '"+pAddress+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
