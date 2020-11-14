import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameRound implements Serializable {
    int gameid;
    int playerTurn = 1;
    String category = "Välj Kategori!";
    List<Question> qlist;
    List<Boolean> player1Results = new ArrayList<>();
    List<Boolean> player2Results = new ArrayList<>();

    public GameRound(int gameid, List qlist) {
        this.gameid = gameid;
        this.qlist = qlist;
    }


}
