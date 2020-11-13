import java.util.ArrayList;
import java.util.List;

public class TriviaQuestions {

    String question;
    String rightAnswer;

    public TriviaQuestions(String question, String rightAnswer) {
        this.question = question;
        this.rightAnswer = rightAnswer;
    }

    public TriviaQuestions() {

    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getQuestion() {
        return question;
    }


}
