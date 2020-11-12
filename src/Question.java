

/**
 * Created by Hodei Eceiza
 * Date: 11/12/2020
 * Time: 14:33
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class Question {
    private String question;
    private String rightAnswer;
    private String answer2;
    private String answer3;
    private String answer4;

Question(String question,String rightAnswer,String answer2,String answer3,String answer4){

    this.question =question ;
    this.rightAnswer =rightAnswer;
    this.answer2=answer2;
    this.answer3=answer3;
    this.answer4=answer4;

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

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", answer4='" + answer4 + '\'' +
                '}';
    }
}
