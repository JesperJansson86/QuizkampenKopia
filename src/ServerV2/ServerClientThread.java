package ServerV2;

/**
 * Created by David Hedman <br>
 * Date: 2020-11-27 <br>
 * Time: 13:28 <br>
 * Project: QuizkampenKopia <br>
 * Copyright: Nackademin <br>
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * PROTOCOL ArrayList (0) = String header<br>
 * ArrayList (1) = Object to be handled.<br>
 * <p>
 * Header:<br>
 * PLAYERORDER : isPlayer1 boolean <br>
 *
 */
public class ServerClientThread implements Runnable {
    private BlockingQueue<Object> clientOut;
    private BlockingQueue<Object> clientIn;
    private BlockingQueue<Object> serverLogicIn;
    private Socket socket;

    public ServerClientThread(BlockingQueue clientOut, BlockingQueue clientIn, BlockingQueue serverLogicIn, Socket socket) {
        this.clientIn = clientIn;
        this.clientOut = clientOut;
        this.serverLogicIn = serverLogicIn;
        this.socket = socket;
    }

    @Override
    public void run() {
        new Thread(() -> {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                while (true) {
                    out.writeObject(clientOut.take());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                while (true) {
                    serverLogicIn.put(in.readObject());
                }
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
