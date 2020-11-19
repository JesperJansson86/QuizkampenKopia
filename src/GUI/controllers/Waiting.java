package GUI.controllers;

import GUI.models.GUIutils;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hodei Eceiza
 * Date: 11/19/2020
 * Time: 09:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Waiting {
    public Group circleGroup;
    public Circle circleRed;
    public Circle circleBlue;
    public Circle circleYellow;
    public Circle circleGreen;
    public AnchorPane waitingPane;
    SequentialTransition sq;
    ScaleTransition st;
    public void initialize() {
        groupTransition();
    }
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

    private void groupTransition() {

        TranslateTransition ts = new TranslateTransition(Duration.seconds(0.5), circleGroup);
        ts.setByY(circleGroup.getTranslateY() + 30);
        ts.setAutoReverse(true);
        ts.setCycleCount(2);
        ts.play();
        ts.setOnFinished(e -> circleSeq());
    }
    private void setScaleTrans(){
        st = new ScaleTransition();
        st.setByX(0.5);
        st.setByY(0.5);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.setDuration(Duration.seconds(0.5));
    }
}
