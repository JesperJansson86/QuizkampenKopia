package GUI.controllers;

import GUI.models.GUIutils;
import GUI.models.QuestionsPanelModel;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class QuestionsPanel {

    public Button answer1;
    public Button answer2;
    public Button answer3;
    public Button answer4;
    public Label question;
    public AnchorPane mainPane;
    //TODO:add this rectangle programmatically depending on amount of questions
    public Rectangle Qresult1;
    public Rectangle Qresult2;
    //public Rectangle Qresult3;
    public Label roundNumber;
    public ProgressBar timeLeftBar;
    public Rectangle rectangleQ;

    //this should come from somewhere else (game class??)
    int round = 1;

    List<Rectangle> resultsList = new ArrayList<>();

    QuestionsPanelModel model;
    @FXML
    Label categoryL;

    GUIutils utils;
    Timeline roundTime;

    /**
     * at start initiates questionList by copying the list from MainClasses.QuestionFactory
     */
    public void initialize() {
        model = new QuestionsPanelModel(answer1, answer2, answer3, answer4, categoryL, question);
        resultsList.add(Qresult2);
        resultsList.add(Qresult1);
        roundNumber.setText(String.valueOf(round));
        utils = new GUIutils(mainPane);
        timeLeftBar.setProgress(1);

        setRoundTimes(5, 2);


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
                new KeyFrame(Duration.ZERO, e -> {
                    model.setOnStage();
                    cardAnimation();
                    timeBar(seconds);
                }),
                new KeyFrame(Duration.seconds(seconds), e -> {
                    model.reset(e);
                    resetCardAnimation();
                }));

        roundTime.setCycleCount(rounds);
        roundTime.play();
        roundTime.setOnFinished(e->nextWindow());
    }


    //TODO: Need a method which jumps to next question when user answers. and make this much cleaner

    /**
     * actionlistener of question options. if its right it paints green, if not it paints red and paints green right answer
     *
     * @param actionEvent
     */
    public void answerOn(ActionEvent actionEvent) {
        Button buttonCLicked = ((Button) actionEvent.getSource());
        if (buttonCLicked.getText().equals(model.getQ().getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: green");
            transition(buttonCLicked, null);
            resultsList.get(model.getQuestionList().size()).setStyle("-fx-fill: green");


        } else if (!buttonCLicked.getText().equals(model.getQ().getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: red");
            transition(model.getRight(), "-fx-background-color: green");
            transition(buttonCLicked, null);
            resultsList.get(model.getQuestionList().size()).setStyle("-fx-fill: red");

        }
    }

    public void reset(ActionEvent actionEvent) {
        model.reset(actionEvent);
    }

    //aesthetics
    public void transition(Button button, String style) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> button.setStyle(style));
        pause.play();
    }

    public void cardAnimation() {
        TranslateTransition st = new TranslateTransition(Duration.millis(1000), rectangleQ);
        st.setByY(-200);
        st.play();
    }

    public void resetCardAnimation() {
        rectangleQ.setTranslateY(0);
    }
public void nextWindow(){
        //Send to server data
    utils.changeScene("../view/ResultsAndReview.fxml");
}

}




