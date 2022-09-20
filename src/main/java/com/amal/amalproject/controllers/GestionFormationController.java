package com.amal.amalproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Collation;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.entities.User;
import com.amal.amalproject.models.FormationModel;
import com.amal.amalproject.models.IFormationModel;

import javafx.scene.control.cell.PropertyValueFactory;

import com.amal.amalproject.utils.DBConnection;

import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import javafx.scene.control.TableView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import com.amal.amalproject.utils.DBConnection;

public class GestionFormationController implements Initializable {

	@FXML
	private AnchorPane dateformation;
	@FXML
	private TableView<Formation> formationTableView;
	
	@FXML
	private TableColumn<Formation, LocalDate> date_colD;
	@FXML
	private TableColumn<Formation, LocalDate> date_colF;
	@FXML
	private TableColumn<Formation, String> theme_col;
	@FXML
	private TableColumn<Formation, String> detail_col;
	@FXML
	private TableColumn<Formation, Integer> nbr_jours_col;
	@FXML
	private TableColumn<Formation, Integer> nbr_per_col;
	@FXML
	private TextField theme;
	@FXML
	private TextArea descriptif;
	@FXML
	private DatePicker Date_publication;
	@FXML
	private DatePicker Date_publicationF;
	@FXML
	private TextField nbr_jours;
	@FXML
	private TextField Nbr_personnes;
	@FXML
	private Button btnBack;
	@FXML
	private Button back;
	@FXML
	private Stage stage;
	@FXML
	private Hyperlink publicationlien;
	@FXML
	private Hyperlink tutoriel;
	FormationModel formationModel = new FormationModel();
	int ID;
	ObservableList<Formation> formations = FXCollections.observableArrayList();

	// button AJOUTER

	@FXML
	public void btnAjouter_clicked(ActionEvent event) {
		if (theme.getText().length() > 0 && descriptif.getText().length() > 0 &&nbr_jours.getText().length()>0 && 	Nbr_personnes.getText().length()>0 )
		{
			
		String FormationTheme = theme.getText();
		String FormationDescriptif = descriptif.getText();
		LocalDate dateD = Date_publication.getValue();	
		LocalDate dateF = Date_publicationF.getValue();
		String  nbrjours=nbr_jours.getText();
		String  nbrpers=Nbr_personnes.getText();
		}	else
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			
			alert.setContentText("saisie un théme pour votre formation ");
	
		}
		String FormationTheme = theme.getText();
		String FormationDescriptif = descriptif.getText();
		LocalDate dateD = Date_publication.getValue();	
		LocalDate dateF = Date_publicationF.getValue();
		String  nbrjours=nbr_jours.getText();
		String  nbrpers=Nbr_personnes.getText();
		
		formationTableView.refresh();
		System.out.print("Date_publicationF date==> " + Date_publication);
		System.out.print("Date_publicationF date==> " + Date_publicationF);
		Formation formation = new Formation( FormationTheme, FormationDescriptif, dateD,dateF,nbrjours,nbrpers);
		
		formationModel.addFormation(formation);
		System.out.println(formation);
		theme.getText();
		descriptif.getText();
		Date_publication.getValue();
		Date_publicationF.getValue();
		
		nbr_jours.getText();
		Nbr_personnes.getText();
		

	

		formationTableView.setItems(formationModel.getAllFormation());
		System.out.println("testtttttttttt 	ajout"+formationModel.getAllFormation());
		

	}

	// Event Listener on Button.onAction
	@FXML
	public void btnSupprimer_clicked(ActionEvent event) {
		Formation fr = (Formation) formationTableView.getSelectionModel().getSelectedItem();
		if (fr == null) {
			System.out.println("aucune formation a supprimer !");
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Supression d'une formation !");
		alert.setContentText("Etes vous totalement sur de vouloir supprimer une formation <" + fr.getId_formation()
				+ "-" + fr.getTheme() + "> ??\n");
		alert.getDialogPane().setMinHeight(ID);
		alert.setHeight(400);
		Optional<ButtonType> reponse = alert.showAndWait();
		if (reponse.get().equals(ButtonType.OK)) {
			try {
				Connection connection = DBConnection.getConnection();
				Statement sqlCommand = connection.createStatement();
				sqlCommand.execute(

						("Delete  FROM `formation` WHERE `id_formation` = " +

								fr.getId_formation()));

				formationTableView.getItems().remove(formationTableView.getSelectionModel().getSelectedItem());
				formationTableView.refresh();
				theme.setText("");
				descriptif.setText("");
				Date_publication.setValue(null);
				Date_publicationF.setValue(null);
				nbr_jours.setText("");
				Nbr_personnes.setText("");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// EVENT AFFICHAGE BASE AU GRAFIC
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// reference de chaque coloune avec son valeur dans la base
	
		theme_col.setCellValueFactory(new PropertyValueFactory<Formation, String>("theme"));
		detail_col.setCellValueFactory(new PropertyValueFactory<Formation, String>("descriptif"));
		date_colD.setCellValueFactory(new PropertyValueFactory<Formation, LocalDate>("Date_publication"));
		date_colF.setCellValueFactory(new PropertyValueFactory<Formation, LocalDate>("Date_publicationf"));
		nbr_jours_col.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Nbr_jours"));
		nbr_per_col.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Nbr_personnes"));


		formationTableView.setItems(formationModel.getAllFormation());

		System.out.print("*".repeat(50));
		formationModel.getAllFormation().forEach(System.out::println);
		System.out.print("*".repeat(50));

	}

	// bouton RETOUR EN arriére
	@FXML
	public void Back(Event e) {
		try {
			// add you loading or delays - ;-)
			Node node = (Node) e.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();

		} catch (Exception ex) {
			System.out.println("y" + ex.getMessage());
		}

	}
	
	
	@FXML
	public void backCLICK(ActionEvent event) {

		try {

			if (event.getSource() == back) {
				stage = (Stage) back.getScene().getWindow();

				FXMLLoader fxmlLoader = new FXMLLoader(
						MainApplication.class.getResource("ProfilFormation.fxml"));
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
	public void btnModifier_clicked(ActionEvent event) {

		Formation formation = new Formation();

		if (!theme.getText().equals("") 
				&& !descriptif.getText().equals("") 
				&& !Date_publication.getValue().equals(null) 
				&& !Date_publicationF.getValue().equals(null) 
				&& !nbr_jours.getText().equals("") 
				&& !Nbr_personnes.getText().equals("")) {

			formation.setTheme(theme.getText());
			formation.setDescriptif(descriptif.getText());
			formation.setDate_publication(Date_publication.getValue());
			formation.setDate_publicationf(Date_publicationF.getValue());
			formation.setNbr_jours(nbr_jours.getText());
			formation.setNbr_personnes(Nbr_personnes.getText());
			formation.setId_formation(ID);

			formationModel.updateFormation(formation);
			System.out.println(formation);
			formationTableView.setItems(formationModel.getAllFormation());
			
			formationTableView.refresh();

			theme.setText("");
			descriptif.setText("");
			Date_publication.setValue(null);
			Date_publicationF.setValue(null);
			nbr_jours.setText("");
			Nbr_personnes.setText("");

			System.out.println("affichage de modification 2" + formationModel.getAllFormation());

		}

	}

	// selection des lignes

	public void clickTable(Event e) {
		Formation formation = (Formation) formationTableView.getSelectionModel().getSelectedItem();
		ID = formation.getId_formation();
		theme.setText(formation.getTheme());
		descriptif.setText(formation.getDescriptif() + "");
		Date_publication.setValue(formation.getDate_publication());
		Date_publicationF.setValue(formation.getDate_publicationf());
		nbr_jours.setText(formation.getNbr_jours() + "");
		Nbr_personnes.setText(formation.getNbr_personnes() + "");

	}
	
	
	
	
	
	@FXML
	public void GererPublication(ActionEvent event) {

		try {

			if (event.getSource() == publicationlien) {
				stage = (Stage) publicationlien.getScene().getWindow();

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
						MainApplication.class.getResource("GestionTutorielFormationView.fxml"));
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


}
