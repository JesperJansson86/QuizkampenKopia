package Server;

import MainClasses.GameRound;
import MainClasses.QuestionFactory;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class ServerLogic {
   private Properties p = new Properties();
   private QuestionFactory qf = new QuestionFactory();
   private GameRound gr = new GameRound();


    public static void main(String[] args) {

    }

    /**
     * Generates questions and writes in the Gamehostory-file.
     * @return returns the starting GameRound
     */
    public GameRound initiateGame() {
        ServerLogic sl = new ServerLogic();
        gr = sl.initateQuestionsAndCategorys();
        sl.writeHistoryFile();
        System.out.println("Game ID: "+gr.gameid);
        return gr;

    }

    /**
     * Reaads the amount if entrys in the historyfile and uses that to increase gameid counter. Writes this games
     * id into the file.
     */
    public void writeHistoryFile() {
        try (PrintWriter toFile = new PrintWriter(new FileWriter("src\\GameHistory.txt", true));
            Scanner scanFile = new Scanner(new FileReader("src\\GameHistory.txt"))) {
            while (scanFile.hasNextLine()) {
                scanFile.nextLine();
                gr.gameid++;}
            toFile.println(+gr.gameid);
            toFile.flush();
        } catch (IOException e) {
            System.out.println("Filen gick ej att skriva till");
        }
    }

    /**
     * generates a set amount of random question for each category
     * @return resturns a GameRound with a updated List of questions
     */
    public GameRound initateQuestionsAndCategorys() {
        try{
            p.load(new FileInputStream("src\\config.properties"));
        }catch (Exception e){
            System.out.println("Properties filen config.properties kunde inte läsas");
        e.printStackTrace();}
        gr.categoryList = qf.getCategories();
        for (String s : gr.categoryList) {
            for (int i = 0; i < Integer.parseInt(p.getProperty("amountOfQuestions")) ; i++) {
                gr.qlist.add(qf.getRandomQuestionByCategory(s));
            }
        }
        return gr;
    }

}
