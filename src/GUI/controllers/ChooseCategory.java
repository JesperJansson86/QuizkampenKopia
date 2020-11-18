package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 20:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ChooseCategory {
    public Label categoryL1;
    public Arc category1;
    public Arc category3;
    public Label categoryL3;
    public Arc category2;
    public Label categoryL2;
    public Group categoriesCircle;
    public AnchorPane mainPane;
    public Group category1group;
    public Group category2group;
    public Group category3group;
    public Circle theCircle;
    GUIutils util;
    ScaleTransition st;
    public void initialize() {

        category1group.setOnMousePressed(e -> {

            buttonAnimation(category1group);
        });
        category2group.setOnMousePressed(e -> {
            buttonAnimation(category2group);
        });
        category3group.setOnMousePressed(e -> {
            buttonAnimation(category3group);
        });

        util = new GUIutils(mainPane);
    }

    public void goNextPanel() {

        util.changeScene("../view/QuestionsPanel.fxml");
    }

    public void buttonAnimation(Group group) {
        RotateTransition rt=new RotateTransition(Duration.millis(5000),theCircle);
        rt.setByAngle(9000);
        rt.play();
        group.toFront();
        st = new ScaleTransition(Duration.millis(1000), group);
        st.setByX(0.3);
        st.setByY(0.3);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        st.setOnFinished(e->goNextPanel()); //here we call to go next panel, here we should call to send to client/server, and wait response.
    }
}
