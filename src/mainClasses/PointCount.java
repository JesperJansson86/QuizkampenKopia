package mainClasses;

import java.util.ArrayList;

/**
 * Created by Lukas Aronsson
 * Date: 14/11/2020
 * Time: 16:01
 * Project: QuizkampenKopia
 * Copyright: MIT
 **/
public class PointCount {


    private static int roundTotal; //total points the user have in the current round

    private static final ArrayList<Integer> pointHolder = new ArrayList<>(); //holds point for each question

    private static final ArrayList<String> answers = new ArrayList<>(); //holds the user answers in input order



    /**
     * adds 1 or 0 to pointHolder
     *
     * @param point 1 or 0 depending on correct answer or not
     */
    public static void playerPointCount(int point) {

        pointHolder.add(point); //adds point to pointHolder

        System.out.println(pointHolder.toString());//TEST: printout of the ArrayList

    }

    /**
     * counts and returns the pointTotal of the round
     */
    public static int playerRoundTotal() {

        //for loops that adds the ints in pointHolder to pointTotal
        for (int integer : pointHolder) {

            roundTotal = roundTotal + integer; //adds the number in point holder to the point total
        }

        System.out.println("Round total:" + roundTotal);

        return roundTotal;
    }

    /**
     * Stores the correct answer in a arrayList
     *
     * @param answer the correct answer in String form
     */
    public static void storeAnswer(String answer) {
        answers.add(answer);
    }

    /**
     * @return returns a list with correct answers
     */
    public static ArrayList<String> getAnswers() {
        return answers;
    }

    public static ArrayList<Integer> getPointHolder() {
        return pointHolder;
    }

    public static int getRoundTotal() {
        return roundTotal;
    }

    /**
     * @return returns the total score for all the rounds in the game
     */
    public static int playerGameTotal() {

        /*

        // TODO: 20/11/2020 remake server side
        int total = 0;
        for (int i = 0; i < nbrRounds; i++) {

            total = total + roundTotal;

        }

        return total;
        */


        return 0;
    }

    /**
     * Prints out all the data currently in PointCount, for testing purposes.
     */
    public static void testPointCount() {

        //test printouts
        System.out.println("PointCount Test : \n");
        System.out.println("playerPointCount: "+pointHolder.toString()); //test: sOut pointHolder[][]
        System.out.println("roundTotal: " + roundTotal); //test: sOut for round total ArrayList<Integer>
        System.out.println("answers: " + getAnswers()); //test: sOut for answers ArrayList<String>
        System.out.println("\n");
    }

    public static void reset(){
        pointHolder.clear();
        roundTotal = 0;
    }

}