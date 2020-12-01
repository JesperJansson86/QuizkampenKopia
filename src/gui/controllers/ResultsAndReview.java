package gui.controllers;

import client.Client;
import gui.models.GUIutils;
import mainClasses.GameRound;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.transform.Rotate.Z_AXIS;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ResultsAndReview {

    public Group player1Group;
    public Group player2Group;

    public Label player1L;
    public Label player2L;
    public Label player2PointsSum;
    public Label player1PointsSum;
    public Pane resultsPane;

    private GUIutils util;
    private Circle roundC;
    private ImageView trophy=new ImageView(new Image(getClass().getResourceAsStream("../resources/trophy.png")));
    private List<Integer> p1PointsList = new ArrayList<>();
    private List<Integer> p2PointsList = new ArrayList<>();
    private List<String> playerNames =new ArrayList<>();

    /**
     * sets the data taken from toGUI, adds a mouseEvent to the main Pane, calculates the total points,
     * calls transition methods and initiates GUIutils.
     */
    public void initialize() {


         try {
             GameRound gr=(GameRound)Client.toGUI.take();
             playerNames=gr.playerNames;

             //a fix which sets to 0 points if the players score is not updated.
             if (gr.player1Score.isEmpty()) {
                 p1PointsList.add(0);
             } else if (gr.player1Score.size()<gr.player2Score.size()){
                 p1PointsList=gr.player1Score;
                 p1PointsList.add(0);
             }else {
                 p1PointsList=gr.player1Score;
             }

             if (gr.player2Score.isEmpty()) {
                 p2PointsList.add(0);

             } else if(gr.player2Score.size() < gr.player1Score.size()){
                 p2PointsList = gr.player2Score;
                 p2PointsList.add(0);
             } else {

                 p2PointsList=gr.player2Score;
             }

             } catch (InterruptedException e) {
           e.printStackTrace();
        }

        resultsPane.setOnMouseClicked(e-> goNextPanel());

        player1L.setText(playerNames.get(0));
        player2L.setText(playerNames.get(1));

        for(int i=0;i<p1PointsList.size();i++)
            createResultsPerRound(i,p1PointsList.get(i),p2PointsList.get(i),i+1);

        player1PointsSum.setText(String.valueOf(getPointSum(p1PointsList)));
        player2PointsSum.setText(String.valueOf(getPointSum(p2PointsList)));

        roundCircleTrans();
        playerPanTransition();

        addCup();


        util=new GUIutils(resultsPane);
    }

    /**
     * creates the nodes to show the results of a round (label for player1 points,label for player2 points, label for round number
     * and a circle for its background
     * @param yPos the position in the y axis
     * @param p1Point points of player 1
     * @param p2Point points of player 2
     * @param roundNumb the actual round number
     */
    private void createResultsPerRound(int yPos,int p1Point,int p2Point,int roundNumb){
        Group roundG=new Group();
        roundG.setLayoutX(250);
        roundG.setLayoutY(180+(yPos*50));


        roundC=new Circle();
        roundC.setRadius(20);
        roundC.setStrokeType(StrokeType.INSIDE);
        roundC.setStrokeWidth(7);
        roundC.setFill(Color.YELLOW);
        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(255,193,0,1)), new Stop(1, Color.rgb(255,255,0,1))};
        roundC.setStroke(new LinearGradient(0,0,0,10,false, CycleMethod.NO_CYCLE,stops));

        Label p1Points=new Label();
        p1Points.setPrefHeight(45);
        p1Points.setPrefWidth(20);
        p1Points.setTranslateX(-150);
        p1Points.setTranslateY(-20);
        p1Points.setFont(new Font("System Bold",30));

        p1Points.setText(String.valueOf(p1Point));

        Label roundNumbL=new Label();
        roundNumbL.setAlignment(Pos.CENTER);
        roundNumbL.setContentDisplay(ContentDisplay.CENTER);
        roundNumbL.setTranslateX(-17);
        roundNumbL.setTranslateY(-9);
        roundNumbL.setTextAlignment(TextAlignment.CENTER);
        roundNumbL.setWrapText(true);
        roundNumbL.setText(String.valueOf(roundNumb));
        roundNumbL.setPrefHeight(18);
        roundNumbL.setPrefWidth(32);
        roundNumbL.setFont(new Font("System Bold",12));

        Label p2Points=new Label();
        p2Points.setPrefHeight(45);
        p2Points.setPrefWidth(20);
        p2Points.setTranslateX(130);
        p2Points.setTranslateY(-20);
        p2Points.setFont(new Font("System Bold",30));

        p2Points.setText(String.valueOf(p2Point));

        roundG.getChildren().addAll(roundC,p1Points,roundNumbL,p2Points);
        resultsPane.getChildren().add(roundG);

    }

    /**
     * Takes a list of Integers and sums them
     * @param listToSum the list to calculate
     * @return the sum of all integer elements
     */
    private int getPointSum(List<Integer> listToSum){
        return listToSum.stream()
                .mapToInt(a -> a)
                .sum();


    }

    /**
     * Transition for the rectangles of each player
     */
    private void playerPanTransition(){
        TranslateTransition ts=new TranslateTransition();
        ts.setFromX(-200);
        ts.setDuration(Duration.seconds(2));
        ts.setNode(player1Group);
        ts.setToX(player1Group.getTranslateX());
        ts.play();
        TranslateTransition ts2=new TranslateTransition();
        ts2.setFromX(200);
        ts2.setDuration(Duration.seconds(2));
        ts2.setNode(player2Group);
        ts2.setToX(player2Group.getTranslateX());
        ts2.play();
    }

    /**
     * rotate transition for the background circle in the round number
     */
    private void roundCircleTrans(){
        RotateTransition rt=new RotateTransition();
        rt.setNode(roundC);
        rt.setAxis(Z_AXIS);
        rt.setByAngle(720);
        rt.setDuration(Duration.seconds(3));
        rt.play();
    }

    /**
     * adds the tropy.png to the player group depending in players result
     */
    private void addCup(){
        trophy.setFitHeight(60);
        trophy.setPreserveRatio(true);
        trophy.setTranslateX(90);
        trophy.setTranslateY(20);
        if ((getPointSum(p1PointsList) > getPointSum(p2PointsList)))
            player1Group.getChildren().add(trophy);
        else
            player2Group.getChildren().add(trophy);
    }

    /**
     * calls GUIutils changeSceneNew method
     */
    public void goNextPanel() {

        try{
            String nextPane = (String) Client.toGUI.take();
            util.changeSceneNew(nextPane);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }


    }
}