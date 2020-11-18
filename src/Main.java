import Client.Client;
import GUI.models.GUIutils;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
//GUIutils util;

        @Override
        public void start(Stage primaryStage) throws Exception{

            Parent root = FXMLLoader.load(getClass().getResource("GUI/view/Intro.fxml"));

           // Parent root = FXMLLoader.load(getClass().getResource("GUI/view/QuestionsPanel.fxml"));
            primaryStage.setTitle("FakeQuizDuel");
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("GUI/resources/logo.png"));
            primaryStage.show();
/*
            Task<Void> task = new Task<>() {
                @Override
                public Void call() {
                    Client c = new Client(util,primaryStage);
                    c.run();
                    return null ;
                }
            };
            new Thread(task).start();

 */
        }


        public static void main(String[] args) {
            launch(args);
        }




}


