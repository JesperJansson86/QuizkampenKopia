package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;

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
    GUIutils util;

    public void initialize() {

        category1group.setOnMousePressed(e -> {
            category1.setScaleY(100);//System.out.println("category1");
            goNextPanel();
        });
        category2group.setOnMousePressed(e -> {//System.out.println("category2");
            goNextPanel();
        });
        category3group.setOnMousePressed(e -> {//System.out.println("category3");
            goNextPanel();
        });

        util = new GUIutils(mainPane);
    }

    public void goNextPanel() {

        util.changeScene("../view/QuestionsPanel.fxml");
    }

}
