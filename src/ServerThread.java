import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ServerThread extends Thread {
    Socket socket;
    Socket socket2;


    public ServerThread(Socket socket, Socket socket2) {

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

//                Thread t1 = new Thread(new ServerToClientThread(in,out2));
//                Thread t2 = new Thread(new ServerToClientThread(in2,out));
//                t1.start();
//                t2.start();
            List<Question> qlist = new ArrayList<>();
            String svar = "start";
            GameRound gr = new GameRound(1,qlist);

            while (true) {
                out.writeObject(gr);
                gr=(GameRound)in.readObject();
                out2.writeObject(gr);
                gr=(GameRound)in2.readObject();

//                        svar = (String) in.readObject();
//                        out2.writeObject(svar);
//                        svar = (String) in2.readObject();
//                        out.writeObject(svar);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();

                }
            }


        }
