package GUI.models;

import MainClasses.Question;
import MainClasses.QuestionFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hodei Eceiza
 * Date: 11/15/2020
 * Time: 13:00
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class QuestionsPanelModel {
    Question q;
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<Button> buttons = new ArrayList<>();
    QuestionFactory questionsAndAnswers = new QuestionFactory();
    Question n =questionsAndAnswers.getRandomQuestionByCategory("Geography");
    Question n2 =questionsAndAnswers.getRandomQuestionByCategory("Geography");
    List<Question> questionList=new ArrayList<Question>();


    //List<Rectangle>resultsList=new ArrayList<>();
    Button right;
    Button answer1,answer2,answer3,answer4;
    Label categoryL,question;

    public Question getQ() {
        return q;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public Button getRight() {
        return right;
    }

    public QuestionsPanelModel(Button answer1, Button answer2, Button answer3, Button answer4, Label categoryL, Label question){

        this.answer1=answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.answer4=answer4;
        this.categoryL=categoryL;
        this.question=question;
        questionList.add(n);
        questionList.add(n2);
    }
    public void reset() {
        for (Button button : buttons) {
            button.setStyle(null);
        }
        buttons.clear();
        answers.clear();
        question.setText(null); //this is not necessary, but, just in case, and its only one line
         // setOnStage();
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

    //remade method, to not take randomly from the list. I suppose it comes random from the server??
    public Question takeAquestion() {

        Question question = questionList.get(questionList.size() - 1);
        questionList.remove(questionList.size() - 1);

        return question;
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





    public void refillListIfEmpty() {
        if (questionList.size() < 1) {
            questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());
        }
    }

 */
}
