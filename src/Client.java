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
                    if (gr.gameover&& gr.playerTurn % 2 == 1) break;
                }

                System.out.println("Kategori?");
                gr.category = scan.nextLine();
                gr = runda(gr);
                System.out.println(gr.playerTurn);
                if (gr.playerTurn % 2 == 1) System.out.println(local.player1Results);
                else System.out.println(local.player2Results);

                out.writeObject(gr);
                local = gr;
                if (gr.gameover&& gr.playerTurn % 2 == 0) break;
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

        Scanner scan = new Scanner(System.in);
        System.out.println("Kategori " + gr.category);
        System.out.println("GameId " + gr.gameid);
        System.out.println("Rund-nummer " + gr.roundnumber);
        for (int i = 1; i <= 1; i++) {
            System.out.println("Svar på frågaa: " + gr.qlist.get(i).getQuestion());
            if (gr.playerTurn % 2 == 1) gr.player1Results.add(scan.nextLine());
            else gr.player2Results.add(scan.nextLine());
        }
        return gr;
    }
}
