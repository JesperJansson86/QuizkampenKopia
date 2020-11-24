package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/19/2020
 * Time: 09:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Waiting {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    @FXML
    public Label message;
    public Group circleGroup;
    public Circle circleRed;
    public Circle circleBlue;
    public Circle circleYellow;
    public Circle circleGreen;
    public AnchorPane waitingPane;
    public String nextPane;
    SequentialTransition sq;
    ScaleTransition st;
    GUIutils util;



    public void initialize() {
        try {
            message.setText((String) toGUI.take()); //Getting waiting-message.
            util = new GUIutils(waitingPane);
            groupTransition();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("In Waiting: Init done.");
        startThreadMethod();

    }

    private void circleSeq() {
        sq = new SequentialTransition();
        sq.setAutoReverse(true);
        sq.setCycleCount(Timeline.INDEFINITE);
        circleGroup.getChildren().forEach(circle -> {
            System.out.println("In Waiting: circleLoop");
            setScaleTrans();
            st.setNode(circle);
            sq.getChildren().add(st);
        });
        sq.play();
    }

    private void groupTransition() {
        TranslateTransition ts = new TranslateTransition(Duration.seconds(0.5), circleGroup);
        ts.setByY(circleGroup.getTranslateY() + 30);
        ts.setAutoReverse(true);
        ts.setCycleCount(2);
        ts.play();
        ts.setOnFinished(e -> circleSeq());
    }

    private void setScaleTrans() {
        st = new ScaleTransition();
        st.setByX(0.5);
        st.setByY(0.5);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.setDuration(Duration.seconds(0.5));
    }


    private void goToNextWindow(){
        try {
            util.changeSceneNew(GUIutils.nextWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startThreadMethod(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("In Waiting: take-thread running");
                // String temp = (String) toGUI.take();
                // nextPane = temp;
                while(true){
                System.out.println(GUIutils.nextWindow);
                if(!GUIutils.nextWindow.equalsIgnoreCase("WAITING")) {
                    break;
                }}
                goToNextWindow();
            }
        }).start();
    }
}
