import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        try (Socket socket = new Socket("localhost", Server.port)){
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            QuestionFactory questionsFromServer;

            while ((questionsFromServer = (QuestionFactory)in.readObject()) != null) {

                System.out.println(questionsFromServer.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
