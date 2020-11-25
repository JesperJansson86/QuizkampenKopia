package GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import Client.Client;
import javafx.scene.image.ImageView;

/**
 * Created by David Hedman <br>
 * Date: 2020-11-25 <br>
 * Time: 15:20 <br>
 * Project: QuizkampenKopia <br>
 * Copyright: Nackademin <br>
 */
public class EndResults {
    @FXML
    public Label player1L;
    public Label player2L;
    public Label player1PointsSum;
    public Label player2PointsSum;
    public Label winnerOrLoser;
    public ImageView trophyImage;

    private int player1Score;
    private int player2Score;
    private boolean isWinner;

    public void initialize(){
        player1L.setText(Client.gr.playerNames.get(0));
        player2L.setText(Client.gr.playerNames.get(1));
        countScore();
        player1PointsSum.setText(String.valueOf(player1Score));
        player2PointsSum.setText(String.valueOf(player2Score));
        setWinOrLose();
        winnerOrLoser.setText(isWinner ? "Congratulations!" : "You lost!");
        trophyImage.setVisible(isWinner);
    }

    private void countScore(){
        for(Integer i: Client.gr.player1Score){
            player1Score += i;
        }
        for(Integer i: Client.gr.player2Score){
            player2Score += i;
        }
    }

    private void setWinOrLose(){
        if(player1Score < player2Score && Client.isPlayer1){
            isWinner = true;
        } else {
            isWinner = false;
        }
    }
}
