package com.amal.amalproject.controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.models.EmploiModel;
import com.amal.amalproject.utils.Navigate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateEmploiController {

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
	EmploiModel empModel = new EmploiModel();
	private Emploi emp ;
	@FXML
	void onModifier(ActionEvent event) throws IOException {
		if (isInputValid()){	

			emp.setRef_emploi(txtref.getText());
			emp.setTitre_emploi(txttitre.getText());
			emp.setSecteur(txtsecteur.getText());
			emp.setDescriptif_emploi(txtdesc.getText());
			emp.setDate_expiration(java.sql.Date.valueOf(dateExp.getValue()));
			empModel.update(emp);
//			Navigate.changerScene(event, "ListEmploi.fxml", "Lise des offres");
			Node source = (Node) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		}
	}

	public void setFields(Emploi e) {
		emp=e; 
		txtref.setText(e.getRef_emploi());
		txttitre.setText(e.getTitre_emploi());
		txtsecteur.setText(e.getSecteur());
		txtdesc.setText(e.getDescriptif_emploi());


		LocalDate dateExpLD=e.getDate_expiration().toLocalDate();
		dateExp.setValue(dateExpLD);


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


