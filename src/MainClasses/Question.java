package MainClasses;

/**
 * Created by Hodei Eceiza
 * Date: 11/12/2020
 * Time: 14:33
 * Project: QuizkampenKopia
 * Copyright: MIT
 */

/**
 * a simple question class. has in strings the question, right answer and three alternatives.
 */
public class Question {
    private String category;
    private String question;
    private String rightAnswer;
    private String answer2;
    private String answer3;
    private String answer4;

    Question(String category, String question, String rightAnswer, String answer2, String answer3, String answer4) {
        this.category = category;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    //used for testing
    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + "'\'" +
                ", question='" + question + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", answer4='" + answer4 + '\'' +
                '}';
    }
}
