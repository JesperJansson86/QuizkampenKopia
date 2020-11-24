package Server;

import MainClasses.GameRound;
import MainClasses.Question;
import MainClasses.QuestionFactory;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerLogic2 {
    QuestionFactory qf = new QuestionFactory();
    GameRound gr = new GameRound();
    public int amountOfQuestions = 3;
    int questionsAlreadyInList = 0;


    public static void main(String[] args) {

    }

    public GameRound initiateGame() {
        gr.playerTurn = 1;
        ServerLogic2 sl = new ServerLogic2();
        gr.roundnumber = 0;
        gr.gameid = sl.initateGameIdByReadingFile();
        sl.writeHistoryToFile();
        gr.categoryList = qf.getCategories();
        return gr;

    }

    public void writeHistoryToFile() {
        try (ObjectOutputStream toFile = new ObjectOutputStream(new FileOutputStream("src\\GameHistory.txt", true));) {
            toFile.writeObject("game");
            toFile.flush();
        } catch (IOException e) {
            System.out.println("Filen gick ej att skriva till");
        }
    }

    public int initateGameIdByReadingFile() {

        try (Scanner scanFile = new Scanner(new FileReader("src\\GameHistory.txt"));) {
            while (scanFile.hasNextLine()) {
                scanFile.next();
                gr.gameid++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Filen kunde inte hittas.");
        }
        return gr.gameid;
    }

    public List buildQuestionsfromCategoryChoice(GameRound gr) {
        List<Question> alist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            alist.add(qf.getRandomQuestionByCategory(gr.category));
            System.out.println(alist.get(i));
        }

return alist;
    }

    public List gradeAnswers(List qlist, List alist) {
        List<Question> questionList = new ArrayList<>();
        List<Integer> gradeList = new ArrayList<>();
        questionList = qlist;
        for (int i = 0; i < amountOfQuestions; i++) {
            System.out.println(questionList.get(0));
            System.out.println(questionList.get(questionList.size()+i-3).getRightAnswer());
            if (alist.get(alist.size()+i-3).equals( questionList.get(questionList.size()+i-3).getRightAnswer())) { //Detta är för att alltid ta de tre sista i listan.
                gradeList.add(1);
            } else {
                gradeList.add(0);
            }

        }
        return gradeList;

    }
    public void buildOldquestionsListAndClearQlist(){
        for (int i = 0; i < amountOfQuestions; i++) {
            gr.oldQuestionslist.add(gr.qlist.get(i));

        }
        gr.qlist.clear();
    }
    public GameRound isGameOver(GameRound gr){
        if (gr.roundnumber == gr.amountOfRounds)gr.gameover=true;
        return gr;
    }
}
