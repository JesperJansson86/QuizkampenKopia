import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Question {

    String question;
    String correctAnswer;
    String incorrect1;
    String incorrect2;
    String incorrect3;
    List<String> shuffled = new LinkedList<>();
    public Question(String question,String correctAnswer,String incorrect1,String incorrect2,String incorrect3)
    {
     this.question=question;
     this.correctAnswer=correctAnswer;
     this.incorrect1=incorrect1;
     this.incorrect2=incorrect2;
     this.incorrect3=incorrect3;
     shuffled.add(correctAnswer);
     shuffled.add(incorrect1);
     shuffled.add(incorrect2);
     shuffled.add(incorrect3);
     Collections.shuffle(shuffled);
    }
    public Question(){

    }




    @Override
    public String toString() {
        String printOutput ="";
        for (int i = 0; i < 4; i++) {
           printOutput = printOutput+" / "+ shuffled.get(i);
        }
        return printOutput;
    }
}