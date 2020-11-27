package GUI.controllers;

import ClientV2.Client;
import GUI.models.GUIutils;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 20:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ChooseCategory {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    public List<String> categoryList = Client.gr.categoryList;
    String category;
    public Label categoryL1;
    public Arc category1;
    public Arc category3;
    public Label categoryL3;
    public Label categoryL2;
    public Group categoriesCircle;
    public AnchorPane mainPane;
    public Group category1group;
    public Group category2group;
    public Group category3group;
    public Circle theCircle;

    GUIutils util;
    ScaleTransition st;

    /**
     * initializes setting text to category Labels, adding MouseEvent for each category group and initating GUIutils
     */
    public void initialize() {

        categoryL1.setText(categoryList.get(0));
        categoryL2.setText(categoryList.get(1));
        categoryL3.setText(categoryList.get(2));

        setGroupUserInteraction(true);

        category1group.setOnMousePressed(e -> {
            category = categoryList.get(0);
            buttonAnimation(category1group);
        });
        category2group.setOnMousePressed(e -> {
            category = categoryList.get(1);
            buttonAnimation(category2group);
        });
        category3group.setOnMousePressed(e -> {
            category = categoryList.get(2);
            buttonAnimation(category3group);
        });

        util = new GUIutils(mainPane);
    }

    /**
     * uses GUIutils changeSceneNew() to load the scene set added by Client in toGUI
     */
    private void goNextPanel() {

        try {
            String nextPane = (String) toGUI.take();
            util.changeSceneNew(nextPane);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds to toClient the category selected and
     * animates theCircle with a rotateTransition
     * and applies a ScaleTransition to Group assigned in the parameter
     * @param group a javafx group
     */
    private void buttonAnimation(Group group) {

        setGroupUserInteraction(false);

        try {
            Client.toServer.put(category);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RotateTransition rt = new RotateTransition(Duration.millis(5000), theCircle);
        rt.setByAngle(9000);
        rt.play();
        group.toFront();
        st = new ScaleTransition(Duration.millis(1000), group);
        st.setByX(0.3);
        st.setByY(0.3);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        st.setOnFinished(e -> goNextPanel());
    }

    /**
     * Sets disable depending on the status sent in the argument
     * @param status a boolean
     */
    private void setGroupUserInteraction(boolean status) {
        if (status) {
            category1group.setDisable(false);
            category2group.setDisable(false);
            category3group.setDisable(false);
        } else {
            category1group.setDisable(true);
            category2group.setDisable(true);
            category3group.setDisable(true);
        }
    }
}
