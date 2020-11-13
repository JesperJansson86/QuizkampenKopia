import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
//    QuizGame quizGame = new QuizGame();
    List<Question> allQuestions = new ArrayList<>();
    Map<String, List<Question>> qsByCategory = new HashMap<>();
    String[] categories;
    Player player1 = Player.getInstance();
    List<Question> qsPlayed = new ArrayList<>();

    public Main() {
        generateQuestionsFromFile();
        listQuestionsByCategory();
        System.out.println("Welcome to QuizNödig, what is your name?");
        player1.setName(getInput());
        System.out.println("Hello, " + player1.getName() + "! How many rounds? (2-4)");
        int numberOfRounds = getInputInt(2, 4);
        System.out.println("OK! " + numberOfRounds + " rounds it is.");
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("chose one of four categories!");
            for (int j = 0; j < categories.length; j++) {
                System.out.print(j + 1 + ": " + categories[j]);
                System.out.println();
            }
            int chosenCategory = getInputInt(1, 4) - 1;
            System.out.println("OK! " + categories[chosenCategory] + " it is!");
            System.out.println("Loading question player 1.\n\n");
            List<Question> qsThisRound = qsByCategory.get(categories[chosenCategory]);
            Collections.shuffle(qsThisRound);
            Question currentQ = null;
            while (true) {
                for (Question q : qsThisRound) {
                    if (!qsPlayed.contains(q)) {
                        currentQ = q;
                        qsPlayed.add(q);
                        break;
                    }
                }
                break;
            }
            if (currentQ == null) {
                System.out.println("Error, no more questions. Exiting!");
                System.exit(0);
            }
            List<String> answers = currentQ.getAnswers();

            System.out.printf("%s\n%s\n1:%s\n2:%s\n3:%s\n4:%s\n",
                    currentQ.category, currentQ.question,
                    answers.get(0), answers.get(1), answers.get(2), answers.get(3));

            int answerNumber = getInputInt(1, 4) - 1;

            if (answers.get(answerNumber).equalsIgnoreCase(currentQ.answer)) {
                System.out.println("Congratulations! +1 point!");
                player1.setScore(player1.getScore() + 1);
            } else {
                System.out.println("You done fucked up! 0 points!");
            }
        }
        System.out.println("Well played! You scored " + player1.getScore());
        System.out.println("Bye!");
    }

    private String getInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                String result = scanner.nextLine();
                if (result == null || result.isEmpty() || result.isBlank()) throw new NoSuchElementException("bla");
                return result;
            } catch (NoSuchElementException e) {
                System.out.println("Oups! Try again!");
                scanner.reset();
            }
        }
    }

    private int getInputInt(int min, int max) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                int result = Integer.parseInt(scanner.nextLine());
                if (result <= max && result >= min) {
                    return result;
                }
                System.out.println("Number not in range (" + min + "-" + max + ")");
            } catch (NumberFormatException e) {
                System.out.println("That was not a number");
                scanner.reset();
            }
        }
    }

    private void listQuestionsByCategory() {
        for (Question q : allQuestions) {
            if (!qsByCategory.containsKey(q.category)) {
                qsByCategory.put(q.category, new ArrayList<>());
                qsByCategory.get(q.category).add(q);
            } else {
                qsByCategory.get(q.category).add(q);
            }
        }
        categories = qsByCategory.keySet().toArray(new String[0]);
    }

    private void generateQuestionsFromFile() {
        File file = new File(getClass().getClassLoader().getResource("questions.txt").getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] temp = scanner.nextLine().split("#");
                allQuestions.add(new Question(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
