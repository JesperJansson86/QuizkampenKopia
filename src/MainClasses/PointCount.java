package MainClasses;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lukas Aronsson
 * Date: 14/11/2020
 * Time: 16:01
 * Project: QuizkampenKopia
 * Copyright: MIT
 **/
public class PointCount {

    private static int nbrRounds = 2; //the number of rounds that will be played //Changed in properties eventually

    private static int nbrQuestions = 2; //the number of questions in a round//Changed in properties eventually



    private static int[][] pointHolder = new int[nbrRounds][nbrQuestions]; //holds the points

    private static int[] pointRoundTotal = new int[nbrRounds]; //holds the totals of the round

    private static ArrayList<Integer> roundTotal = new ArrayList<Integer>(); //holds the round total score for all the rounds

    private static ArrayList<String> answers = new ArrayList<>(); //holds the correct answers +


    /**
     * returns the round total
     *
     * @return returns a list with ints that hold the total score for the round
     */
    public static ArrayList<Integer> getRoundTotal() {return roundTotal;}

    /**
     *
     * @return returns a list with correct answers
     */
    public ArrayList<String> getAnswers(){return answers;};

    public int getNbrRounds() {
        return nbrRounds;
    }

    public void setNbrRounds(int nbr3Rounds) {
        nbrRounds = nbr3Rounds;
    }

    public int getNbrQuestions() {
        return nbrQuestions;
    }

    public void setNbrQuestions(int nbr3Questions) {
        nbrQuestions = nbr3Questions;
    }

    public static int[][] getPointHolder() {
        return pointHolder;
    }

    public static void setPointHolder(int[][] pointHolder) {
        PointCount.pointHolder = pointHolder;
    }

    public static int[] getPointRoundTotal() {
        return pointRoundTotal;
    }

    public static void setPointRoundTotal(int[] pointRoundTotal) {
        PointCount.pointRoundTotal = pointRoundTotal;
    }

    /**
     * adds 1 or 0 to pointHolder
     *
     * @param point  1 or 0 depending on correct answer or not
     */
    public static void playerPointCount(int point,int round,int questions){

        pointHolder[round-1][questions-1] = point;

        System.out.println(Arrays.toString(pointHolder[round-1]));//TEST: printout of the ArrayList

    }

    /**
     * counts and returns the pointTotal of the round.
     *
     * @return returns the pointTotal of the round
     */
    public static int playerRoundTotal(int round){

        //for loops that adds the ints in pointHolder to pointTotal
        for (int integer : pointHolder[round-1]) {

            pointRoundTotal[round-1] = pointRoundTotal[round-1] + integer; //adds the number in point holder to the point total
        }

        System.out.println("Round total:" + pointRoundTotal[round-1]);

        return pointRoundTotal[round-1];
    }

    /**
     *
     * @return returns the total score for all the rounds in the game
     */
    public static  int playerGameTotal( ){


        int total = 0;

        for (int i = 0; i < nbrRounds; i++){

            total = total + pointRoundTotal[i];

        }

        return total;
    }

    /**
     * Stores the correct answer in a arrayList
     * @param answer the correct answer in String form
     */
    public static void answerStore(String answer) {
        answers.add(answer);
    }

}
