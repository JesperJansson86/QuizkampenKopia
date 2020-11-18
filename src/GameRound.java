import MainClasses.Question;
import MainClasses.QuestionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameRound implements Serializable {
    public int gameid = 1;
    public int roundnumber = 0;
    int playerTurn = 2;
    int amountOfRounds;
    int questionsPerRound;
    String category;
    boolean gameover = false;
    List<String> playedCategorys = new ArrayList<>();
    List<String> categoryList = new ArrayList<>();
    List<Question> qlist = new ArrayList<>();
    List<String> player1Results = new ArrayList<>();
    List<String> player2Results = new ArrayList<>();
    List<String> playerNames = new ArrayList<>();

//    public GameRound(int gameid, List qlist) {
//        this.gameid = gameid;
//        this.amountOfRounds= amountOfRounds;
//        this.questionsPerRound=questionsPerRound;
//        this.qlist=qlist;
//    }


}
