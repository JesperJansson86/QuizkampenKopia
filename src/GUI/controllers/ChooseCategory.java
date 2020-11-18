package GUI.controllers;

import Client.Client;
import GUI.models.GUIutils;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hodei Eceiza
 * Date: 11/13/2020
 * Time: 20:02
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class ChooseCategory {
    BlockingQueue toGUI = Client.toGUI;
    BlockingQueue toClient = Client.toClient;
    List<String> allCategories = new ArrayList<>();

    public Label categoryL1;
    public Arc category1;
    public Arc category3;
    public Label categoryL3;
    public Arc category2;
    public Label categoryL2;
    public Group categoriesCircle;
    public AnchorPane mainPane;
    public Group category1group;
    public Group category2group;
    public Group category3group;
    GUIutils util;
    public void initialize() throws InterruptedException {

        allCategories = (ArrayList<String>) toGUI.take(); // Get categories from BQ

        Random rand = new Random();


        List<String> categories = new ArrayList<>();
        for (int i = 0; i < 3; i++) { //Randomize categories
            int temp = rand.nextInt(allCategories.size());
            categories.add(allCategories.get(temp));
            allCategories.remove(temp);
        }

        categoryL1.setText(categories.get(0));
        categoryL2.setText(categories.get(1));
        categoryL3.setText(categories.get(2));

//category1group.setOnMousePressed(e-> {System.out.println("category1");goNextPanel();});
//category2group.setOnMousePressed(e->{System.out.println("category2");goNextPanel();});
//category3group.setOnMousePressed(e->{System.out.println("category3");goNextPanel();});

                util =new GUIutils(mainPane);
    }
  public void goNextPanel(){
      try {
          String nextPane = (String) toGUI.take();
          util.changeScene(nextPane);
      } catch (IOException | InterruptedException e) {
          e.printStackTrace();
      }
  }

    public void category1Pressed(MouseEvent mouseEvent) {
        categoryChosen(categoryL1.getText());
        goNextPanel();
    }

    public void category2Pressed(MouseEvent mouseEvent) {
        categoryChosen(categoryL2.getText());
        goNextPanel();
    }

    public void category3Pressed(MouseEvent mouseEvent) {
        categoryChosen(categoryL3.getText());
    }

    private void categoryChosen(String category){
        try {
            toClient.put(category);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        goNextPanel();
    }
}
