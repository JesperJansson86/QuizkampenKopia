package Server;

import MainClasses.GameRound;
import MainClasses.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread2 extends Thread {
    Socket socket;
    Socket socket2;
    ServerLogic2 sl = new ServerLogic2();
    List<Question> wtflist = new ArrayList<>();
    GameRound gr = new GameRound();


    public ServerThread2(Socket socket) throws IOException {

        this.socket = socket;
//        this.socket2 = socket2;
    }

    @Override
    public void run() {

        try {


            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            gr=sl.initiateGame();
            out.writeObject("0"); //Talar om att det är första rundan.
            out.writeObject(gr.categoryList); //Skickar kategorilista till spelare ett
            gr.category=(String)in.readObject();//Sätter kategorin till vald kategori.
            gr.categoryList.remove(gr.category);//Tar bort vald kategori från listan
            gr.qlist=sl.buildQuestionsfromCategoryChoice(gr);//Skapar frågor i den kategorin
            out.writeObject(gr.qlist);// Skickar frågorna till spelare 1
            gr.player1Results=(List)in.readObject();//Tar emot svaren
            gr.player1Score=sl.gradeAnswers(gr.qlist,gr.player1Results);//Rättar frågorna och sparar resulstatet
            out.writeObject(gr.player1Score); //Skickar resultat till spelare 1

            socket2 = (Socket) GameStartupServer.waitForPLayer2.take(); //Väntar på att spelare2 ansluter.
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
            out2.writeObject("2");

            while (true) {
                gr.playerTurn++;
                out2.writeObject(gr.qlist);//Skickar frågor till spelare2
                gr.player2Results=(List)in2.readObject();//Tar emot svar
                gr.player2Score=sl.gradeAnswers(gr.qlist,gr.player2Results);//Rättar
                out2.writeObject(gr.player1Score);//skickar spelare 1s poäng
                out2.writeObject(gr.player2Score);//skickar spelare 2s poäng
                System.out.println("shit1");
//                gr = sl.isGameOver(gr);
//                if (gr.gameover){
//                    out.writeObject(gr);
//                    out2.writeObject(gr);
//                    break;
//                }
                System.out.println("shit2");
                out2.writeObject(gr.categoryList);//Skickar kategorilistan
                System.out.println("shit3");
                gr.category=(String)in2.readObject();//Tar emot val av kategori
                gr.qlist=sl.buildQuestionsfromCategoryChoice(gr);
                System.out.println("fakk");
                out2.writeObject(gr.qlist);// Skickar frågorna till spelare2
                System.out.println("fakk2");
                gr.player2Results=(List)in2.readObject();//Tar emot svaren
                System.out.println("fakk3");
                gr.player2Score=sl.gradeAnswers(gr.qlist,gr.player2Results);//Rättar frågorna och sparar resulstatet
                System.out.println("fakk4");
                out2.writeObject(gr.player1Score); //Skickar resultat till spelare 2
                System.out.println("fakk5");
                out2.writeObject(gr.player2Score); //Skickar resultat till spelare 2
                System.out.println("fakk5.2");
                gr.playerTurn--;
                gr.roundnumber++;
                System.out.println("shit3.1");
                out.writeObject(gr.qlist);//Skickar frågor till spelare1
                System.out.println("shit3.11");
                gr.player1Results=(List)in.readObject();//Tar emot svar
                System.out.println("shit3.12");
                gr.player1Score=sl.gradeAnswers(gr.qlist,gr.player1Results);//Rättar
                System.out.println("shit3.2");
                out.writeObject(gr.player1Score);//skickar spelare 1s poäng
                out.writeObject(gr.player2Score);//skickar spelare 2s poäng
                out.writeObject(gr.categoryList);//Skickar kategorilistan
                System.out.println("shit3.3");
                gr.category=(String)in.readObject();//Tar emot val av kategori
                gr.qlist=sl.buildQuestionsfromCategoryChoice(gr);
                out.writeObject(gr.qlist);// Skickar frågorna till spelare1
                gr.player1Results=(List)in.readObject();//Tar emot svaren
                System.out.println("shit4");
                gr.player1Score=sl.gradeAnswers(gr.qlist,gr.player1Results);//Rättar frågorna och sparar resulstatet
                System.out.println("shit5");
                out.writeObject(gr.player1Score); //Skickar resultat till spelare 1
                System.out.println("shit6");
                out.writeObject(gr.player2Score); //Skickar resultat till spelare 1
                System.out.println("shit7");

            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();

        }
    }


}
