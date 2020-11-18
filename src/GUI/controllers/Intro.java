package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Intro {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    public Label message;
    public Button startB;
    public TextField nameField;
    public AnchorPane introPane;
    GUIutils util;

    public TextField getNameField() {
        return nameField;
    }


    public void initialize() {
        message.setText(null);
        util = new GUIutils(introPane);
    }

    public void startButtonOn() {
        if (!nameField.getText().isBlank() || !nameField.getText().isEmpty()) {
            //send to server and if its ok go to next page
            util.changeScene("../view/ChooseCategory.fxml");
           /* try {
                toClient.put(nameField.getText());
                if ((Boolean) toGUI.take())
                    util.changeScene("../view/ChooseCategory.fxml");
                else
                    message.setText("Something went very,very,very wrong...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            */


        } else
            message.setText("Write something in the name field, please.");
    }
}
