package Client;

import GUI.controllers.Intro;
import GUI.models.GUIutils;
import MainClasses.GameRound;
import Server.Server;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable {
    Server s;
    GameRound gr=new GameRound();
    public static BlockingQueue<Object> toGUI=new LinkedBlockingQueue();
    public static BlockingQueue<Object> toClient =new LinkedBlockingQueue();

    @Override
    public void run() {

        try (Socket socket = new Socket("localhost",s.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

while(true){
            //testing a Client-GUI protocol
            String name=(String)toClient.take();
            //send to server this name
            if(name.equals("hodei"))//<-have to check something with the name, for now checks if its hodei written
                toGUI.put(true);
            else
                toGUI.put(false);}
        /*
            while (true) {

                gr = (GameRound) in.readObject();
                System.out.println(gr.category);
                gr.category = scan.next();


                out.writeObject(gr);

            }

         */


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
