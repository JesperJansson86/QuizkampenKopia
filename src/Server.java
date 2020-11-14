import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static int port = 5000;

    public static void main(String[] args) {

        new Server();
    }

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server up and ready.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client1 connected");
                Socket socket2 = serverSocket.accept();
                System.out.println("Client2 connected.");
                new ServerThread(socket, socket2).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
