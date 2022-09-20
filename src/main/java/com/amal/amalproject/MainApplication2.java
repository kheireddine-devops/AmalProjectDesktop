package com.amal.amalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication2 extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root=FXMLLoader.load(getClass().getResource("ListEmploi.fxml"));
        
        Scene scene = new Scene(root,800,600);
       ;
        
        primaryStage.setTitle("Ajout offre");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   public static void main(String[] args) {
        launch(args);
    }
}