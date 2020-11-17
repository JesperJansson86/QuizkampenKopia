import MainClasses.Question;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerThread extends Thread {
    Socket socket;
    Socket socket2;
    ServerLogic sl = new ServerLogic();
    GameRound gr = new GameRound();


    public ServerThread(Socket socket, Socket socket2) throws IOException {

        this.socket = socket;
        this.socket2 = socket2;
    }

    @Override
    public void run() {

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());

            gr = sl.initiateGame();

            while (true) {
                gr.roundnumber++;
                gr.playerTurn++;
                out.writeObject(gr);
                gr = (GameRound) in.readObject();
                gr.playerTurn++;

                if (gr.roundnumber > 1) gr.gameover = true;
                out2.writeObject(gr);
                gr = (GameRound) in2.readObject();


            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }


}
