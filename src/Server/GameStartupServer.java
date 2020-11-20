package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class GameStartupServer {
    public static Socket socket2;
    static ServerThread2 ts2;
    public static int port = 5000;

    public static void main(String[] args) throws IOException {

        GameStartupServer gs = new GameStartupServer();

    }

    public GameStartupServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server.Server up and ready.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client1 connected");

                socket2 = null;
                 ts2 = new ServerThread2(socket);
                ts2.start();
                synchronized (ts2){
                    ts2.notify();
                }
                socket2 = serverSocket.accept();
                System.out.println("Client2 connected.");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void gameStartup() {


    }
}