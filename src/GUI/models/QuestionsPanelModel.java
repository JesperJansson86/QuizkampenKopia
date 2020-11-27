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
    private Question q;
    public ArrayList<String> answers = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>(); //an arrayList of buttons (contains answer1-4)
    private List<Question> questionList=new ArrayList<>();
    private Button right; //right has the same value as the button with the right answer
    private final Button answer1,answer2,answer3,answer4; //buttons that will be stored in the buttons arrayList
    private final Label categoryL,question;

    /**
     *getter for the arrayList with buttons
     * @return arrayList with the answer1-4 buttons
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    /**
     * Getter for q that is a Question
     *
     * @return returns a question
     */
    public Question getQ() {
        return q;
    }

    /**
     * Getter for QuestionList, a list of Questions
     * @return returns a List with Questions
     */
    public List<Question> getQuestionList() {
        return questionList;
    }

    /**
     * Getter for the button with the right answer
     *
     * @return the button with the right answer
     */
    public Button getRight() {
        return right;
    }

    /**
     *
     * @param qList //an array with Questions
     * @param answer1 //button with a question answer on it
     * @param answer2 //button with a question answer on it
     * @param answer3 //button with a question answer on it
     * @param answer4 //button with a question answer on it
     * @param categoryL
     * @param question
     */
    public QuestionsPanelModel(ArrayList<Question> qList,Button answer1, Button answer2, Button answer3, Button answer4, Label categoryL, Label question){
        this.questionList = qList;
        this.answer1=answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.answer4=answer4;
        this.categoryL=categoryL;
        this.question=question;
    }

    /**
     * resets the buttons and the question.
     */
    public void reset() {
        for (Button button : buttons) { //For all the buttons
            button.setStyle(null);
        }
        buttons.clear(); //Clears the buttons
        answers.clear(); //clears the answer
        question.setText(null); //this is not necessary, but, just in case, and its only one line

    }

    /**
     * method to shuffle.first shuffles the String list of answers.then puts those string in buttons. then, calls
     * setRightAnswer to set the button with the right answer
     */
    public void setShuffled() {
        Collections.shuffle(answers); //shuffles the answer buttons so that the answers are in a random order

        //adds the answers to the buttons
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));

        setRightAnswer(); //Calls on the set right answer method
    }

    /**
     * method to get the button with the right answer.
     */
    public void setRightAnswer() {

        //adds all the answer buttons to the buttons arrayList
        buttons.add(answer1);
        buttons.add(answer2);
        buttons.add(answer3);
        buttons.add(answer4);

        for (Button b : buttons) { //Checks all the buttons in the arrayList
            if (b.getText().equals(q.getRightAnswer())) //checks if the button represent the right answer
                right = b; //sets teh value of right to the value of the button with the right answer
        }
    }

    /**
     *
     */
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

    /**
     *
     * @return
     */
    public Question takeAquestion() {

        Question question = questionList.get(questionList.size() - 1);
        questionList.remove(questionList.size() - 1);

        return question;
    }
}
