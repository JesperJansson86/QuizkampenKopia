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

/**
 * Created by Hodei Eceiza
 * Date: 11/19/2020
 * Time: 09:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Waiting {
    @FXML
    public Label message;
    public Group circleGroup;
    public Circle circleRed;
    public Circle circleBlue;
    public Circle circleYellow;
    public Circle circleGreen;
    public AnchorPane waitingPane;

    private String nextPane;
    private SequentialTransition sq;
    private ScaleTransition st;
    private TranslateTransition ts;
    private GUIutils util;

    /**
     * initiates methods: sets the message put by Client in toGui, sets Pane for GUIutils
     * and runs startThreadMethod
     */
    public void initialize() {
        try {
            message.setText((String) Client.toGUI.take());
            util = new GUIutils(waitingPane);
            groupTransition();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startThreadMethod();

    }

    /**
     * Sequential indefinite transition animation of circles. sets scale transition to each circle.
     */
    private void circleSeq() {
        sq = new SequentialTransition();
        sq.setAutoReverse(true);
        sq.setCycleCount(Timeline.INDEFINITE);
        circleGroup.getChildren().forEach(circle -> {
            setScaleTrans();
            st.setNode(circle);
            sq.getChildren().add(st);
        });
        sq.play();
    }

    /**
     * translate transition for the circleGroup (moves them down and then up)
     */
    private void groupTransition() {
        ts = new TranslateTransition(Duration.seconds(0.5), circleGroup);
        ts.setByY(circleGroup.getTranslateY() + 30);
        ts.setAutoReverse(true);
        ts.setCycleCount(2);
        ts.play();
        ts.setOnFinished(e -> circleSeq());
    }

    /**
     * scale transition scales by half and goes back
     */
    private void setScaleTrans() {
        st = new ScaleTransition();
        st.setByX(0.5);
        st.setByY(0.5);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.setDuration(Duration.seconds(0.5));
    }

    /**
     * uses GUIutils changeSceneNew to load next scene
     */
    private void goToNextWindow() {
        try {
            util.changeSceneNew(nextPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * a thread which "picks up" from toGUI the reference for the next pane and
     * stops animations if they are running
     */
    private void startThreadMethod() {
        new Thread(() -> {
            try {
                nextPane = (String) Client.toGUI.take();
                if (ts.getStatus() == Animation.Status.RUNNING) ts.stop();
                else if (sq.getStatus() == Animation.Status.RUNNING) sq.stop();
                goToNextWindow();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
