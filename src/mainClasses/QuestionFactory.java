package mainClasses;

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
    private List<Question> questionList = new ArrayList<>();
    private Map<String, ArrayList<Question>> questionsByCategory = new HashMap<>();
    private List<String> categories = new ArrayList<>();
    private boolean test = true;

    public QuestionFactory() {
        try {
            updateList("src/OpenTDB.txt");
        } catch (NullPointerException e) {
            //If reading fails, use standard 1200 questions.
            updateList("src/OpenTDBFallBack.txt");
            e.printStackTrace();
        }
    }

    private void createQuestion(String stringFromFile) throws NullPointerException{
        while (true) {
            stringFromFile = Jsoup.parse(stringFromFile).text();
            int indexStart = stringFromFile.indexOf("category");
            if (indexStart == -1) break;
            int indexEnd = stringFromFile.indexOf("category", indexStart + 1);
            if (indexEnd == -1) {
                indexEnd = stringFromFile.length();
            }
            String nextQuestion = stringFromFile.substring(0, indexEnd);
            //Parsing text instead of using gson
            String category = grabBetween(nextQuestion, "category\":\"", "\",\"");
            String question = grabBetween(nextQuestion, "question\":\"", "\",\"");
            String correctAnswer = grabBetween(nextQuestion, "correct_answer\":\"", "\",\"");
            if(test){
                correctAnswer = "BAM! " + correctAnswer;
            }
            String[] incorrectAnswer = grabBetween(nextQuestion, "incorrect_answers\":[\"", "\"]}").split("\",\"");
            stringFromFile = stringFromFile.substring(indexEnd);
            //Creating and adding Questions to correct Category.
            Question q = new Question(category, question, correctAnswer, incorrectAnswer[0], incorrectAnswer[1], incorrectAnswer[2]);
            questionList.add(q);
            if (!questionsByCategory.containsKey(q.getCategory())) {
                questionsByCategory.put(q.getCategory(), new ArrayList<>());
                questionsByCategory.get(q.getCategory()).add(q);
            } else {
                questionsByCategory.get(q.getCategory()).add(q);
            }
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
    private void updateList(String source) throws NullPointerException {
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
                while (scanner.hasNextLine()) {
                    createQuestion(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String grabBetween(String source, String after, String before) throws NullPointerException{
        int indexStart = source.indexOf(after) + after.length();
        int indexEnd = source.indexOf(before, indexStart);
        String result = source.substring(indexStart, indexEnd);
        return result;
    }

    /**
     * Returns a random questions in a category. <br>
     * Removes that question from Hashmap of questions so it can't be selected again.
     * @param category
     * @return
     */
    public Question getRandomQuestionByCategory(String category) {
        Question question = null;
        int rnd = 0;
        if (questionsByCategory.containsKey(category)) {
            Random random = new Random();
            rnd = random.nextInt(questionsByCategory.get(category).size());
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
     *
     * @param questions (number of questions from each category)
     * @return
     */
    public List<Question> getQuestions(int questions) {
        List<Question> questionsList = new ArrayList<>();
        for (String key : questionsByCategory.keySet()) {
            for (int i = 0; i < questions; i++) {
                questionsList.add(questionsByCategory.get(key).get(i));
            }
        }
        return questionsList;
    }

    /**
     * updates file src/OpenTDB.txt with new questions from OpenTDB.com
     */
    public static void updateListFromOpenTDB() {
        List<String> URLList = new ArrayList<>();
        for (int i = 10; i < 34; i++) {
            URLList.add("https://opentdb.com/api.php?amount=30&category=" + i +"&type=multiple");
        }

        try(FileWriter out = new FileWriter("src/OpenTDB.txt")){
            out.write("");
            out.flush();
            for(String source : URLList) {
                URL url = new URL(source);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                out.write(in.readLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
