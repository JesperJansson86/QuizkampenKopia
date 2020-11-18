package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
    public AnchorPane mainPane;
    GUIutils util;

    public TextField getNameField() {
        return nameField;
    }


    public void initialize(){


        message.setText(null);
       util =new GUIutils(mainPane);
    }
    public void startButtonOn() {
        if(!nameField.getText().isBlank() || !nameField.getText().isEmpty()){
            //send to server and if its ok go to next page

                util.changeScene("../view/ChooseCategory.fxml");
        }
        else
            message.setText("Write something in the name field, please.");
    }
}
