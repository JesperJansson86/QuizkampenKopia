package ClientV2;

import MainClasses.GameRound;
import Server.StartupGameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by David Hedman <br>
 * Date: 2020-11-27 <br>
 * Time: 13:28 <br>
 * Project: QuizkampenKopia <br>
 * Copyright: Nackademin <br>
 */
public class Client implements Runnable {
    public static BlockingQueue<Object> toClient = new LinkedBlockingQueue<>();
    public static BlockingQueue<Object> toGUI = new LinkedBlockingDeque<>();
    public static BlockingQueue<Object> toServer = new LinkedBlockingDeque<>();
    public static GameRound gr = new GameRound();
    private static int port = StartupGameServer.port;

    @Override
    public void run() {
        try{
            Socket socket = new Socket("localhost", port);

            //Reads BlockingQueue toServer and sends it to server.
            new Thread(() -> {
                try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                    while (true) {
                        out.writeObject(toServer.take());
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            //Reads Client socket in and puts it in BlockingQueue.
            new Thread(() -> {
                try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                    while (true) {
                        toClient.put(in.readObject());
                    }
                } catch (IOException | InterruptedException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

            //Reads BlockingQueue toClient and does what it does.
            while(true){
                List<Object> packet = (ArrayList) toClient.take();
                resolveFromServer((String) packet.get(0), packet.get(1));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resolveFromServer(String header, Object body) {
        if(header.equals("ISPLAYERONE")){
            System.out.println("I am player one: " + (boolean) body);
        }
    }


    public static void main(String[] args) {
        new Thread(new Client()).start();
    }
}
