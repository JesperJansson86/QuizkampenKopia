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

    public void initialize() {

        questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());

        setOnStage();
        setShuffled();

    }


    public void answerOn(ActionEvent actionEvent) {
        Button buttonCLicked = ((Button) actionEvent.getSource());
        if (buttonCLicked.getText().equals(q.getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: green");
        } else if (!buttonCLicked.getText().equals(q.getRightAnswer())) {
            buttonCLicked.setStyle("-fx-background-color: red");
            right.setStyle("-fx-background-color: green");
        }
    }

    public void resetTest(ActionEvent actionEvent) {
        for (Button button : buttons) {
            button.setStyle(null);
        }
        buttons.clear();
        answers.clear();
        question.setText(null);
        setOnStage();
        setShuffled();
    }

    public void setShuffled() {
        Collections.shuffle(answers);

        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));

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
        if (questionList.size() < 1)
            questionList = new ArrayList<>(questionsAndAnswers.getQuestionList());

    }

}




