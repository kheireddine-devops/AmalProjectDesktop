package com.amal.amalproject;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFormationTutoriel extends Application {
	  private Stage stage;

	    public void start(Stage stage) throws IOException {
	        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Gestiontutoriel.fxml"));
	        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
	        stage.setTitle("Plateform AMAL ");
	        stage.setScene(scene);
	        //String css = this.getClass().getResource("style.css").toExternalForm();
	        //scene.getStylesheets().add(css);
	        stage.show();

}
	    

	    public static void main(String[] args) {
	        launch();
	    }


}
