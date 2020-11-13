import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.LinkedList;

public class QDataBase {
    public Question q1 = new Question("Två plus två?", "Fyra", "Tre", "Två", "fem");
    public Question q2 = new Question("Två minus två?", "Noll", "Tre", "Två", "fem");
    public Question q3 = new Question("Två är lika med?", "Två", "Tre", "Fyra", "fem");

    public Question getQuestion(Question q) {
        return q;
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
        }}

return qlist;
    }
}
