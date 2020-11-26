package MainClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameRound implements Serializable {
    public int gameid = 1;
    public int roundnumber = 1;
    public int playerTurn = 0;
    public String category = null;
    public boolean gameover = false;
    public List<String> categoryList = new ArrayList<>();
    public List<Question> qlist = new ArrayList<>();
    public List<String> player1Results = new ArrayList<>();
    public List<String> player2Results = new ArrayList<>();
    public List<String> playerNames = new ArrayList<>();
    public List<Integer> player1Score = new ArrayList<>();
    public List<Integer> player2Score = new ArrayList<>();
    public List<Question> activeQuestions = new ArrayList();


}
