import Client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {


        @Override
        public void start(Stage primaryStage) throws Exception{
          // Client c=new Client();
           // Thread th=new Thread(c);

            Parent root = FXMLLoader.load(getClass().getResource("GUI/view/Intro.fxml"));
           // Parent root = FXMLLoader.load(getClass().getResource("GUI/view/QuestionsPanel.fxml"));
            primaryStage.setTitle("FakeQuizDuel");
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("GUI/resources/logo.png"));
            primaryStage.show();
          //  Platform.runLater(c);
        }


        public static void main(String[] args) {
            launch(args);
        }




}


