package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameStartupServer {
public static Socket socket2;
    public static int port = 5000;

    public static void main(String[] args) {

        new GameStartupServer();
    }

    public GameStartupServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server.Server up and ready.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client1 connected");
                socket2=null;
                new ServerThread2(socket).start();
                socket2 = serverSocket.accept();
                System.out.println("Client2 connected.");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
