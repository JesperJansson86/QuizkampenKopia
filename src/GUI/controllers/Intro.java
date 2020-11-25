package GUI.controllers;

import Client.Client;
import GUI.models.DatabaseConnection;
import GUI.models.GUIutils;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Intro {
    public TextField passwordField;
    public Button startB1;
    public TextField usernameField;
    public Button loginButton;
    public Button continueButton;
    public Button registerButton;
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    //public Label message;
   // public Button startB;
    public TextField nameField;
    public AnchorPane introPane;
    public UUID uuid;
    GUIutils util;

    public void initialize() {
        //message.setText(null);
        util = new GUIutils(introPane);
    }


    public void loginButtonOn(ActionEvent actionEvent) {

        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            DatabaseConnection connectNow = new DatabaseConnection();
            System.out.println(connectNow.databaseLink);
            System.out.println(connectNow.getConnection().toString());
            Connection connectDb = connectNow.getConnection();
            System.out.println(connectDb);

            String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameField.getText() + "' AND password ='" + passwordField.getText() + "'";

            System.out.println(usernameField.getText());
            System.out.println(passwordField.getText());
            try {
                System.out.println("HERE1");
                Statement statement = connectDb.createStatement();

                ResultSet queryResult = statement.executeQuery(verifyLogin);
                System.out.println("Inside try");
                System.out.println(queryResult);

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        System.out.println("Logged in successfully");
                    } else {
                        System.out.println("Invalid login");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("nooooot");
            }
        }


    }


    public void continueButtonOn(ActionEvent actionEvent) {
        uuid = UUID.randomUUID();
        try {
            toClient.put(uuid.toString());
            String nextScene = (String) toGUI.take();
            util.changeSceneNew(nextScene);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            System.out.println("Något gick fel");
        }
    }

    public void registerButtonOn(ActionEvent actionEvent) {
    }


}
