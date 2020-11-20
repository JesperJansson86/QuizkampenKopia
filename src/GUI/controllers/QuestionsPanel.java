package GUI.controllers;

import Client.Client;
import MainClasses.Question;
import GUI.models.GUIutils;
import GUI.models.QuestionsPanelModel;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;


import static javafx.scene.paint.Color.TRANSPARENT;


public class QuestionsPanel {
    private static final String GREEN_BCK = "-fx-background-color: #00be00";
    private static final String GREEN_FILL = "-fx-fill: #00be00";
    private static final String RED_FILL = "-fx-background-color:red";
    private static final String RED_BCK = "-fx-fill: red";
    public Button answer1;
    public Button answer2;
    public Button answer3;
    public Button answer4;
    public Label question;
    public AnchorPane mainPane;

    public Label roundNumber;
    public ProgressBar timeLeftBar;
    public Rectangle rectangleQ;

    //this should come from somewhere else (game class??)
   // int questionsAmount;//<-to set the amount of result rectangles and the questions rounds.
    int actualRound;//<-to set the actual round number
    Group resultRects;

    QuestionsPanelModel model;
    @FXML
    Label categoryL;

    GUIutils utils;
    Timeline roundTime;
    KeyFrame setQuestions;
    KeyFrame cleanQuestions;
    int secondsToAnswer = 5;

    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    public ArrayList<Question> qList;

    /**
     * at start initiates questionList by copying the list from MainClasses.QuestionFactory
     */

    public void initialize() {
        System.out.println("In QuestionsPanel: init begins.");
        try {
            qList = (ArrayList<Question>) toGUI.take();
            actualRound = (Integer) toGUI.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //questionsAmount=qList.size();//<-The question amount is the size of the questions?
        System.out.println("In QuestionsPanel: got questions");
        System.out.println(qList.toString());


        model = new QuestionsPanelModel(qList, answer1, answer2, answer3, answer4, categoryL, question);

        roundNumber.setText(String.valueOf(actualRound));
        utils = new GUIutils(mainPane);
        timeLeftBar.setProgress(1);
        createResultsRect();
        setRoundTimes(secondsToAnswer, qList.size());


    }

    public void timeBar(int seconds) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timeLeftBar.progressProperty(), 1)),
                new KeyFrame(Duration.seconds(seconds), e -> {
                }, new KeyValue(timeLeftBar.progressProperty(), 0)));
        timeline.play();
    }

    public void setRoundTimes(int seconds, int rounds) {
        roundTime = new Timeline(

                setQuestions = new KeyFrame(Duration.ZERO, "setQuestions", e -> {
                    buttonsDisable(false);
                    model.reset();
                    resetCardAnimation();
                    model.setOnStage();
                    cardAnimation();
                    timeBar(seconds);
                    System.out.println("setquesstions");
                }),
                cleanQuestions = new KeyFrame(Duration.seconds(seconds), "clearQuestions", e -> {
                    model.reset();
                    resetCardAnimation();
                    System.out.println("clearquestions");
                }));

        roundTime.setCycleCount(rounds);
        roundTime.play();
        roundTime.setOnFinished(e -> nextWindow());
    }


    /**
     * actionlistener of question options. if its right it paints green, if not it paints red and paints green right answer
     *
     * @param actionEvent
     */
    public void answerOn(ActionEvent actionEvent) {
        Button buttonCLicked = ((Button) actionEvent.getSource());
        if (buttonCLicked.getText().equals(model.getQ().getRightAnswer())) {
            buttonCLicked.setStyle(GREEN_BCK);
            transition(buttonCLicked, null);

            resultRects.getChildren().get(model.getQuestionList().size()).setStyle(GREEN_FILL);


            buttonsDisable(true);
            jumpToNextQuestion();

        } else if (!buttonCLicked.getText().equals(model.getQ().getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color:red");
            transition(model.getRight(), GREEN_BCK);
            transition(buttonCLicked, null);
            resultRects.getChildren().get(model.getQuestionList().size()).setStyle("-fx-fill:red");
            buttonsDisable(true);
            jumpToNextQuestion();
        }

    }

    public void jumpToNextQuestion() {


        if (roundTime.getCycleCount() > 1) {
            roundTime.setDelay(Duration.seconds(2));
            roundTime.stop();
            roundTime.setCycleCount(roundTime.getCycleCount() - 1);
            roundTime.playFromStart();
        } else {
            roundTime.setDelay(Duration.seconds(2));
            roundTime.stop();
            roundTime.playFrom("clearQuestions");

        }
    }

    public void reset() {
        model.reset();
    }

    public void buttonsDisable(boolean disabled) {
        for (Button answerB : model.getButtons())
            answerB.setDisable(disabled);
    }

    //building
    private void createResultsRect() {
        Group gr = new Group();
        for (int i = qList.size(); i > 0; i--) {
            Rectangle rect = new Rectangle();
            rect.setArcHeight(5);
            rect.setArcWidth(5);
            rect.setFill(Color.WHITE);
            rect.setHeight(26);
            rect.setWidth(28);
            rect.setLayoutX(20 + (i * 30));
            rect.setLayoutY(32);
            rect.setStyle("-fx-arc-height: 10; -fx-arc-width: 10;");
            rect.setStroke(TRANSPARENT);
            rect.setStrokeType(StrokeType.INSIDE);
            rect.toFront();
            gr.getChildren().add(rect);
        }
        resultRects = gr;
        mainPane.getChildren().add(resultRects);
    }

    //aesthetics
    public void transition(Button button, String style) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> button.setStyle(style));
        pause.play();
    }

    public void cardAnimation() {
        TranslateTransition st = new TranslateTransition(Duration.millis(1000), rectangleQ);
        st.setByY(-600);
        st.play();
    }

    public void resetCardAnimation() {
        rectangleQ.setTranslateY(0);
    }

    public void nextWindow() {
        //Send to server data
        //   utils.changeScene("../view/ResultsAndReview.fxml");
    }

}





