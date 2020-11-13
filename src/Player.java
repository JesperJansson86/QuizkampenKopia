
public class Player {
    private static Player player;
    private String name;
    private int score;

    private Player(){}

    public static Player getInstance(){
        if(player == null){
            player = new Player();
        }
        return player;
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
