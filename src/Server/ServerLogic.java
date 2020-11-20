package Server;

import MainClasses.GameRound;
import MainClasses.QuestionFactory;

import java.io.*;
import java.util.Scanner;

public class ServerLogic {
    QuestionFactory qf = new QuestionFactory();
    GameRound gr = new GameRound();


    public static void main(String[] args) {

    }

    public GameRound initiateGame() {
        ServerLogic sl = new ServerLogic();
        gr = sl.initateQuestionsAndCategorys();
        gr.roundnumber = -100;
        gr.gameid = sl.initateGameIdByReadingFile();
        sl.writeHistoryToFile();
        System.out.println(gr.gameid);
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

    public GameRound initateQuestionsAndCategorys() {
        gr.categoryList = qf.getCategories();
        for (String s : gr.categoryList) {
            for (int i = 0; i < 3; i++) {
                gr.qlist.add(qf.getRandomQuestionByCategory(s));
            }
        }
        return gr;
    }

}
