package Client;

import MainClasses.GameRound;
import MainClasses.PointCount;
import MainClasses.Question;
import Server.*;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable {
    static GameRound gr;
    boolean isPlayer1;
    public static BlockingQueue<Object> toGUI = new LinkedBlockingQueue();
    public static BlockingQueue<Object> toClient = new LinkedBlockingQueue();

    public Client() {
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", Server.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            System.out.println("in Client, just before taking name");
            String name = (String) toClient.take();
//            String playerNumber = (String) in.readObject();
            goToWaiting("Waiting for opponent");
            while (true) {

                System.out.println("in Client, just before add name");
                gr = (GameRound) in.readObject(); //add names if needed, ergo, first round.
                if (gr.playerNames.isEmpty()){
                    isPlayer1 = true;
                    gr.playerNames.add(name);
                } else if (gr.playerNames.size() == 1){
                    isPlayer1 = false;
                    gr.playerNames.add(name);
                }

                System.out.println("in Client gr.category = " + gr.category);

                if (gr.category == null) { //Choose category and answer questions
                    System.out.println("in Client: gr.category is null.");
                    getCategory();
                    System.out.println("in Client: gr.category is now: " + gr.category);
                    answerQuestions();
                    showResults();

                } else if (gr.category != null) {
                    System.out.println("in Client: gr.category is " + gr.category);
                    answerQuestions();
                    showResults();

                    getCategory();
                    answerQuestions();
                }

                out.writeObject(gr);
                goToWaiting("End of Round, waiting for opponent/server");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getCategory() {
        try {
            System.out.println("GetCategory() toGUI.put CATEGORY");
            toGUI.put("CATEGORY");
            System.out.println("GetCategory() toGUI.put categoryList");
            toGUI.put(gr.categoryList);

            System.out.println("GetCategory() toClient.take String");
            gr.category = (String) toClient.take();
            System.out.println("GetCategory() gr.category taken, now :" + gr.category);
            gr.categoryList.remove(gr.category); //TODO Serverside
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void answerQuestions() {
        try {
            List<Question> activeQuestions = new ArrayList<>();
            for (Question q : gr.qlist) {
                if (q.getCategory().equalsIgnoreCase(gr.category)) {
                    activeQuestions.add(q);
                    System.out.println(q.toString());
                }
            }
            toGUI.put("QUESTION");
            toGUI.put(activeQuestions);
            toGUI.put(gr.roundnumber);

            toClient.take();

            ArrayList<String>answerPC = PointCount.getAnswers();
            //ArrayList<String> result = (ArrayList<String>) toClient.take();
            for (String answer : answerPC) {
                if (isPlayer1) {
                    gr.player1Results.add(answer);
                } else {
                    gr.player2Results.add(answer);
                }
            }


            //should playerScore hold round total or score per question ?
//HODEI TEST, I TOOK THE ROUND TOTAL VERSION FOR NOW, WILL FIX WHEN WE ADD FANCY RECTANGLES.
            ArrayList<Integer> pointHolder = PointCount.getPointHolder();

         /*   for (int point : pointHolder){
                if (isPlayer1){
                    gr.player1Score.add(point);
                } else{
                    gr.player2Score.add(point);
                }
            }

          */



            //roundTotal version
            if(isPlayer1){
                gr.player1Score.add(PointCount.getRoundTotal());
            } else {
                gr.player2Score.add(PointCount.getRoundTotal());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void showResults(){
//        try{
//            toGUI.put("RESULTS");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
public void showResults(){
    try{

        //i hope its here we are supposed to go to the result scene
        int roundTotal = PointCount.playerRoundTotal(); //the round total

        PointCount.testPointCount();
        System.out.println("Reached showResult");


        toGUI.put("RESULTS");
        toGUI.put(gr); //HODEI TEST!!!

    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

    public void goToWaiting(String message){
        try{
            toGUI.put("WAITING");
            toGUI.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
