package server;

import mainClasses.GameRound;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ServerThreadNew extends Thread {
    private Socket socket;
    private Socket socket2;
    private ServerLogic sl = new ServerLogic();
    private GameRound gr;
    private Properties p = new Properties();


    public ServerThreadNew(Socket socket)  {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            p.load(new FileInputStream("src\\config.properties"));
        } catch (Exception e) {
            System.out.println("Properties filen config.properties kunde inte läsas");
            e.printStackTrace();
        }
        try {//Streams to Client1 is initiated
            ObjectOutputStream out1 = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in1 = new ObjectInputStream(socket.getInputStream());
            //Initiates game and player1 can play his first round before player2 is connected
            gr = sl.initiateGame();
            gr.playerTurn++;
            player1Turn(out1, in1);
            //player 2 connects
            socket2 = (Socket) StartupGameServer.waitForPLayer2.take();
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            if (Integer.parseInt(p.getProperty("amountOfRounds", "1")) != 1) {
                //player 2 gets the first roundinformation
                out2.writeObject(gr);
                gr = (GameRound) in2.readObject();
                //Iteration of rounds begin
                while (true) {
                    gr.category = null;
                    gr.roundnumber++;
                    //Roundnumber is used to determine whether its player1 or player2s turn to go.
                    if (gr.roundnumber % 2 == 1 && gr.roundnumber != 1) {
                        gr.playerTurn++;
                        player1Turn(out1, in1);

                        if (gr.playerTurn >= Integer.parseInt(p.getProperty("amountOfRounds", "1"))) {
                            break;
                        }
                        player2Turn(out2, in2);

                    } else if (gr.roundnumber % 2 == 0) {

                        gr.playerTurn++;
                        player2Turn(out2, in2);

                        if (gr.playerTurn >= Integer.parseInt(p.getProperty("amountOfRounds", "1"))) {
                            break;
                        }
                        player1Turn(out1, in1);

                    }
                }
            }

            if (gr.roundnumber % 2 == 0) {
                player1Turn(out1, in1);
                gr.gameover = true;

            }
            if (gr.roundnumber % 2 == 1) {
                player2Turn(out2, in2);
                gr.gameover = true;
            }

            out1.writeObject(gr);
            out2.writeObject(gr);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    /**
     * send a GameRound to the playerone and recieves one back when player has completed it
     * @param out1 the ObjectOutputStream
     * @param in1 the ObjectInputstream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void player1Turn(ObjectOutputStream out1, ObjectInputStream in1) throws IOException, ClassNotFoundException {
        System.out.println("Sending to player1: Roundnumber " + gr.roundnumber);
        out1.writeObject(gr);
        gr = (GameRound) in1.readObject();


    }

    /**
     * send a GameRound to the playertwo and recieves one back when player has completed it
     * @param out2 the ObjectOutputStream
     * @param in2 the ObjectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void player2Turn(ObjectOutputStream out2, ObjectInputStream in2) throws IOException, ClassNotFoundException {
        System.out.println("Sending to player2: Roundnumber " + gr.roundnumber);
        out2.writeObject(gr);
        gr = (GameRound) in2.readObject();


    }


}
