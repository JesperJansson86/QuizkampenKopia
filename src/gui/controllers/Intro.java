package gui.controllers;

import client.Client;
import gui.models.GUIutils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Intro {

    public Label message;
    public Button startB;
    public TextField nameField;
    public AnchorPane introPane;
   private GUIutils util;

    /**
     * sets the messages string to null and initiates GUIutils with controllers AnchorPane as argument
     */
    public void initialize() {
        message.setText(null);
        util = new GUIutils(introPane);
    }

    /**
     * startB buttons action event.
     * check if nameField is not empty and puts the nameField text for Client.toClient blockingqueue, then goes to the next
     * window defined by Client.toGui.
     * if nageField is empty, shows a message.
     */
    public void startButtonOn() {

        if (!nameField.getText().isBlank() || !nameField.getText().isEmpty()) {
            try {
                Client.toClient.put(nameField.getText());
                String nextScene = (String) Client.toGUI.take();
                util.changeSceneNew(nextScene);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {
            message.setText("Write something in the name field, please.");

        }
    }
}
