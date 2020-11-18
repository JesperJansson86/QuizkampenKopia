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

    public void changeScene(String fxml) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));

            Stage primaryStage = (Stage) mainPane.getScene().getWindow();
            primaryStage.getScene().setRoot(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
