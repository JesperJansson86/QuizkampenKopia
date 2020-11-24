package MainClasses;

import org.jsoup.Jsoup;

import java.io.*;
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
 * this class takes the text from a text with this format followed by more questions on same line.
 * {"category":"CATEGORY","type":"TYPE","difficulty":"medium","question":"QUESTION","correct_answer":"ANSWER","incorrect_answers":["WRONG","WRONG","WRONG"]}
 * Usable Methods:.
 * <p>
 * getQuestions(int questions);
 * get List<Question> with [questions] amount of questions from each category.
 * <p>
 * getting a single random questions from a category
 * getRandomQuestionByCategory(String categorys)
 * <p>
 * updateList(String URL or filepath)
 * <p>
 * getCategories()
 */
public class QuestionFactory {
    private static List<Question> questionList = new ArrayList<>();
    private static Map<String, ArrayList<Question>> questionsByCategory = new HashMap<>();
    private static List<String> categories = new ArrayList<>();
    private static boolean doOnlyOnce = true;

    public QuestionFactory() {

        if (doOnlyOnce) {
            updateList("src/questionsfromOpenTDB1.txt");
            updateList("src/questionsfromOpenTDB2.txt");
            //Uncomment when live for more questions and categories.
            updateList("https://opentdb.com/api.php?amount=10&category=18&difficulty=easy&type=multiple");
            updateList("https://opentdb.com/api.php?amount=10&category=9&difficulty=easy&type=multiple");
            updateList("https://opentdb.com/api.php?amount=10&category=23&difficulty=easy&type=multiple");
            updateList("https://opentdb.com/api.php?amount=10&category=25difficulty=easy&type=multiple");
            doOnlyOnce = false;
        }

        for (String key : questionsByCategory.keySet()) {
            for (Question q : questionsByCategory.get(key)) {
              //  System.out.println(q);
            }
        }
      //  System.out.println(getCategories().toString());
    }

    private void createQuestion(String stringFromFile) {
        while (true) {
            stringFromFile = Jsoup.parse(stringFromFile).text();
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
            }
            questionsByCategory.get(q.getCategory()).add(q);
        }
        for (String string : questionsByCategory.keySet()) {
            if (!categories.contains(string))
                categories.add(string);
        }
    }

    /**
     * method which reads a file or URL and fills both List questionList and Map questionsByCategory
     * with questions from file or URL.
     *
     * @param source in form of path or URL(http/https only).
     * @return String with all questions and answers.
     */
    public void updateList(String source) {
        if (source.startsWith("http")) {
            try {
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

    private String grabBetween(String source, String after, String before) {
        int indexStart = source.indexOf(after) + after.length();
        int indexEnd = source.indexOf(before, indexStart);
        String result = source.substring(indexStart, indexEnd);
        result = replaceChars(result);
        return result;
    }

    private String replaceChars(String string) {
//        string = string.replace("&quot;", "\"");
//        string = string.replace("&#039;", "'");
//        string = string.replace("&amp;", "&");
//        string = string.replace("&Uuml;", "Ü");
//        string = string.replace("&uuml;", "ü");
        return string;
    }

    /**
     * Returns a random questions in a category.
     *
     * @param category
     * @return
     */
    //Map<String, ArrayList<Question>>
    public Question getRandomQuestionByCategory(String category) {
        Question question = null;
        int rnd=0;
        if (questionsByCategory.containsKey(category)) {
            Random random = new Random();
             rnd= random.nextInt(questionsByCategory.get(category).size());
            question = questionsByCategory.get(category).get(rnd);
        }
        questionsByCategory.get(category).remove(rnd);
        return question;
    }

    /**
     * Returns all categories available.
     *
     * @return
     */
    public List<String> getCategories() {
        return categories;
    }


    /**
     * returns X amount of questions from each available category.
     * MAX 4 available during testing.
     * @param questions (number of questions from each category)
     * @return
     */
    public List<Question> getQuestions(int questions) {
        if(questions > 4) throw new IllegalArgumentException("Can't accept over 4 right now.");
        List<Question> questionsList = new ArrayList<>();
        for (String key : questionsByCategory.keySet()) {
            for (int i = 0; i < questions; i++) {
                questionsList.add(questionsByCategory.get(key).get(i));
            }
        }
        return questionsList;
    }

    //    För test.
    public static void main(String[] args) {
        QuestionFactory qf = new QuestionFactory();
    }

}
