package Server;

import MainClasses.GameRound;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread2 extends Thread {
    Socket socket;
    Socket socket2;
    ServerLogic sl = new ServerLogic();
    GameRound gr = new GameRound();


    public ServerThread2(Socket socket) throws IOException {

        this.socket = socket;
//        this.socket2 = socket2;
    }

    @Override
    public void run() {

        try {


            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("Player1");
            out.writeObject("start");
            String answer = (String) in.readObject();
            System.out.println(answer);
            synchronized (GameStartupServer.ts2) {
                wait();
            }
//            while (GameStartupServer.socket2 == null) {
//                sleep(1000);
//                System.out.println("No player2 yet");
//
//            }

            socket2 = GameStartupServer.socket2;

            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
            out2.writeObject("Player2");
            out2.writeObject(answer);
            while (true) {

                answer = (String) in2.readObject();
                System.out.println(answer);
                out.writeObject(answer);
                answer = (String) in.readObject();
                out2.writeObject(answer);
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();

        }
    }


}
