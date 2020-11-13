import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class QuestionFactory {
    static List<Question> allQuestions = new ArrayList<>();
    static Map<String, ArrayList<Question>> qsByCategory = new HashMap();
    static String[] categories;

    private QuestionFactory(){
        generateQuestionsFromFile();
        listQuestionsByCategory();
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
}
