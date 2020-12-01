package gui.controllers;

import client.Client;
import gui.models.GUIutils;
import gui.models.QuestionsPanelModel;
import mainClasses.PointCount;
import mainClasses.Question;
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

import static javafx.scene.paint.Color.TRANSPARENT;

public class QuestionsPanel {
    // TODO: 26/11/2020 Figure out what needs to be public/private
    private static final String GREEN_BCK = "-fx-background-color: #00be00"; //Green
    private static final String GREEN_FILL = "-fx-fill: #00be00"; //Green
    private static final String RED_BCK = "-fx-background-color:red"; //Red
    private static final String RED_FILL = "-fx-fill: red"; //Red
    private final List<Question> qList = Client.gr.activeQuestions; //list of questions
    private final int actualRound = Client.gr.roundnumber;//holds the round number
    private final int secondsToAnswer = 5; //how long the user has to answer // TODO: 26/11/2020 Maybe we should make this a setting in the config ?
    @FXML
    public AnchorPane mainPane; //pane that holds the buttons and labels
    public QuestionsPanelModel model; //model holds the buttons, question and the timeBar
    public Label categoryL; //label holds what category questions are
    public Button answer1; //button that holds answer1
    public Button answer2; //button that holds answer2
    public Button answer3; //button that holds answer3
    public Button answer4; //button that holds answer4
    public Label question; //holds and shows the question
    public KeyFrame setQuestions; //sets the right questions
    public KeyFrame cleanQuestions;//removes the previous questions
    public Label roundNumber; //holds the current round number and displays it
    public Rectangle rectangleQ; //rectangle with the question inside of it
    public Timeline roundTime; //uses the timeLeftBar to represent how much time the user has left ot answer the question
    public ProgressBar timeLeftBar; //a progress bar that's inside of the timeline
    public Group resultRects; // tbe squares inside of rectangleQ that change color based on right or wrong answer
    public GUIutils utils; // for changing view when done

    /**
     * at start initiates questionList by copying the list from MainClasses.QuestionFactory
     */
    public void initialize() {
        model = new QuestionsPanelModel((ArrayList<Question>) qList, answer1, answer2, answer3, answer4, categoryL, question); //adds all the things that will be shown to model

        roundNumber.setText(String.valueOf(actualRound)); //shows the round number
        utils = new GUIutils(mainPane);
        timeLeftBar.setProgress(1); //calls on timeBar
        createResultsRect(); //Calls on The method that creates the score keeping squares
        setRoundTimes(secondsToAnswer, qList.size()); //sends secondsToAnswer and how many questions there are to setRoundTimes
    }

    /**
     * creates the timeline that will be used to show the time left to answer
     *
     * @param seconds the amount of time a player has to answer a question
     */
    public void timeBar(int seconds) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timeLeftBar.progressProperty(), 1)),
                new KeyFrame(Duration.seconds(seconds), e -> {
                }, new KeyValue(timeLeftBar.progressProperty(), 0)));
        timeline.play();
    }

    /**
     *
     *
     * @param seconds the amount of time a player has to answer a question
     * @param rounds the number of question in a round (the name is bad)
     */
    public void setRoundTimes(int seconds, int rounds) {
        roundTime = new Timeline(

                setQuestions = new KeyFrame(Duration.ZERO, "setQuestions", e -> { //when the time runs out
                    buttonsDisable(false); //Disables additional userInputs
                    model.reset();
                    resetCardAnimation();
                    model.setOnStage();
                    cardAnimation();
                    timeBar(seconds); //Calls on the timeBar method
                }),
                cleanQuestions = new KeyFrame(Duration.seconds(seconds), "clearQuestions", e -> {

                    PointCount.storeAnswer(null); //no user input (time ran out)
                    PointCount.playerPointCount(0); //player answered wrong (did not answer)
                    PointCount.playerRoundTotal(); //calls on the method playerRoundTotal to calculate the total score of the round

                    //turns the top left corner box red because no answer is always the wrong answer
                    resultRects.getChildren().get(model.getQuestionList().size()).setStyle(RED_FILL);

                    //resets animations and other ui things
                    model.reset();
                    resetCardAnimation();
                }));
        roundTime.setCycleCount(rounds);
        roundTime.play();
        roundTime.setOnFinished(e -> nextWindow());
    }


    /**
     * checks if the user pressed the right answer, if its right it paints green, if not it paints red and paints green right answer
     *
     * @param actionEvent if the user press a button
     */
    public void answerOn(ActionEvent actionEvent) {

        Button buttonCLicked = ((Button) actionEvent.getSource()); //Saves the button the user pressed

        if (buttonCLicked.getText().equals(model.getQ().getRightAnswer())) { //user answered correctly
            buttonCLicked.setStyle(GREEN_BCK); //Turns the button green
            transition(buttonCLicked, null); //sends the button the user pressed to transition

            resultRects.getChildren().get(model.getQuestionList().size()).setStyle(GREEN_FILL); //turns the result square green

            PointCount.storeAnswer(buttonCLicked.getText()); //answer stored in String form in the storeAnswer method
            PointCount.playerPointCount(1); //player answered correctly so 1 gets sent to playerPointCount

            buttonsDisable(true); //disables further button input
            jumpToNextQuestion();

        } else if (!buttonCLicked.getText().equals(model.getQ().getRightAnswer())) { //user answered incorrectly
            buttonCLicked.setStyle(RED_BCK); //turns the button the user pressed red (because it was wrong)
            transition(model.getRight(), GREEN_BCK); //turns the button with the correct answer green
            transition(buttonCLicked, null); //sends the pressed button to transition
            resultRects.getChildren().get(model.getQuestionList().size()).setStyle(RED_FILL); //turns the result square red

            PointCount.storeAnswer(buttonCLicked.getText()); //answer stored in String form in the storeAnswer method
            PointCount.playerPointCount(0); //player answered incorrectly so 0 gets sent to playerPointCount

            buttonsDisable(true); //disables further button input
            jumpToNextQuestion();
        }
    }

    /**
     * transition between one question to the next
     */
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

    /**
     * turns of the buttons if true
     *
     * @param disabled if true, any further button presses will be disabled
     */
    public void buttonsDisable(boolean disabled) {
        for (Button answerB : model.getButtons()) //loops all the buttons
            answerB.setDisable(disabled); //disables the button
    }

    /**
     * Creates the result squares that are green if user answered correctly and red if user answered wrong
     */
    private void createResultsRect() {
        Group gr = new Group();
        for (int i = qList.size(); i > 0; i--) { //one square per question in qList
            Rectangle rect = new Rectangle();
            //all the settings for the rectangles
            rect.setArcHeight(5);
            rect.setArcWidth(5);
            rect.setFill(Color.WHITE); //white until user answers a question or runs out of time
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

    /**
     * Used to transition between colors and or styles
     *
     * @param button the answer that the user pressed
     * @param style a string
     */
    public void transition(Button button, String style) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> button.setStyle(style));
        pause.play();
    }

    /**
     * the question reveal animation that plays in the beginning of a question
     */
    public void cardAnimation() {
        TranslateTransition st = new TranslateTransition(Duration.millis(1000), rectangleQ);
        st.setByY(-600);
        st.play();
    }

    /**
     * resets the question card animation
     */
    public void resetCardAnimation() {
        rectangleQ.setTranslateY(0);
    }

    /**
     * sends a string to server and start the transition to result or loading
     */
    public void nextWindow() {
        //Send to server when dune
        try {
            Client.toClient.put("s"); //Random string gets sent, so that the client knows that questionPanel is dune

            String nextPane = (String) Client.toGUI.take();

            utils.changeScene(nextPane);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}