import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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
    private List<String> categories = new ArrayList<>();

    QuestionFactory() {
        updateList("src/questionsfromOpenTDB1.txt");
        updateList("src/questionsFromOpenTDB2.txt");
        //Uncomment when live.
//        updateList("https://opentdb.com/api.php?amount=10&category=18&difficulty=easy&type=multiple");
//        updateList("https://opentdb.com/api.php?amount=10&category=9&difficulty=easy&type=multiple");

        for (String key : questionsByCategory.keySet()) {
            for (Question q : questionsByCategory.get(key)) {
                System.out.println(q);
            }
        }
        System.out.println(categories.toString());
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    /**
     * a method which takes a list of strings, save them in a question class, and adds this questions to the questionList
     */
    public void createQuestion(String stringFromFile) {
        while (true) {
            int indexStart = stringFromFile.indexOf("category");
            if (indexStart == -1) break;
            int indexEnd = stringFromFile.indexOf("category", indexStart + 1);
            if (indexEnd == -1) {
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

            if (!questionsByCategory.containsKey(q.getCategory())) {
                questionsByCategory.put(q.getCategory(), new ArrayList<>());
                questionsByCategory.get(q.getCategory()).add(q);
            } else {
                questionsByCategory.get(q.getCategory()).add(q);
            }
        }
        for(String string: questionsByCategory.keySet()){
            if(!categories.contains(string))
                categories.add(string);
        }
    }

    /**
     * method which reads a file or URL and fills both List questionList and Map questionsByCategory
     * with questions from file or URL.
     * @param source in form of path or URL of source to read.
     * @return String with all questions and answers.
     */
    private void updateList(String source) {
        if(source.startsWith("http")){
            try{
                URL url = new URL(source);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                createQuestion(in.readLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            try (Scanner scanner = new Scanner(new File(source))) {
                createQuestion(scanner.nextLine());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Question getRandomQuestionByCategory(String category) {
        Question question = null;
        if (questionsByCategory.containsKey(category)) {
            Random random = new Random();
            question = questionsByCategory.get(category).get(random.nextInt(questionsByCategory.get(category).size()));
        }
        return question;
    }

    public List<String> getCategories(){
        List<String> result = new ArrayList<>();
        for(String string: questionsByCategory.keySet()){
            result.add(string);
        }
        return result;
    }

    private String grabBetween(String source, String after, String before) {
        int indexStart = source.indexOf(after) + after.length();
        int indexEnd = source.indexOf(before, indexStart);
        String result = source.substring(indexStart, indexEnd);
        result = replaceChars(result);
        return result;
    }

    private String replaceChars(String string) {
        string = string.replace("&quot;", "\"");
        string = string.replace("&#039;", "'");
        return string;
    }

    public static void main(String[] args) {
        QuestionFactory qf = new QuestionFactory();
    }
}
