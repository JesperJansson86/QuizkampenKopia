import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hodei Eceiza
 * Date: 11/12/2020
 * Time: 20:24
 * Project: QuizkampenKopia
 * Copyright: MIT
 */

/**
 * this class takes the text from a text with this format->
 * 1. line->question
 * 2. line->right answer
 * 3. line->an answer
 * 4. line->an answer
 * 5. line->an answer
 */
public class QuestionFactory implements Serializable {
    List<Question> questionList = new ArrayList<>();


    QuestionFactory() {
        createQuestion();
    }

    public List<Question> getQuestionList() {
        return questionList;
    }


    /**
     * a method which takes a list of strings, save them in a question class, and adds this questions to the questionList
     */
    public void createQuestion() {
        List<String> questions = updateList("src/uncategorizedQuestions.txt");
        for (int i = 0; i < questions.size(); i += 5) {
            Question q = new Question(questions.get(i), questions.get(i + 1), questions.get(i + 2), questions.get(i + 3), questions.get(i + 4));
            questionList.add(q);
        }

    }

    /**
     * method which reads a file and puts its line in an element of a list
     * @param filename the file to read
     * @return an arraylist full of strings
     */
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
