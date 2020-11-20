package GUI.controllers;

import Client.Client;
import MainClasses.PointCount;
import MainClasses.Question;
import GUI.models.GUIutils;
import GUI.models.QuestionsPanelModel;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QuestionsPanel {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    public ArrayList<Question> qList;

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
    public ProgressBar timeLeft;
    public Rectangle rectangleQ;


    //this should come from somewhere else (game class??)
    int round = 1;

    int NBRQuestion = 1; // represents the current question that is being answered

    List<Rectangle> resultsList = new ArrayList<>();

    QuestionsPanelModel model;
    @FXML
    Label categoryL;

    GUIutils utils;

    /**
     * at start initiates questionList by copying the list from MainClasses.QuestionFactory
     */
    public void initialize() {
        System.out.println("In QuestionsPanel: init begins.");
        try {
            qList = (ArrayList<Question>) toGUI.take();
            round = (Integer) toGUI.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("In QuestionsPanel: got questions");
        model = new QuestionsPanelModel(qList, answer1, answer2, answer3, answer4, categoryL, question);
        resultsList.add(Qresult2);
        resultsList.add(Qresult1);

        roundNumber.setText(String.valueOf(round));
        utils = new GUIutils(mainPane);
        //  questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());
        timeLeft.setProgress(1);

        //this have to be in one method.
        model.setOnStage();
        animationTest();
        timeBar(5);
        timeForAnswer();
    }

    public void timeBar(int seconds) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timeLeft.progressProperty(), 1)),
                new KeyFrame(Duration.seconds(seconds), e -> {

                }, new KeyValue(timeLeft.progressProperty(), 0)));
        timeline.play();
    }

    //added a game sequence in time, make a method which creates a timeline depending on amount of questions.
    public void timeForAnswer() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    model.reset();
                    resetAnimationTest();
                    model.setOnStage();
                    animationTest();
                    timeBar(5);
                }),
                new KeyFrame(Duration.seconds(5), e -> {
                    try {
                        utils.changeScene("RESULTS");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }));

        timeline.play();
    }

    //TODO: Need a method which jumps to next question when user answers. and make this much cleaner

    /**
     * actionlistener of question options. if its right it paints green, if not it paints red and paints green right answer
     *
     * @param actionEvent
     */
    public void answerOn(ActionEvent actionEvent) {
        Button buttonCLicked = ((Button) actionEvent.getSource()); //Hämtar vilken knapp som är tryckt

        if (buttonCLicked.getText().equals(model.getQ().getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: green");
            transition(buttonCLicked, null);
            resultsList.get(model.getQuestionList().size()).setStyle("-fx-fill: green");

            PointCount.answerStore(buttonCLicked.getText()); //stores the right answer in pointCount
            PointCount.playerPointCount(1,round,NBRQuestion); //if the player answered correctly
            NBRQuestion++;

        } else if (!buttonCLicked.getText().equals(model.getQ().getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: red");
            transition(model.getRight(), "-fx-background-color: green");
            transition(buttonCLicked, null);
            resultsList.get(model.getQuestionList().size()).setStyle("-fx-fill: red");

            PointCount.playerPointCount(0,round,NBRQuestion); //if the player answered wrong
            NBRQuestion++;
        }
    }

    public void reset(ActionEvent actionEvent) {
        model.reset();
    }

    //aesthetics
    public void transition(Button button, String style) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> button.setStyle(style));
        pause.play();
    }

    public void animationTest() {
        TranslateTransition st = new TranslateTransition(Duration.millis(1000), rectangleQ);
        st.setByY(-200);
        st.play();
    }

    public void resetAnimationTest() {
        rectangleQ.setTranslateY(0);
    }


}




