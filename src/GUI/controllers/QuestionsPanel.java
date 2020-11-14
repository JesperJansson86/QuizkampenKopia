package GUI.controllers;

import GUI.models.GUIutils;
import MainClasses.Question;
import MainClasses.QuestionFactory;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionsPanel {

    public Button answer1;
    public Button answer2;
    public Button answer3;
    public Button answer4;
    public Label question;
    public AnchorPane mainPane;
    public Rectangle Qresult1;
    public Rectangle Qresult2;
    //public Rectangle Qresult3;
    public Label roundNumber;
    public ProgressBar timeLeft;



    Question q;
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<Button> buttons = new ArrayList<>();
    QuestionFactory questionsAndAnswers = new QuestionFactory();
    List<Question> questionList;
    Button right;
    @FXML
    Label categoryL;
    static double progressTime=1;

    GUIutils utils;
    /**
     * at start initiates questionList by copying the list from MainClasses.QuestionFactory
     */
    public void initialize() {
        utils=new GUIutils(mainPane);
        questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());
       timeLeft.setProgress(1);
        setOnStage();

        timeForAnswer();


    }
public void timeForAnswer(){
    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(5), e -> {
                resetTest(e);
                setOnStage();
            }),
    new KeyFrame(Duration.seconds(10), e -> {
        try {
            utils.changeScene("../view/ResultsAndReview.fxml");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }));

    timeline.play();
}

    /**
     * actionlistener of question options. if its right it paints green, if not it paints red and paints green right answer
     *
     * @param actionEvent
     */
    public void answerOn(ActionEvent actionEvent) {
        Button buttonCLicked = ((Button) actionEvent.getSource());
        if (buttonCLicked.getText().equals(q.getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: green");
            transition(buttonCLicked, null);

        } else if (!buttonCLicked.getText().equals(q.getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: red");
            transition(right, "-fx-background-color: green");
            transition(buttonCLicked, null);

        }
    }

    public void transition(Button button, String style) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(e -> {
            button.setStyle(style);
        });
        pause.play();
    }

    /**
     * method to clean all the styling and setup again
     *
     * @param actionEvent
     */
    public void resetTest(ActionEvent actionEvent) {
        for (Button button : buttons) {
            button.setStyle(null);
        }
        buttons.clear();
        answers.clear();
        question.setText(null); //this is not necessary, but, just in case, and its only one line
     //   setOnStage();

    }

    /**
     * method to shuffle.first shuffles the String list of answers.then puts those string in buttons. then, calls
     * setRightAnswer to set the button with the right answer
     */
    public void setShuffled() {
        Collections.shuffle(answers);

        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));

        setRightAnswer();
    }

    /**
     * method to get the button with the right answer.
     */
    public void setRightAnswer() {
        buttons.add(answer1);
        buttons.add(answer2);
        buttons.add(answer3);
        buttons.add(answer4);

        for (Button b : buttons) {
            if (b.getText().equals(q.getRightAnswer()))
                right = b;
        }
    }

    public void setOnStage() {
        q = takeAquestion();

        question.setText(q.getQuestion());
        answers.add(q.getAnswer2());
        answers.add(q.getAnswer3());
        answers.add(q.getAnswer4());
        answers.add(q.getRightAnswer());
        categoryL.setText(q.getCategory());
        setShuffled();
    }
/*
    public Question takeAquestion() {
        refillListIfEmpty();
        Random rnd = new Random();
        int index = rnd.nextInt(questionList.size());
        Question question = questionList.get(index);
        questionList.remove(index);

        return question;
    }

 */
public Question takeAquestion() {

    Question question = questionList.get(questionList.size()-1);
    questionList.remove(questionList.size()-1);

    return question;
}
    public void refillListIfEmpty() {
        if (questionList.size() < 1) {
            questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());
        }
    }


}




