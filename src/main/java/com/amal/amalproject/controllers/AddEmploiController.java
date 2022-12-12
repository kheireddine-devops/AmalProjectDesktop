package com.amal.amalproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.entities.User;
import com.amal.amalproject.models.EmploiModel;
import com.amal.amalproject.utils.Navigate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddEmploiController implements Initializable  {
	private EmploiModel em =new EmploiModel();
	
	@FXML
	private AnchorPane Apid;

	@FXML
	private Button btnok;

	@FXML
	private TextField txtref;

	@FXML
	private TextField txttitre;

	@FXML
	private TextField txtsecteur;

	@FXML
	private DatePicker dateExp;

	@FXML
	private TextArea txtdesc;

	@FXML
	private Button btnretour;

	@FXML
	void OnRetour(ActionEvent event) throws IOException {

//		Navigate.changerScene(event, "ListEmploi.fxml","List des offres");
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		
	}

	@FXML
	void onClick(ActionEvent event) throws IOException {
		if (isInputValid()){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd"); 
			String titre =txttitre.getText();
			String secteur=txtsecteur.getText();
			String ref =txtref.getText();
			Date dateexp= java.sql.Date.valueOf(dateExp.getValue());
			String desc =txtdesc.getText();

			Emploi e =new Emploi(titre,desc,secteur,ref,dateexp);
			em.add(e);
			
		
//			Navigate.changerScene(event, "ListEmploi.fxml", "Lise des offres");
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();

		     
			/*
			txttitre.clear();
			txtsecteur.clear();
			txtref.clear();
			txtdesc.clear();
			dateExp.getEditor().clear();*/

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}


	public boolean isInputValid(){

		String errorMessage = "";

		if (txtref.getText() == null || txtref.getText().length() == 0){
			errorMessage += "Référence invalide!\n";
		}

		if (dateExp.getValue() == null){
			errorMessage += "Date invalide!\n";
		}

		if (txttitre.getText() == null || txttitre.getText().length() == 0){
			errorMessage += "Titre invalid!\n";
		}
		if (txtdesc.getText() == null || txtdesc.getText().length() == 0){
			errorMessage += "Description invalide!\n";
		}
		if (txtsecteur.getText() == null || txtsecteur.getText().length() == 0){
			errorMessage += "Secteur invalide!\n";
		}


		if (errorMessage.length() == 0){
			return true;
		}else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Veuillez corriger les champs invalide");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}

	}


}
