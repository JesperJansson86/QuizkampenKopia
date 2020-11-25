package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import MainClasses.GameRoundStatic;
import MainClasses.INotify;
import MainClasses.IObserve;
import MainClasses.MessageToClient;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 20:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ChooseCategory implements INotify {
    IObserve o=new MessageToClient();
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    public List<String> categoryList = new ArrayList<>();
    String category;
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

            categoryList = GameRoundStatic.categoryList;
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


    public void buttonAnimation(Group group) {

        setGroupUserInteraction(false);

       GameRoundStatic.category=category;

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
        st.setOnFinished(e-> {
            try {
                notifyObserver("CATEGORY");
                util.changeSceneNew(GUIutils.nextWindow);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }); //here we call to go next panel, here we should call to send to client/server, and wait response.
    }

    public void setGroupUserInteraction(boolean status) {
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

    @Override
    public void addObserver(IObserve o) {
        this.o=o;
    }

    @Override
    public void notifyObserver(String str) {
        o.update(str);
    }
}
