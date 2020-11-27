package Client;

import MainClasses.GameRound;
import MainClasses.PointCount;
import MainClasses.Question;
import Server.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Client-class is communicating between GameRound Data-class, TCP-server and GUI.
 *
 */
public class Client implements Runnable {
    public static GameRound gr;
    public static boolean isPlayer1;
    public static BlockingQueue<Object> toGUI = new LinkedBlockingQueue();
    public static BlockingQueue<Object> toClient = new LinkedBlockingQueue();

    public Client() {}

    @Override
    public void run() {

        String name = null;
        try {
            name = (String) toClient.take(); //Waiting for GUI to send name to Client
            goToWaiting("Waiting for opponent");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (Socket socket = new Socket("localhost", StartupGameServer.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                gr = (GameRound) in.readObject();
                if (gr.gameover) {
                    goToEndResults();
                    break;
                }
                if (gr.playerNames.isEmpty()) {
                    gr.playerNames.add("Unknown");
                    gr.playerNames.add("Unknown");
                }
                if (gr.playerNames.get(0).equalsIgnoreCase("Unknown")) {
                    isPlayer1 = true;
                    gr.playerNames.add(0, name);
                    gr.playerNames.remove("Unknown");
                } else if (gr.playerNames.get(1).equalsIgnoreCase("Unknown")) {
                    isPlayer1 = false;
                    gr.playerNames.add(1, name);
                    gr.playerNames.remove("Unknown");
                }

                if (gr.category == null) {
                    goToChooseCategory();
                    goToQuestionPanel();
                    goToResultsAndReview();
                } else if (gr.category != null) {
                    goToQuestionPanel();
                    goToResultsAndReview();
                }
                out.writeObject(gr);
                goToWaiting("End of Round, waiting for opponent/server");
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends GUI to ChooseCategory-view. <br>
     * Set category in GameRound after GUI has completed it's task. <br>
     * Removes category from list so it can't be chosen again.
     * @throws InterruptedException
     */
    private void goToChooseCategory() throws InterruptedException{
            toGUI.put("CATEGORY");
            gr.category = (String) toClient.take();  //Wait for GUI to select category
            gr.categoryList.remove(gr.category);
    }

    /**
     * Sets activeQuestions in GameRound to correct questions for curren category. <br>
     * Sends GUI to QuestionPanel-view. <br>
     * Puts awnsers in GameRound when GUI has completed it's task.
     * @throws InterruptedException
     */
    private void goToQuestionPanel() throws InterruptedException {
            gr.activeQuestions.clear();
            for (Question q : gr.qlist) {  //Make list of questions to awnser this round.
                if (q.getCategory().equalsIgnoreCase(gr.category)) {
                    gr.activeQuestions.add(q);
                }
            }
            toGUI.put("QUESTION");
            toClient.take();
            for (String answer : PointCount.getAnswers()) { //Add answers to players answers(Results)-list
                if (isPlayer1) {
                    gr.player1Results.add(answer);
                } else {
                    gr.player2Results.add(answer);
                }
            }

            if (isPlayer1) { //Add round score to players score-list.
                gr.player1Score.add(PointCount.getRoundTotal());
            } else {
                gr.player2Score.add(PointCount.getRoundTotal());
            }
            PointCount.reset();
    }

    /**
     * Sends GUI to ResultsAndReview-view
     * @throws InterruptedException
     */
    private void goToResultsAndReview() throws InterruptedException {
            toGUI.put("RESULTS");
            toGUI.put(gr);
    }

    /**
     * Sends GUI to Waiting-panel with argumented message.
     * @param message
     * @throws InterruptedException
     */
    private void goToWaiting(String message) throws InterruptedException {
            toGUI.put("WAITING");
            toGUI.put(message);
    }

    /**
     * Sends GUI to EndResults-view.
     * @throws InterruptedException
     */
    private void goToEndResults() throws InterruptedException {
            toGUI.put("END_RESULTS");
    }
}