import Client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {


        @Override
        public void start(Stage primaryStage) throws Exception{ ;
            Thread t = new Thread(new Client());
            Parent root = FXMLLoader.load(getClass().getResource("GUI/view/Intro.fxml"));
            primaryStage.setTitle("FakeQuizDuel");
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("GUI/resources/logo.png"));
            primaryStage.show();
            t.start();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }


