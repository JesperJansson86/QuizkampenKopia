package MainClasses;


import Client.Client;

import java.util.ArrayList;
import java.util.List;

public class GameRoundSingleton {
    private static GameRoundSingleton instance;
    public int gameid = 1;
    public int roundnumber = 0;
    public int playerTurn = 2;
    public int amountOfRounds;
    public int questionsPerRound;
    public String category;
    public boolean gameover = false;
    public List<String> playedCategorys = new ArrayList<>();
    public List<String> categoryList = new ArrayList<>();
    public List<Question> oldQuestionslist = new ArrayList<>();
    public List<Question> qlist = new ArrayList<>();
    public List<String> player1Results = new ArrayList<>();
    public List<String> player2Results = new ArrayList<>();
    public List<String> playerNames = new ArrayList<>();
    public List<Integer> player1Score = new ArrayList<>();
    public List<Integer> player2Score = new ArrayList<>();

    private GameRoundSingleton(){}

    public static GameRoundSingleton getInstance(){
        if(instance == null){
            instance = new GameRoundSingleton();
        }
        return instance;
    }

//    public void convertFromGameRound(){
//
//    }
//
//    public GameRound convertToGameRound(){
//        GameRound gr = new GameRound();
//        return gr;
//    }
}
