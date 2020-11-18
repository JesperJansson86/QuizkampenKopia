package GUI.controllers;

import MainClasses.PointCount;
import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ResultsAndReview {
    public Label player1L;
    public Label player2L;
    public Group roundGroup;
    public Label roundNumbL;
    public static Label player1Points;
    public Label player2Points;
    public static Label player1PointsSum;
    public Label player2PointsSum;


    /**
     * the player1 result for the round
     *
     * @param p1Points the player1 result for the current round
     */
    public static void setPlayer1Points(int p1Points) {
        player1Points.setText(String.valueOf(p1Points));
    }

    /**
     * the player1 result for the round
     *
     * @param p2Points the player2 result for the current round
     */
    public void setPlayer2Points(int p2Points) {
        player1Points.setText(String.valueOf(p2Points));
    }

    /**
     * Player1s result for all the rounds
     *
     * @param player1Sum the total result of all the rounds (player1)
     */
    public static void setPlayer1PointsSum(int player1Sum) {
        player1Points.setText(String.valueOf(player1Sum));
    }

    /**
     * Player1s result for all the rounds
     *
     * @param player2Sum the total result of all the rounds (player2)
     */
    public void setPlayer2PointsSum(int player2Sum) {
        player1Points.setText(String.valueOf(player2Sum));
    }
}