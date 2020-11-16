package GUI.controllers;

import GUI.models.GUIutils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 20:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ChooseCategory {
    public Label categoryL1;
    public Arc category1;
    public Arc category3;
    public Label categoryL3;
    public Arc category2;
    public Label categoryL2;
    public Group categoriesCircle;
    public AnchorPane mainPane;
    GUIutils util;
    public void initialize(){
categoriesCircle.setOnMousePressed(e->goNextPanel());
                util =new GUIutils(mainPane);
    }
  public void goNextPanel(){
      try {
          util.changeScene("../view/QuestionsPanel.fxml");
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}
