package GUI.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Hodei Eceiza
 * Date: 11/14/2020
 * Time: 22:56
 * Project: QuizkampenKopia
 * Copyright: MIT
 */


public class GUIutils {
    Pane mainPane;
    public GUIutils(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Stage primaryStage = (Stage) mainPane.getScene().getWindow();
        primaryStage.getScene().setRoot(pane);
    }
    public void changeSceneNew(String nextPane) throws IOException {
        String fxml = nextPane(nextPane);
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Stage primaryStage = (Stage) mainPane.getScene().getWindow();
        primaryStage.getScene().setRoot(pane);
    }

    public String nextPane(String nextPane) {
        if (nextPane.equalsIgnoreCase("CATEGORY")) {
            System.out.println("In GUIutils: It's time for Category!");
            return "../view/ChooseCategory.fxml";
        } else if (nextPane.equalsIgnoreCase("QUESTION")) {
            System.out.println("In GUIutils: It's time for Question!");
            return "../view/QuestionsPanel.fxml";
        } else if (nextPane.equalsIgnoreCase("RESULTS")){
            System.out.println("In GUIutils: It's time for Results!");
            return "../view/ResultsAndReview.fxml";
        } else if(nextPane.equalsIgnoreCase("WAITING")){
            System.out.println("In GUIutils: It's time for Waiting!");
            return "../view/Waiting.fxml";
        } else if(nextPane.equalsIgnoreCase("END_RESULTS")){
            System.out.println("In GUIutils: It's time for End Results!");
            return "../view/EndResults.fxml";
        } else {
            System.out.println("In GUIutils: It's null! WADUHEK!?!");
            return null;
        }
    }

}
