package GUI.controllers;

import GUI.models.GUIutils;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
    GUIutils util;
    String nextScene;
    public void initialize(){
        groupTransition();

    }
    public void groupTransition(){

        TranslateTransition ts=new TranslateTransition(Duration.seconds(0.5),circleGroup);
        ts.setByY(circleGroup.getTranslateY()+30);
       ts.setAutoReverse(true);
        ts.setCycleCount(2);
        ts.play();
        ts.setOnFinished(e->circleTransition());
    }
    public void circleTransition(){
        List<Circle>circlesList=new ArrayList<Circle>();
        circlesList.add(circleBlue);
        circlesList.add(circleRed);
        circlesList.add(circleYellow);
        circlesList.add(circleGreen);
        ScaleTransition st=new ScaleTransition();
        st.setByX(1);
        st.setByY(1);
        st.setAutoReverse(true);
        st.setCycleCount(Timeline.INDEFINITE);
        st.setDuration(Duration.seconds(2));
        st.setNode(circlesList.get(0));
        st.setNode(circlesList.get(1));
        st.setNode(circlesList.get(2));
        st.setNode(circlesList.get(3));
        st.play();

    }
}
