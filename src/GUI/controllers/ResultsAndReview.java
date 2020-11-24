package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import MainClasses.GameRound;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
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
import java.util.concurrent.BlockingQueue;

import static javafx.scene.transform.Rotate.Y_AXIS;
import static javafx.scene.transform.Rotate.Z_AXIS;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ResultsAndReview {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;

    public Group player1Group;
    public Group player2Group;

    public Label player1L;
    public Label player2L;
    public Label player2PointsSum;
    public Label player1PointsSum;
    public Pane resultsPane;
    GUIutils util;
    Circle roundC;

    ImageView trophy=new ImageView(new Image(getClass().getResourceAsStream("../resources/trophy.png")));


    //Values for testing
    private List<Integer> p1PointsList = new ArrayList<>(); //this should be player1results from GameRound.
    private List<Integer> p2PointsList = new ArrayList<>();
    private List<String> playerNames =new ArrayList<>();
    public void initialize() {

        ///uncomment to communicate with client
         try {//HODEI TEST
             GameRound gr=(GameRound)toGUI.take();
             playerNames=gr.playerNames;
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

             //     playerNames=(ArrayList)toGUI.take();
       //  p1PointsList=(ArrayList)toGUI.take();
         //p2PointsList=(ArrayList)toGUI.take();
             } catch (InterruptedException e) {
           e.printStackTrace();
        }
        resultsPane.setOnMouseClicked(e-> {goNextPanel();
            System.out.println("BACK TO THE GAME!!!");
        });

        //here! take the real data and comment the test and fix the iteration
//values for test
        //creates a list of results of 2 rounds
/*
        p1PointsList.add(3);
        p1PointsList.add(2);
        p1PointsList.add(3);
        p1PointsList.add(3);

        p2PointsList.add(0);
        p2PointsList.add(1);
        p2PointsList.add(0);
        p2PointsList.add(2);

        playerNames.add("Hodei");//we need the names!
        playerNames.add("Einstein");

 */
        //adds the name
        player1L.setText(playerNames.get(0));
        player2L.setText(playerNames.get(1));

        //create by iteration
        for(int i=0;i<p1PointsList.size();i++)
            createResultsPerRound(i,p1PointsList.get(i),p2PointsList.get(i),i+1); //creates the rounds depending the list size

        //add summing points
        player1PointsSum.setText(String.valueOf(getPointSum(p1PointsList)));
        player2PointsSum.setText(String.valueOf(getPointSum(p2PointsList)));

        //animations
        fancyTransition();
        playerPanTransition();

        //-idea to add a trophy to the winner, just an experiment
        addCup();


        util=new GUIutils(resultsPane);
    }

    private void createResultsPerRound(int yPos,int p1Point,int p2Point,int roundNumb){
        Group roundG=new Group();
        roundG.setLayoutX(250);
        roundG.setLayoutY(180+(yPos*50)); //<-this changes per round


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

        p1Points.setText(String.valueOf(p1Point));//<-Here goes the p1 points

        Label roundNumbL=new Label();
        roundNumbL.setAlignment(Pos.CENTER);
        roundNumbL.setContentDisplay(ContentDisplay.CENTER);
        roundNumbL.setTranslateX(-17);
        roundNumbL.setTranslateY(-9);
        roundNumbL.setTextAlignment(TextAlignment.CENTER);
        roundNumbL.setWrapText(true);
        roundNumbL.setText(String.valueOf(roundNumb));//<-here goes the round number
        roundNumbL.setPrefHeight(18);
        roundNumbL.setPrefWidth(32);
        roundNumbL.setFont(new Font("System Bold",12));

        Label p2Points=new Label();
        p2Points.setPrefHeight(45);
        p2Points.setPrefWidth(20);
        p2Points.setTranslateX(130);
        p2Points.setTranslateY(-20);
        p2Points.setFont(new Font("System Bold",30));

        p2Points.setText(String.valueOf(p2Point));//<-Here goes the p2 points

        roundG.getChildren().addAll(roundC,p1Points,roundNumbL,p2Points);
        resultsPane.getChildren().add(roundG);

    }
    //playing with streams instead of a for or forEach
    private int getPointSum(List listToSum){
        int sum = listToSum.stream()
                .mapToInt(a -> (int)a)
                .sum();

        return sum;
    }
    //ANIMATIONS
    //a very dirty and silly method.
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
    private void fancyTransition(){
        RotateTransition rt=new RotateTransition();
        rt.setNode(roundC);
        rt.setAxis(Z_AXIS);
        rt.setByAngle(720);
        rt.setDuration(Duration.seconds(3));
        rt.play();
    }

    private void addCup(){
        trophy.setFitHeight(60);
        trophy.setPreserveRatio(true);
        trophy.setTranslateX(90);
        trophy.setTranslateY(20);
        if ((getPointSum(p1PointsList) > getPointSum(p2PointsList)))
            player1Group.getChildren().add(trophy);
        else if((getPointSum(p1PointsList) < getPointSum(p2PointsList)))
            player2Group.getChildren().add(trophy);
        else
            System.out.println("draw!");

    }
    //this will become part of an interface
    public void goNextPanel() {
//        try {
//            util.changeSceneNew("CATEGORY"); //<-to go to ChooseCategory
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try{
           // String nextPane = (String) toGUI.take();
            util.changeSceneNew(GUIutils.nextWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}