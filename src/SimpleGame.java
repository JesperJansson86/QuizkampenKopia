import java.util.List;
import java.util.Scanner;

public class SimpleGame {

    int score = 0;

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        SimpleGame game1 = new SimpleGame();
        Question q = new Question();
        game1.chooseAnswer((List) (q.buildQuestions(4)));
        System.out.println("Totalt antal rätt: " + game1.score);
    }

    public void chooseAnswer(List list) {
        List<Question> qlist = list;
        for (var q : qlist
        ) {
            System.out.println(q.question);
            System.out.println(q.toString());
            Scanner scan = new Scanner(System.in);
            int answer = Integer.parseInt(scan.next());
            if (q.shuffled.get(answer - 1) == q.correctAnswer) {
                System.out.println("Rätt");
                score++;
            } else {
                System.out.println("Fel");
            }
        }
    }
}
