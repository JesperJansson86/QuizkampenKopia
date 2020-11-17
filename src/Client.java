import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    GameRound local = new GameRound();

    public static void main(String[] args) {

        new Client();
    }

    public Client() {

        try (Socket socket = new Socket("localhost", Server.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scan = new Scanner(System.in);
            String test = null;

            while (true) {

                GameRound gr = (GameRound) in.readObject();

                if (gr.category != null) {
                    gr = runda(gr);
                    local = gr;
                    if (gr.gameover && gr.playerTurn % 2 == 1) break;
                }

                System.out.println("Kategori?" + gr.categoryList.toString());
                String temp = scan.nextLine();
                gr.playedCategorys.add(temp);
                gr.category=temp;

                gr = runda(gr);
                if (gr.playerTurn % 2 == 1) System.out.println(gr.player1Results);
                else System.out.println(gr.player2Results);
                gr.categoryList.remove(temp);
                out.writeObject(gr);
                local = gr;
                if (gr.gameover && gr.playerTurn % 2 == 0) break;
            }
            if (local.playerTurn % 2 == 1) System.out.println(local.player1Results);
            else System.out.println(local.player2Results);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static GameRound runda(GameRound gr) {
        int count = 0;
        while (!gr.qlist.get(count).getCategory().equals(gr.category)) {
            count++;
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Kategori " + gr.category);
        System.out.println("GameId " + gr.gameid);
        for (int i = 1; i <= 1; i++) {
            System.out.println("Svara på fråga: " + gr.qlist.get(i + count).getQuestion());
            System.out.println(gr.qlist.get(i + count).getRightAnswer());
            System.out.println(gr.qlist.get(i + count).getAnswer2());
            System.out.println(gr.qlist.get(i + count).getAnswer3());
            System.out.println(gr.qlist.get(i + count).getAnswer4());
            int choice = Integer.parseInt(scan.next());
            String answer;
            switch (choice) {
                case 1: {
                    answer = gr.qlist.get(i + count).getRightAnswer();
                    break;
                }
                case 2: {
                    answer = gr.qlist.get(i + count).getAnswer2();
                    break;
                }
                case 3: {
                    answer = gr.qlist.get(i + count).getAnswer3();
                    break;
                }
                case 4: {
                    answer = gr.qlist.get(i + count).getRightAnswer();
                    break;
                }

                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

            if (gr.playerTurn % 2 == 1) gr.player1Results.add(answer);
            else gr.player2Results.add(answer);
        }
        return gr;
    }
//    public void grading(GameRound gr){
//        for (int i = 0; i < gr.player1Results.size(); i++) {
//        if (gr.player1Results.get(i).equals())
//        }
//
//
//
//
//    }
}
