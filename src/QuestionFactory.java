import java.io.*;
import java.util.*;

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
public class QuestionFactory {
    private List<Question> questionList = new ArrayList<>();
    private Map<String, ArrayList<Question>> questionsByCategory = new HashMap<>();


    QuestionFactory() {
//        createQuestion();
        updateList("src/questionsfromOpenTDB1.txt");
        testRun();
    }

    public List<Question> getQuestionList() {
        return questionList;
    }


    /**
     * a method which takes a list of strings, save them in a question class, and adds this questions to the questionList
     */
//    public void createQuestion() {
//        List<String> questions = updateList("src/uncategorizedQuestions.txt");
//        for (int i = 0; i < questions.size(); i += 5) {
//            Question q = new Question(questions.get(i), questions.get(i + 1), questions.get(i + 2), questions.get(i + 3), questions.get(i + 4));
//            questionList.add(q);
//        }
//
//    }
    public void createQuestion() {

    }

    /**
     * method which reads a file and puts its line in an element of a list
     *
     * @param filename the file to read
     * @return an arraylist full of strings
     */
//    public List updateList(String filename) {
//        ArrayList<String> readFile = new ArrayList<>();
//        try (
//                BufferedReader reader = new BufferedReader(new FileReader(filename))) {
//            String reading;
//            while ((reading = reader.readLine()) != null) {
//                readFile.add(reading);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return readFile;
//    }

    private void updateList(String filename){
        try (Scanner scanner = new Scanner(new File(filename))) {
            String stringFromFile = scanner.nextLine();
            while(true){
                int indexStart = stringFromFile.indexOf("category");
                if(indexStart == -1) break;
                int indexEnd = stringFromFile.indexOf("category", indexStart+1);
                if(indexEnd == -1){
                    indexEnd = stringFromFile.length();
                }
                String nextQuestion = stringFromFile.substring(0, indexEnd);

                String category = grabBetween(nextQuestion, "category\":\"", "\",\"");
                String question = grabBetween(nextQuestion, "question\":\"", "\",\"");
                String correctAnswer = grabBetween(nextQuestion, "correct_answer\":\"", "\",\"");
                String[] incorrectAnswer = grabBetween(nextQuestion, "incorrect_answers\":[\"", "\"]}").split("\",\"");

                stringFromFile = stringFromFile.substring(indexEnd);

                Question q = new Question(category, question, correctAnswer, incorrectAnswer[0], incorrectAnswer[1], incorrectAnswer[2]);
                questionList.add(q);

//                System.out.printf("Cat: %s Q: %s \nA: %s \nF1: %s \nF2: %s \nF3: %s\n",
//                        category, question, correctAnswer, incorrectAnswer[0], incorrectAnswer[1], incorrectAnswer[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void testRun(){
        createQuestion();
    }

    private String grabBetween(String source, String after, String before){
        int indexStart = source.indexOf(after) + after.length();
        int indexEnd = source.indexOf(before, indexStart);
        String result = source.substring(indexStart, indexEnd);
        result = replaceChars(result);
        return result;
    }

    private String replaceChars(String string){
        string = string.replace("&quot", "\"");
        string = string.replace("&#039;","'");
        return string;
    }

    public static void main(String[] args) {
        QuestionFactory qf = new QuestionFactory();
    }
}
