package Client;

import GUI.controllers.Intro;
import GUI.models.GUIutils;
import MainClasses.GameRound;
import Server.Server;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client implements Runnable {
    Server s;
    GameRound gr=new GameRound();
    GUIutils utils;
    Stage stage;

   // Stage primaryStage;

  //  public static void main(String[] args) {

    //    new Client.Client();
   // }

    public Client(GUIutils utils, Stage stage) {
        this.utils=utils;
        this.stage=stage;
    }

    @Override
    public void run() {

        try (Socket socket = new Socket("localhost",s.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scan = new Scanner(System.in);
            String test = null;
//            if (in.readObject()=="StartingPlayer"){
//                in.available();
            // PLACEHOLDER---
            //Metod för att välja kategori
            //Som returneras till servern med out.writeObject();
            //PLACEHOLDER---
//            }
            while (true) {
                // PLACEHOLDER---
                //Kontroll som kollar om det är din tur att välja kategori eller bara svara på frågor.
                //Antingen skickas kategorin till servern eller så har en redan fått alla frågor en kan tänkas behöva.
                //Resultatet av ens frågor skickas till motståndaren samt de resultat en fått från motståndaren visas
                //när en svarat på ens frågor för rundan
                //om spelet är slut räknas totalpoäng ihop och en vinnare koras.
                //PLACEHOLDER---

                /*
                Just nu skickas ett MainClasses.GameRound objekt och det en kan skriva strängar till skannern som sparas som MainClasses.GameRound.category
                 */
                gr = (GameRound) in.readObject();
                System.out.println(stage.getScene().getRoot().getId());
                if(stage.getScene().getRoot().getId().equals("mainPane")){

                }
                utils.changeScene("../view/ChooseCategory.fxml");
                System.out.println(gr.category);
                gr.category = scan.next();


                out.writeObject(gr);


//                test = scan.next();
//                out.writeObject(test);
//                System.out.println(in.readObject());


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
