package MainClasses;

import MainClasses.Question;
import MainClasses.QuestionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameRound implements Serializable {
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

//    public MainClasses.GameRound(int gameid, List qlist) {
//        this.gameid = gameid;
//        this.amountOfRounds= amountOfRounds;
//        this.questionsPerRound=questionsPerRound;
//        this.qlist=qlist;
//    }


}
