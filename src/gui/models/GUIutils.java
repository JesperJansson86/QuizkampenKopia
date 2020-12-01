package gui.models;

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

    public void changeScene(String nextPane) throws IOException {
        String fxml = nextPane(nextPane);
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Stage primaryStage = (Stage) mainPane.getScene().getWindow();
        primaryStage.getScene().setRoot(pane);
    }

    public String nextPane(String nextPane) {
        switch(nextPane){
            case "CATEGORY" -> { return "../view/ChooseCategory.fxml"; }
            case "QUESTION" -> { return "../view/QuestionsPanel.fxml"; }
            case "RESULTS" -> { return "../view/ResultsAndReview.fxml"; }
            case "WAITING" -> { return "../view/Waiting.fxml"; }
            case "END_RESULTS" -> { return "../view/EndResults.fxml"; }
            default -> { return null; }
        }
    }

}
