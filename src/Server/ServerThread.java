package Server;

import MainClasses.GameRound;

import java.io.*;
import java.net.Socket;

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
            ObjectOutputStream out1 = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream in1 = new ObjectInputStream(socket.getInputStream());
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());

            gr = sl.initiateGame();
            gr.roundnumber = 0;
            while (true) {
                gr.roundnumber++;
                gr.category = null;
                if (gr.roundnumber % 2 == 1) {
                    gr.playerTurn++;
                    System.out.printf("Sending to player1: Roundnumber " + gr.roundnumber);
                    out1.writeObject(gr);
                    gr = (GameRound) in1.readObject();
                    gr.playerTurn++;

                    if (gr.roundnumber > 4) {
                        gr.gameover = true;

                    }
                    System.out.printf("Sending to player2: Roundnumber " + gr.roundnumber);
                    out2.writeObject(gr);
                    gr = (GameRound) in2.readObject();
                } else {
                    gr.playerTurn++;
                    System.out.printf("Sending to player2: Roundnumber " + gr.roundnumber);
                    out2.writeObject(gr);
                    gr = (GameRound) in2.readObject();
                    gr.playerTurn++;

                    if (gr.roundnumber > 4) {
                        gr.gameover = true;

                    }
                    System.out.printf("Sending to player1: Roundnumber " + gr.roundnumber);
                    out1.writeObject(gr);
                    gr = (GameRound) in1.readObject();
                }


            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }


}
