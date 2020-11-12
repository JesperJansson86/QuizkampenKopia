import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hodei Eceiza
 * Date: 11/12/2020
 * Time: 20:24
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class QuestionFactory {
    List<Question> questionList = new ArrayList<>();
    String fileName;

    QuestionFactory() {
        createQuestion();
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void createQuestion() {
        List<String> questions = updateList("src/uncategorizedQuestions.txt");
        for (int i = 0; i < questions.size(); i += 5) {
            Question q = new Question(questions.get(i), questions.get(i + 1), questions.get(i + 2), questions.get(i + 3), questions.get(i + 4));
            questionList.add(q);
        }

    }

    public List updateList(String filename) {
        ArrayList<String> readFile = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String reading;
            while ((reading = reader.readLine()) != null) {
                readFile.add(reading);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return readFile;
    }
}
