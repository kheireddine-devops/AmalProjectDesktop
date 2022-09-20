package com.amal.amalproject.controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.attribute.standard.Media;
import javax.swing.Icon;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Formation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProfilFormation implements Initializable {

	@FXML
	private Button publication;
	@FXML
	private Button tutoriel;
	@FXML
	private Button close;
	@FXML
	private Button refresh;
	@FXML
	private Stage stage;

	
	@FXML
	public void onClickClose(ActionEvent event) throws IOException {

		System.exit(0);

	}
	
	
	
	// afficher linterface de publication formation

	@FXML
	public void GererPublication(ActionEvent event) {
		
		      
		try {

			if (event.getSource() == publication) {
				stage = (Stage) publication.getScene().getWindow();

				FXMLLoader fxmlLoader = new FXMLLoader(
						MainApplication.class.getResource("GestionPublicationFormation.fxml"));
				Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.UTILITY);
				stage.show();
				
			}
		} catch (IOException ex) {
			Logger.getLogger(ProfilFormation.class.getName()).log(Level.SEVERE, null, ex);

		}
		
		
	}

	@FXML
	public void GererTutoriel(ActionEvent event) throws IOException {

		try {

			if (event.getSource() == tutoriel) {
				stage = (Stage) tutoriel.getScene().getWindow();

				FXMLLoader fxmlLoader = new FXMLLoader(
						MainApplication.class.getResource("Gestiontutoriel.fxml"));
				Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.UTILITY);
				stage.show();
//				stage.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(ProfilFormation.class.getName()).log(Level.SEVERE, null, ex);

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
