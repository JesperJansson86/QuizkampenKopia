package Client;

import MainClasses.GameRound;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Server.*;

public class FakeClient implements Runnable {
    static GameRound gr = new GameRound();
    boolean isPlayer1;
    public static BlockingQueue<Object> toGUI = new LinkedBlockingQueue();
    public static BlockingQueue<Object> toClient = new LinkedBlockingQueue();

    public FakeClient() {
    }

    public static void main(String[] args) {
        FakeClient c = new FakeClient();
        c.run();
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", StartupGameServer.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            gr.playerTurn = Integer.parseInt((String) in.readObject());
            System.out.println("hit1");
            if (gr.playerTurn == 0) {
                System.out.println("INTE hit player 2");
                gr.categoryList = (List) in.readObject();
                System.out.println("Tar emot en kategorilista");
                out.writeObject("Geography");//Skickar tillbaks vald kategori
                gr.qlist = (List) in.readObject();
                //Svara på de tre sista frågorna i listan
                for (int i = 0; i < 3; i++) {
                    if (gr.playerTurn == 1) gr.player1Results.add("Svar" + gr.roundnumber);
                    else gr.player2Results.add("svar" + gr.roundnumber);
                }
                if (gr.playerTurn == 1) out.writeObject(gr.player1Results); //Skickar resultat till servern
                else out.writeObject(gr.player2Results);
                gr.player1Score=(List)in.readObject();
                gr.playerTurn = 1;
                System.out.println("hit2");
            }
            while (true) {
                gr.qlist = (List) in.readObject(); //tar emot frågor
                System.out.println("hit3");
                //Svara på de tre sista frågorna i listan
                for (int i = 0; i < 3; i++) {
                    if (gr.playerTurn == 1) gr.player1Results.add("Svar" + gr.roundnumber);
                    else gr.player2Results.add("svar" + gr.roundnumber);
                }
                if (gr.playerTurn == 1) out.writeObject(gr.player1Results); //Skickar resultat till servern
                else out.writeObject(gr.player2Results);
                System.out.println("hit4");
                gr.player1Score = (List) in.readObject();//Får tillbaks sin lista som ska visa ens "Rektanglar"
                System.out.println("hit4.1");
                gr.player2Score = (List) in.readObject();//Får tillbaks fiendens lista som ska visa fiendes "Rektanglar"
                System.out.println("hit4.2");
                gr.categoryList = (List) in.readObject();//Får kategorilistan
                System.out.println("hit4.3");
                out.writeObject("Geography");//Skickar tillbaks vald kategori
                System.out.println("hit5");
                gr.qlist = (List) in.readObject();//Får frågor
                for (int i = 0; i < 3; i++) {//Svarar på frågor
                    if (gr.playerTurn == 1) gr.player1Results.add("Svar" + gr.roundnumber);
                    else gr.player2Results.add("svar" + gr.roundnumber);
                }
                System.out.println("hit6");
                if (gr.playerTurn == 1) out.writeObject(gr.player1Results); //Skickar resultat till servern
                else out.writeObject(gr.player2Results);
                System.out.println("shit6.1");
                gr.player1Score = (List) in.readObject();//Får tillbaks sin lista som ska visa ens "Rektanglar"
                System.out.println("shit6.2");
                gr.player2Score = (List) in.readObject();//Får tillbaks fiendens lista som ska visa fiendes "Rektanglar"
                System.out.println("hit7");
            }
        } catch (Exception e) {
            System.out.println("Nu blev det fel.");
            e.printStackTrace();
        }
    }
}

//    private void getCategory() {
//        try {
//            System.out.println("GetCategory() toGUI.put CATEGORY");
//            toGUI.put("CATEGORY");
//            System.out.println("GetCategory() toGUI.put categoryList");
//            toGUI.put(gr.categoryList);
//
//            System.out.println("GetCategory() toClient.take String");
//            gr.category = (String) toClient.take();
//            System.out.println("GetCategory() gr.category taken, now :" + gr.category);
//            gr.categoryList.remove(gr.category); //TODO Serverside
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void answerQuestions() {
//        try {
//            List<Question> activeQuestions = new ArrayList<>();
//            for (Question q : gr.qlist) {
//                if (q.getCategory().equalsIgnoreCase(gr.category)) {
//                    activeQuestions.add(q);
//                }
//            }
//            toGUI.put("QUESTION");
//            toGUI.put(activeQuestions);
//            toGUI.put(gr.roundnumber);
//            ArrayList<String> result = (ArrayList<String>) toClient.take();
//            for (String answer : result) {
//                if (isPlayer1) {
//                    gr.player1Results.add(answer);
//                } else {
//                    gr.player2Results.add(answer);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void showResults(){
//        try{
//            toGUI.put("RESULTS");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void goToWaiting(){
//        try{
//            toGUI.put("WAITING");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static GameRound runda(GameRound gr) {
//        int count = 0;
//        while (!gr.qlist.get(count).getCategory().equals(gr.category)) {
//            count++;
//        }
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Kategori " + gr.category);
//        System.out.println("GameId " + gr.gameid);
//        for (int i = 1; i <= 1; i++) {
//            System.out.println("Svara på fråga: " + gr.qlist.get(i + count).getQuestion());
//            System.out.println(gr.qlist.get(i + count).getRightAnswer());
//            System.out.println(gr.qlist.get(i + count).getAnswer2());
//            System.out.println(gr.qlist.get(i + count).getAnswer3());
//            System.out.println(gr.qlist.get(i + count).getAnswer4());
//            int choice = Integer.parseInt(scan.next());
//            String answer;
//            switch (choice) {
//                case 1: {
//                    answer = gr.qlist.get(i + count).getRightAnswer();
//                    break;
//                }
//                case 2: {
//                    answer = gr.qlist.get(i + count).getAnswer2();
//                    break;
//                }
//                case 3: {
//                    answer = gr.qlist.get(i + count).getAnswer3();
//                    break;
//                }
//                case 4: {
//                    answer = gr.qlist.get(i + count).getRightAnswer();
//                    break;
//                }
//
//                default:
//                    throw new IllegalStateException("Unexpected value: " + choice);
//            }
//
//            if (gr.playerTurn % 2 == 1) gr.player1Results.add(answer);
//            else gr.player2Results.add(answer);
//        }
//        return gr;
//    }
////    public void grading(MainClasses.GameRound gr){
////        for (int i = 0; i < gr.player1Results.size(); i++) {
////        if (gr.player1Results.get(i).equals())
////        }
////    }
//}
