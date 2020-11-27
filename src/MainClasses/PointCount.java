package MainClasses;

import java.util.ArrayList;

/**
 * Created by Lukas Aronsson
 * Date: 14/11/2020
 * Time: 16:01
 * Project: QuizkampenKopia
 * Copyright: MIT
 **/
public class PointCount {
    private static final ArrayList<Integer> pointHolder = new ArrayList<>(); //holds point for each question
    private static final ArrayList<String> answers = new ArrayList<>(); //holds the user answers in input order
    private static int roundTotal; //total points the user have in the current round

    /**
     * adds 1 or 0 to pointHolder
     *
     * @param point 1 or 0 depending on correct answer or not
     */
    public static void playerPointCount(int point) {
        pointHolder.add(point); //adds point to pointHolder
    }

    /**
     * counts and returns the pointTotal of the round
     */
    public static void playerRoundTotal() {
        for (int integer : pointHolder)//for loops that adds the ints in pointHolder to pointTotal
            roundTotal = roundTotal + integer; //adds the number in point holder to the point total
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
     * Getter for an ArrayList that holds the answers that the user pressed in string form
     *
     * @return returns a list with correct answers
     */
    public static ArrayList<String> getAnswers() {
        return answers;
    }

    /**
     * Getter for the players total score of the round
     *
     * @return roundTotal
     */
    public static int getRoundTotal() {
        return roundTotal;
    }

    /**
     * Clears point holder & sets the value of roundTotal to 0
     */
    public static void reset() {
        pointHolder.clear();
        roundTotal = 0;
    }

}