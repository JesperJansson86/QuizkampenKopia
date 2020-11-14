import java.util.ArrayList;

/**
 * Created by Lukas Aronsson
 * Date: 14/11/2020
 * Time: 16:01
 * Project: QuizkampenKopia
 * Copyright: MIT
 **/
public class PointCount {

    //holds the points for the current round
    static ArrayList<Integer> pointHolder = new ArrayList<>();

    static int pointTotal = 0;


    /**
     * adds 1 or 0 to pointHolder
     *
     * @param point  1 or 0 depending on correct answer or not
     */
    static void playerPointCount(int point){

        pointHolder.add(point); // just adds it to pointHolder


        //System.out.println(pointHolder.toString()); //TEST: printout of the ArrayList

    }

    /**
     * counts and returns the pointTotal of the round.
     *
     * @return returns the pointTotal of the round
     */
    static int playerPointTotal(){

        //for loops that adds the ints in pointHolder to pointTotal
        for (Integer integer : pointHolder) {

            pointTotal = pointTotal + integer; //adds the number in point holder to the point total
        }

        return pointTotal;
    }

    /**
     * returns a Arraylist of Integers that represent the answers of the players
     *
     * @return returns a Arraylist of Integers
     */
    public static ArrayList<Integer> getPointHolder() {
        return pointHolder;
    }
}
