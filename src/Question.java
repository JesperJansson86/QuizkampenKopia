import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    final String category;
    final String question;
    final String answer;
    final String fake1;
    final String fake2;
    final String fake3;

    public Question(String category, String question, String answer, String fake1, String fake2, String fake3){
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.fake1 = fake1;
        this.fake2 = fake2;
        this.fake3 = fake3;
    }

    public List<String> getAnswers(){
        List<String> result = new ArrayList<>();
        result.add(answer);
        result.add(fake1);
        result.add(fake2);
        result.add(fake3);
        Collections.shuffle(result);

        return result;
    }

    public String getAnswer(){
        return this.answer;
    }

    public String getQuestion(){
        return this.question;
    }

    public String getCategory(){
        return this.category;
    }
}