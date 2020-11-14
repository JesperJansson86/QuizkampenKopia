import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Controller {

    public Button answer1;
    public Button answer2;
    public Button answer3;
    public Button answer4;
    public Label question;
    public AnchorPane mainPane;
    Question q;
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<Button> buttons = new ArrayList<>();
    QuestionFactory questionsAndAnswers = new QuestionFactory();
    List<Question> questionList;
    Button right;

    /**
     * at start initiates questionList by copying the list from QuestionFactory
     */
    public void initialize() {

        questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());

        setOnStage();
        setShuffled();

    }

//TODO: this logic has to be fixed, it needs a time thread, and stop it when user clicks in an answer.
// the user can now click in every button.

    /**
     * actionlistener of question options. if its right it paints green, if not it paints red and paints green right answer
     * @param actionEvent
     */
    public void answerOn(ActionEvent actionEvent) {
        Button buttonCLicked = ((Button) actionEvent.getSource());
        if (buttonCLicked.getText().equals(q.getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: green");

            PointCount.playerPointCount(1); // adds a point to the point counter
        } else if (!buttonCLicked.getText().equals(q.getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: red");
            right.setStyle("-fx-background-color: green");

            PointCount.playerPointCount(0); // sends a 0 to the playerPointCount method in playerCount
        }
    }

    /**
     * method to clean all the styling and setup again
     * @param actionEvent
     */
    public void resetTest(ActionEvent actionEvent) {
        for (Button button : buttons) {
            button.setStyle(null);
        }
        buttons.clear();
        answers.clear();
        question.setText(null); //this is not necessary, but, just in case, and its only one line
        setOnStage();
        setShuffled();
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
    public void setRightAnswer(){
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
    }

    public Question takeAquestion() {
        refillListIfEmpty();
        Random rnd = new Random();
        int index = rnd.nextInt(questionList.size());
        Question question = questionList.get(index);
        questionList.remove(index);

        return question;
    }

    public void refillListIfEmpty() {
        if (questionList.size() < 1){
            questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());}
    }

}




