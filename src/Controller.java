import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {

    public Button answer1;
    public Button answer2;
    public Button answer3;
    public Button answer4;
    public Label question;
    Question q;


    public void initialize(){
        q=new Question("Which one is right?","right","left2","left3","left4");
        question.setText(q.getQuestion());
        ArrayList<String> answers=new ArrayList<>();
        answers.add(q.getAnswer2());
        answers.add(q.getAnswer3());
        answers.add(q.getAnswer4());
        answers.add(q.getRightAnswer());
        Collections.shuffle(answers);
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
    }
    public void answerOn(ActionEvent actionEvent) {
        System.out.println( actionEvent.getSource());
        System.out.println();
        Button buttonCLicked=((Button)actionEvent.getSource());
if(buttonCLicked.getText().equals(q.getRightAnswer())){
    buttonCLicked.setStyle("-fx-background-color: green");
}
    }
}




