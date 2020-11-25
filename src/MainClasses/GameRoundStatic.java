package MainClasses;

import MainClasses.Question;
import MainClasses.QuestionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameRoundStatic implements Serializable {
    public static String nameTemp;
    public static int gameid = 1;
    public static int roundnumber = 0;
    public static int playerTurn = 2;
    public static int amountOfRounds;
    public static int questionsPerRound;
    public static String category;
    public static boolean gameover = false;
    public static List<String> playedCategorys = new ArrayList<>();
    public static List<String> categoryList = new ArrayList<>();
    public static List<Question> oldQuestionslist = new ArrayList<>();
    public static List<Question> qlist = new ArrayList<>();
    public static List<String> player1Results = new ArrayList<>();
    public static List<String> player2Results = new ArrayList<>();
    public static List<String> playerNames = new ArrayList<>();
    public static List<Integer> player1Score = new ArrayList<>();
    public static List<Integer> player2Score = new ArrayList<>();



}
