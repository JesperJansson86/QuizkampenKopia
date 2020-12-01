import client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {


        @Override
        public void start(Stage primaryStage) throws Exception{ ;
            Thread t = new Thread(new Client());
            Parent root = FXMLLoader.load(getClass().getResource("gui/view/Intro.fxml"));
            primaryStage.setTitle("FakeQuizDuel");
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("gui/resources/logo.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
            t.start();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        }


        public static void main(String[] args) {
            launch(args);
        }
    }


