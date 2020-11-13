import jdk.jfr.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        startTrivia();

    }

    public static String selectedCategory() {
        System.out.println("Välj en kategori? \n" +
                "1)Sport\n" +
                "2)Math\n");

        switch (sc.nextLine()) {
            case "1": return "Sport";
            case "2": return "Math";
            default:
                System.out.println("Välj med ett tal mellan 1-2");
                sc.nextLine();
        }

        return null;
    }

    public static List<TriviaQuestions> getTriviaQuestions() {
        String selected = selectedCategory();

        if (selected.equalsIgnoreCase("sport")) {
            String question1 = "Vilket fotbollslag spelar inte i Serie A?\n" +
                    "1) Milan \n2) Inter \n3) Tottenham \n4) Juventus";
            String question2 = "Vilket U21 landlag i fotboll vann år 2015?\n" +
                    "1) Sverige \n2) Portugal \n3) Italien \n4) Irland";

            List<TriviaQuestions> sportQuestions = new ArrayList<>();
            sportQuestions.add(new TriviaQuestions(question1, "3"));
            sportQuestions.add(new TriviaQuestions(question2, "1"));
            Collections.shuffle(sportQuestions);

            return sportQuestions;

        } else if (selected.equalsIgnoreCase("math")) {
            String question1 = "1+1?\n" +
                    "1) 11 \n2) 3 \n3) 1 \n4) 2";
            String question2 = "5x2?\n" +
                    "1) 15 \n2) 10 \n3) 5 \n4) 0";

            List<TriviaQuestions> matchQuestions = new ArrayList<>();
            matchQuestions.add(new TriviaQuestions(question1, "4"));
            matchQuestions.add(new TriviaQuestions(question2, "2"));
            Collections.shuffle(matchQuestions);

            return matchQuestions;
        }
        return null;
    }

    public static void startTrivia() {
        List<TriviaQuestions> questionsList = getTriviaQuestions();
        int points = 0;

        for (TriviaQuestions question : questionsList) {
            System.out.println(question.question);
            String answer = sc.nextLine();
            if (answer.equals(question.rightAnswer)) {
                points++;
                System.out.println("Korrekt!");
            } else {
                System.out.println("Fel svar.");
            }
        }

        System.out.println("Du fick " + points + " poäng av " + questionsList.size() + " möjliga.");
    }
}
