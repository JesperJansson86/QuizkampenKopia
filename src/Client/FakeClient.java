package Client;

import MainClasses.GameRound;
import MainClasses.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Server.*;

public class FakeClient implements Runnable {
    static GameRound gr;
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
        try (Socket socket = new Socket("localhost", Server.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String player = (String)in.readObject();
            System.out.println(player);
            out.writeObject("Hej");

//            FRÅN SERVER
            while (true) {
                String test = (String) in.readObject();
                Scanner scan = new Scanner(System.in);
                System.out.println(test);
//            TILL SERVER
                test = scan.nextLine();
                out.writeObject(test);
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
