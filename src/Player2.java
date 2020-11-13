/**
 * Created by David Hedman <br>
 * Date: 2020-11-12 <br>
 * Time: 16:39 <br>
 * Project: QuizkampenKopia <br>
 * Copyright: Nackademin <br>
 */
public class Player2 {
    private static Player2 player;
    private String name;
    private int score;

    public Player2 getInstance(){
        if(this.player == null){
            this.player = new Player2();
        }
        return this.player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }


}
