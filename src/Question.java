import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    public List buildQuestions(int amountOfQuestions) {
        List<Question> qlist = new LinkedList<>();
        Random rand = new Random();

        for (int j = 0; j < amountOfQuestions; j++) {
            int rowInQuestionFile = rand.nextInt(4) + 1;
            try (Scanner scan = new Scanner(new FileReader("src\\questions"))) {
                scan.useDelimiter(",");

                for (int i = 0; i < rowInQuestionFile - 1; i++) {
                    scan.nextLine();
                    System.out.println("ny rad");
                }
                qlist.add(new Question(scan.next(), scan.next(), scan.next(), scan.next(), scan.next()));

            } catch (IOException e) {
                System.out.println("Det gick ej att läsa från filen");
            }
        }

        return qlist;
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