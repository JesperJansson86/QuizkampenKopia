package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class StartupGameServer {
    private static Socket socket2;
    private static ServerThreadNew ts2;
    public static int port = 5000;
    protected static BlockingQueue<Object> waitForPLayer2 = new LinkedBlockingQueue();

    public static void main(String[] args) throws IOException {

        StartupGameServer gs = new StartupGameServer();

    }

    /**
     * StartupGameServer waits for someone to connect to the and create a Socket and then starts a new ServerThreadNew
     * the next Socket is added to a BlockingQueue that is then accessed from within that thread.
     * @throws IOException
     */
    public StartupGameServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is online and waiting for players.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client1 connected");
                ts2 = new ServerThreadNew(socket);
                ts2.start();
                socket2 = serverSocket.accept();
                waitForPLayer2.add(socket2);
                System.out.println("Client2 connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}