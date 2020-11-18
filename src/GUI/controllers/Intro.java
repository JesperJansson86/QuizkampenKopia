package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
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
                System.out.println("In Intro: Putting in GetText toClient");
                toClient.put(nameField.getText());
                System.out.println("In Intro: Put text to Client");
                System.out.println("In Intro: Getting NextPane");
                String goNext = (String) toGUI.take();
                System.out.println("In Intro: Got NextPane");
//                util.changeScene("../view/ChooseCategory.fxml");
                util.changeScene(goNext);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
