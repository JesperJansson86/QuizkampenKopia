package Client;

import MainClasses.GameRound;
import MainClasses.Question;
import Server.Server;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements Runnable {
    static GameRound gr;
    boolean player1;
    public static BlockingQueue<Object> toGUI = new LinkedBlockingQueue();
    public static BlockingQueue<Object> toClient = new LinkedBlockingQueue();

    public Client() {
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", Server.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scan = new Scanner(System.in);
            String test = null;

            System.out.println("in Client, just before taking name");
            String name = (String) toClient.take();
            while (true) {

                System.out.println("in Client, just before add name");
                gr = (GameRound) in.readObject(); //add names if needed, ergo, first round.
                if (gr.playerNames.size() < 1 || gr.roundnumber == 1) {
                    player1 = true;
                    gr.playerNames.add(name);
                } else if (gr.roundnumber == 1) {
                    gr.playerNames.add(name);
                }

                System.out.println("in Client gr.category = " + gr.category);

                if (gr.category == null) { //Choose category and answer questions
                    System.out.println("in Client: gr.category is null.");
                    getCategory();
                    System.out.println("in Client: gr.category is now: " + gr.category);
                    answerQuestions();
                    out.writeObject(gr);
                    showResults();
                    //send answers to Server and await response.
//                    if (gr.gameover && gr.playerTurn % 2 == 1) break;
                } else if (gr.category != null) {
                    System.out.println("in Client: gr.category is " + gr.category);
                    answerQuestions();
                    getCategory();
                    answerQuestions();
                    out.writeObject(gr);
                    showResults();
                }


//                gr = runda(gr);
                if (gr.playerTurn % 2 == 1) System.out.println(gr.player1Results);
                else System.out.println(gr.player2Results);
//                out.writeObject(gr);
//                gr = gr;
                if (gr.gameover && gr.playerTurn % 2 == 0) break;
            }
            if (gr.playerTurn % 2 == 1) System.out.println(gr.player1Results);
            else System.out.println(gr.player2Results);

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
            Thread.sleep(50);
            System.out.println("GetCategory() toGUI.put categoryList");
            toGUI.put(gr.categoryList);

            System.out.println("GetCategory() toClient.take String");
            gr.category = (String) toClient.take();
            System.out.println("GetCategory() gr.category taken, now :" + gr.category);
            gr.categoryList.remove(gr.category);
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
                }
            }
            toGUI.put("QUESTION");
            toGUI.put(activeQuestions);
            toGUI.put(gr.roundnumber);
            ArrayList<String> result = (ArrayList<String>) toClient.take();
            for (String answer : result) {
                if (player1) {
                    gr.player1Results.add(answer);
                } else {
                    gr.player2Results.add(answer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showResults(){
        try{
            toGUI.put("RESULTS");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static GameRound runda(GameRound gr) {
        int count = 0;
        while (!gr.qlist.get(count).getCategory().equals(gr.category)) {
            count++;
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Kategori " + gr.category);
        System.out.println("GameId " + gr.gameid);
        for (int i = 1; i <= 1; i++) {
            System.out.println("Svara på fråga: " + gr.qlist.get(i + count).getQuestion());
            System.out.println(gr.qlist.get(i + count).getRightAnswer());
            System.out.println(gr.qlist.get(i + count).getAnswer2());
            System.out.println(gr.qlist.get(i + count).getAnswer3());
            System.out.println(gr.qlist.get(i + count).getAnswer4());
            int choice = Integer.parseInt(scan.next());
            String answer;
            switch (choice) {
                case 1: {
                    answer = gr.qlist.get(i + count).getRightAnswer();
                    break;
                }
                case 2: {
                    answer = gr.qlist.get(i + count).getAnswer2();
                    break;
                }
                case 3: {
                    answer = gr.qlist.get(i + count).getAnswer3();
                    break;
                }
                case 4: {
                    answer = gr.qlist.get(i + count).getRightAnswer();
                    break;
                }

                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

            if (gr.playerTurn % 2 == 1) gr.player1Results.add(answer);
            else gr.player2Results.add(answer);
        }
        return gr;
    }
//    public void grading(MainClasses.GameRound gr){
//        for (int i = 0; i < gr.player1Results.size(); i++) {
//        if (gr.player1Results.get(i).equals())
//        }
//    }
}
