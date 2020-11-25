package Server;

import MainClasses.GameRound;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ServerThreadNew extends Thread {
    Socket socket;
    Socket socket2;
    ServerLogic sl = new ServerLogic();
    GameRound gr = new GameRound();
    Properties p = new Properties();


    public ServerThreadNew(Socket socket) throws IOException {

        this.socket = socket;
//        this.socket2 = socket2;
    }

    @Override
    public void run() {
        try {
            p.load(new FileInputStream("src\\config.properties"));
        } catch (Exception e) {
            System.out.println("Properties filen config.properties kunde inte läsas");
            e.printStackTrace();
        }

        try {
            ObjectOutputStream out1 = new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream in1 = new ObjectInputStream(socket.getInputStream());

            gr = sl.initiateGame();
            gr.roundnumber = 1;
            gr.category = null;
            gr.playerTurn++;
            System.out.printf("Sending to player1: Roundnumber " + gr.roundnumber);
            out1.writeObject(gr);
            gr = (GameRound) in1.readObject();
            gr.playerTurn++;

            socket2 = (Socket) GameStartupServer.waitForPLayer2.take();
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            out2.writeObject(gr);
            gr = (GameRound) in2.readObject();

            while (true) {

                gr.category = null;
                gr.roundnumber++;
                if (gr.roundnumber % 2 == 1 && gr.roundnumber != 1) {
                    gr.playerTurn++;
                    System.out.printf("Sending to player1: Roundnumber " + gr.roundnumber);
                    out1.writeObject(gr);
                    gr = (GameRound) in1.readObject();
                    gr.playerTurn++;
                    if (gr.roundnumber >= Integer.parseInt(p.getProperty("amountOfRounds", "3"))) {
                        gr.gameover = true;
                    }
                    System.out.printf("Sending to player2: Roundnumber " + gr.roundnumber);
                    out2.writeObject(gr);
                    gr = (GameRound) in2.readObject();
                } else if (gr.roundnumber % 2 == 0) {
                    gr.playerTurn++;
                    System.out.printf("Sending to player2: Roundnumber " + gr.roundnumber);
                    out2.writeObject(gr);
                    gr = (GameRound) in2.readObject();
                    gr.playerTurn++;
                    if (gr.roundnumber >= Integer.parseInt(p.getProperty("amountOfRounds", "3"))) {
                        gr.gameover = true;
                    }
                    System.out.printf("Sending to player1: Roundnumber " + gr.roundnumber);
                    out1.writeObject(gr);
                    gr = (GameRound) in1.readObject();

                }

            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();

        }
    }


}
