package GUI.controllers;

import GUI.models.DatabaseConnection;
import GUI.models.GUIutils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class Register {
    public TextField enterUsernameField;
    public TextField enterPasswordField;
    public TextField confirmPasswordField;
    public Button goBackButton;
    public Button confirmButton;
    public AnchorPane registerPane;
    public Label notificationLabel;

    GUIutils util;

    public void initialize() {
        util = new GUIutils(registerPane);
    }

    public void confirmButtonOn(ActionEvent actionEvent) {
        if (!enterUsernameField.getText().isEmpty() && !enterPasswordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {

            if (enterPasswordField.getText().equals(confirmPasswordField.getText())) {
                notificationLabel.setText("Passwords is a match!");
                registerNewUser();

            } else {
                notificationLabel.setText("Passwords does not match. Try again.");
            }
        } else {
            notificationLabel.setText("Fields cannot be empty");
        }

    }

    public void goBackButtonOn(ActionEvent actionEvent) {
        try {
            util.changeScene("../view/Intro.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerNewUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String username = enterUsernameField.getText();
        String password = enterPasswordField.getText();

        String insertField = "INSERT INTO user_account(username, password) VALUES('" + username + "','" + password + "')";

        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(insertField);
            System.out.println("Account with username: " + username + " and password: " + password + " registered successfully");
            notificationLabel.setText("Account created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Username in use.");
            notificationLabel.setText("Username is use.");
        }

    }
}
