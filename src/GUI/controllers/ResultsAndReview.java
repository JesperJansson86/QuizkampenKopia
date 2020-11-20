package GUI.controllers;

import Client.Client;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 19:57
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ResultsAndReview {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    public Label player1L;
    public Label player2L;
    public Group roundGroup;
    public Label roundNumbL;
    public Label player1Points;
    public Label player2Points;
    public Label player2PointsSum;
    public Label player1PointsSum;
}
