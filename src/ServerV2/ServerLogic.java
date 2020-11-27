package ServerV2;

import MainClasses.GameRound;
import MainClasses.QuestionFactory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by David Hedman <br>
 * Date: 2020-11-27 <br>
 * Time: 13:28 <br>
 * Project: QuizkampenKopia <br>
 * Copyright: Nackademin <br>
 */

/**
 * PROTOCOL
 */
public class ServerLogic implements Runnable {
    private Socket c1Socket;
    private Socket c2Socket;
    private BlockingQueue<Object> bQClient1Out = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> bQClient1In = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> bQClient2Out = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> bQClient2In = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> bQServerLogicIn = new LinkedBlockingQueue<>();
    private GameRound gr = new GameRound(); //TODO
    private QuestionFactory questionFactory = new QuestionFactory(); //TODO
    private String player1State;
    private String player2State;

    public ServerLogic(Socket c1Socket) {
        this.c1Socket = c1Socket;
        startClient1();
    }

    public void startClient1() {
        new Thread(new ServerClientThread(bQClient1Out, bQClient1In, bQServerLogicIn, this.c1Socket)).start();
    }

    public void setAndStartClient2(Socket c2Socket) {
        this.c2Socket = c2Socket;
        new Thread(new ServerClientThread(bQClient2Out, bQClient2In, bQServerLogicIn, this.c2Socket)).start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Tell player1: is player1");
            bQClient1Out.put(createPacket("ISPLAYERONE", true));
            System.out.println("Tell player2: is player2");
            bQClient2Out.put(createPacket("ISPLAYERONE", false));
            while (true) {
                List<Object> packet = (ArrayList) bQServerLogicIn.take();
                resolveFromClient((String) packet.get(0),  packet.get(1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resolveFromClient(String header, Object body) throws InterruptedException {
        if(header.equals("CHANGENAME_P1")){
            gr.playerNames.set(0, (String) body);
            bQClient2Out.put(createPacket("P1NAME", body));
            return;
        }
        if(header.equals("CHANGENAME_P2")){
            gr.playerNames.set(1, (String) body);
            bQClient1Out.put(createPacket("P2NAME", body));
            return;
        }
    }

    public Object createPacket(String header, Object body){
        List<Object> packet = new ArrayList<>();
        packet.add(0, header);
        packet.add(1, body);
        return packet;
    }
}
