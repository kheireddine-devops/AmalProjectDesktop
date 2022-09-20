package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

public class AddFormationController implements Initializable {
	
	  Connection connection = DBConnection.getConnection();
	
	

	    Stage stage;

		@FXML
		private AnchorPane dateformation;
		
		@FXML
		private TableView<Formation> formationTableView;
		
		@FXML
		private TableColumn<Formation ,Integer> id_col;
		
		@FXML
		private TableColumn<Formation, LocalDate> date_col;
		
		@FXML
		private TableColumn<Formation, String> theme_col;
		
		@FXML
		private TableColumn<Formation,String> detail_col;
		
		@FXML
		private TextField theme;
		
		@FXML
		private TextArea descriptif;
		
		@FXML
		private DatePicker Date_publication;

	    @FXML
	    private void btnClose_clicked(MouseEvent e) {
	        stage = (Stage) ((ImageView) e.getSource()).getScene().getWindow();
	        stage.close();
	    }

	    @Override
	    public void initialize(URL url, ResourceBundle resourceBundle)   {
	        try {
	       	listerFormation();
	        }catch (SQLException q){

	        }
	    }

		public ObservableList<com.amal.amalproject.entities.Formation> getAllFormation() throws SQLException {
			
			
			Connection connection = DBConnection.getConnection();
	        ObservableList<Formation> formations = FXCollections.observableArrayList();

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("select * from formation ");
	        while(resultSet.next()){
	        	formations.addAll(new Formation(
	                    resultSet.getInt("id_formation"),
	                    resultSet.getString("theme"),
	                    resultSet.getString("descriptif")
	                   // resultSet.getDate("Date_publication").toLocalDate() 
	            ));
	        }
	        return formations;
	    
		
	}
	    public void listerFormation() throws SQLException{
	        id_col.setCellValueFactory(new PropertyValueFactory("id-formation"));
	        theme_col.setCellValueFactory(new PropertyValueFactory("theme"));
	        detail_col.setCellValueFactory(new PropertyValueFactory("descriptif"));
	       date_col.setCellValueFactory(new PropertyValueFactory("Date_publication"));
	        formationTableView.setItems(getAllFormation());
	    }
	    
	    
	    
	    //supprimer formation
	    @FXML
	    private void supprimerFormation(){
	        Formation formation = (Formation) formationTableView.getSelectionModel().getSelectedItem();
	        if (formation == null) {
	            System.out.println("aucune formation a supprimer !");
	            return;
	        }
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Supression d'une formation !");
	        alert.setContentText("Etes vous totalement sur de vouloir supprimer une formation <" + formation.getId_formation() + "-" + formation.getTheme() +"> ??\n");
	        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
	        alert.setHeight(400);
	        Optional<ButtonType> reponse = alert.showAndWait();
	        if (reponse.get().equals(ButtonType.OK)) {
	            try {
	            	 Connection connection = DBConnection.getConnection();
	                Statement sqlCommand = connection.createStatement();
	                sqlCommand.execute
	                        (
	                                String.format
	                                        (
	                                                "delete from formation where id_formation = '%s' ;" ,
	                                                       
	                                                        formation.getId_formation(), formation.getTheme()
	                                        )
	                        );
	                formationTableView.getItems().remove(formationTableView.getSelectionModel().getSelectedItem());
	                formationTableView.refresh();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    @FXML
	    public void btnAjouter_clicked(ActionEvent e) {
	    	 Connection connection = DBConnection.getConnection();
	        try {
	            Statement sqlCommand = connection.createStatement();
	            sqlCommand.execute(
	                    String.format
	                            (
	                            		"INSERT INTO formation("
	            	            				+ "id_formation,theme,descriptif,Date_publication) VALUES (?,?,?,?)"
	                              
	                            )
	            );
	            theme.setText("");
	          //  Date_publication.setValue(null);
	            descriptif.setText("");
	            formationTableView.refresh();

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

	
	
	
	

