package ServerV2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Hedman <br>
 * Date: 2020-11-27 <br>
 * Time: 13:27 <br>
 * Project: QuizkampenKopia <br>
 * Copyright: Nackademin <br>
 */
public class ServerMain {
    public static int port = 5000;
    public static int serverInstance = 0;
    public static List<ServerLogic> serverInstances = new ArrayList<>();

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                Socket client1Socket = serverSocket.accept();
                System.out.println("Client1 connected");
                ServerLogic serverLogic = new ServerLogic(client1Socket);
                new Thread(serverLogic).start();
                Socket client2Socket = serverSocket.accept();
                System.out.println("Client2 connected");
                serverLogic.setAndStartClient2(client2Socket);
                serverInstance++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
