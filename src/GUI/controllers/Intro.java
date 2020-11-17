package GUI.controllers;

import GUI.models.GUIutils;
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
    public void initialize(){
        message.setText(null);
       util =new GUIutils(mainPane);
    }
    public void startButtonOn(ActionEvent actionEvent) {
        if(nameField != null){
            //send to server and if its ok go to next page
            try {
                util.changeScene("../view/ChooseCategory.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
