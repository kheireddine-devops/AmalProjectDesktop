package com.amal.amalproject.controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.amal.amalproject.entities.Candidature;
import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.models.CandidatureModel;
import com.amal.amalproject.models.EmploiModel;
import com.amal.amalproject.utils.Navigate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListCandidaturesController implements Initializable{
	@FXML
    private Button btnmodif;

    @FXML
    private Button btnsup;
  

    @FXML
    private Button btnannuler;

    @FXML
    private TableView<Candidature> table;

    @FXML
    private TableColumn<?, String> col_ref;

    @FXML
    private TableColumn<Candidature, String> col_cv;
    @FXML
    private TableColumn<Candidature, String> col_niv;
    @FXML
    private TableColumn<Candidature, Date> col_date;
    @FXML
    private TableColumn<Candidature, String> col_message;
    
    private List<Candidature> listCandidature = new ArrayList();
  	private CandidatureModel candModel = new CandidatureModel();
    @FXML
    void OnSupprimer(ActionEvent event) {
    	if (table.getSelectionModel().getSelectedIndex() > -1) {

			Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnYes = new ButtonType("Yes");
			ButtonType btnNoAnswer = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

			dialogoExe.setTitle("Attention!");
			dialogoExe.setHeaderText("Inform if you want to delete");
			dialogoExe.setContentText("Delete " + table.getSelectionModel().getSelectedItem().getId_emploi()+ "?");
			dialogoExe.getButtonTypes().setAll(btnYes, btnNoAnswer);

			dialogoExe.showAndWait().ifPresent(b -> {
				if (b == btnYes) {
					candModel.delete(table.getSelectionModel().getSelectedItem());
					//loadEmploi(true);
					System.out.println("Votre candidature est annulé");
				}
			});

		} else {
			if (listCandidature.isEmpty()) {
				alert("Error", null, "There are no records to delete", Alert.AlertType.ERROR);
			} else {
				alert("Error", "Select a record", "To delete you need to select a record from the table",
						Alert.AlertType.ERROR);
			}
		}
    	
    	Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    }
    private void alert(String titulo, String headerText, String contentText, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    @FXML
    void OnAnnuler(ActionEvent event) throws IOException {
//    	Navigate.changerScene(event, "ListOffres.fxml", "Liste des offres");
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    }

    @FXML
    void OnModifier(ActionEvent event) {

    }
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			ArrayList<Candidature> list = candModel.readAll();
			this.remplirTab(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void remplirTab(ArrayList<Candidature> list) throws SQLException {

		ObservableList<Candidature> obs = FXCollections.observableArrayList(list);
		definingColumn();

		table.setItems((ObservableList<Candidature>) obs);
	}
    public void definingColumn(){
    	col_ref.setCellValueFactory(new PropertyValueFactory<>("id_emploi"));
		col_niv.setCellValueFactory(new PropertyValueFactory<>("niveau"));
		col_cv.setCellValueFactory(new PropertyValueFactory<>("url_cv"));
		col_date.setCellValueFactory(new PropertyValueFactory<>("date_postule"));
		col_message.setCellValueFactory(new PropertyValueFactory<>("message"));
    }

}
