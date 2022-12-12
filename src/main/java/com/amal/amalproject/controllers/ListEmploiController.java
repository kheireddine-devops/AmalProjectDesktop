package com.amal.amalproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.models.EmploiModel;
import com.amal.amalproject.utils.DBConnection;
import com.amal.amalproject.utils.Navigate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;

public class ListEmploiController implements Initializable {
	@FXML
	private Button btnconsulter;
	@FXML
	private Button btnajout;

	@FXML
	private Button bntmodif;

	@FXML
	private Button btnsup;

    @FXML
    private Button btncandidature;
    @FXML
    private Button btnrefresh;


	@FXML
	private TableView<Emploi> table;
	@FXML
	private TableColumn<Emploi, String> col_titre;

	@FXML
	private TableColumn<Emploi, String> col_sec;

	@FXML
	private TableColumn<Emploi, String> col_desc;

	@FXML
	private TableColumn<Emploi, String> col_ref;

	@FXML
	private TableColumn<Emploi, Date> col_date;
	@FXML
	private TextField txtrech;

	@FXML
	private Button btnrech;
	@FXML
	private ComboBox combrech;
	
	private List<Emploi> listEmploi = new ArrayList();
	EmploiModel empModel = new EmploiModel();
	@FXML
	void OnModifier(ActionEvent event) throws IOException {
		  FXMLLoader loader= Navigate.changerScene(event, "UpdateEmploi.fxml", "Modifier une offre");
		//envoi des donnés ente les scénes
		    UpdateEmploiController controller=loader.getController();
		    controller.setFields(table.getSelectionModel().getSelectedItem());
					
	}

	@FXML
	void OnRecherche(ActionEvent event) throws ParseException {
		try{
            if (combrech.getValue().equals("Show all")){
                loadEmploi(true);
                txtrech.clear();
            }else{
               
                List<Emploi> emploi = new ArrayList();
                switch ((String)combrech.getSelectionModel().getSelectedItem()) {
                    case "ID":
                        emploi.add(empModel.readById(Integer.parseInt(txtrech.getText())));
                        break;
                       
                    case "Titre":
                        emploi = empModel.readByTitre(txtrech.getText());
                        break; 
                        
                    case "Secteur":
                        emploi = empModel.readBySecteur(txtrech.getText()); 
                        break;
                    case "Reference":
                        emploi = empModel.readByReference(txtrech.getText()); 
                        break;
                    case "Date":
                    	
                    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
                    	java.util.Date date = format.parse(txtrech.getText());
                    	java.sql.Date sqlDate =  new java.sql.Date(date.getTime());
                    	emploi = empModel.readByDate(sqlDate); 
                        break;
                    
                    default:
                        break;
                }
                
               loadEmploi(emploi);
            }
        }catch(NumberFormatException ime){
            System.out.println("Enter the valid value type");
        }catch(NullPointerException npe){
        	System.out.println("Enter some value");
        } 
	}

	@FXML
	void OnSupprimer(ActionEvent event) {
		if (table.getSelectionModel().getSelectedIndex() > -1) {

			Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnYes = new ButtonType("Yes");
			ButtonType btnNoAnswer = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

			dialogoExe.setTitle("Attention!");
			dialogoExe.setHeaderText("Inform if you want to delete");
			dialogoExe.setContentText("Delete " + table.getSelectionModel().getSelectedItem().getRef_emploi() + "?");
			dialogoExe.getButtonTypes().setAll(btnYes, btnNoAnswer);

			dialogoExe.showAndWait().ifPresent(b -> {
				if (b == btnYes) {
					empModel.delete(table.getSelectionModel().getSelectedItem());
					loadEmploi(true);
				}
			});

		} else {
			if (listEmploi.isEmpty()) {
				alert("Error", null, "There are no records to delete", Alert.AlertType.ERROR);
			} else {
				alert("Error", "Select a record", "To delete you need to select a record from the table",
						Alert.AlertType.ERROR);
			}
		}
	}

	@FXML
	void OnAjouter(ActionEvent event) throws IOException {
		
		Navigate.changerScene(event, "AddEmploi.fxml", "Ajouter une offre d'emlpoi");

	}

	@FXML
	void selectChoix(ActionEvent event) {
		String choix = combrech.getSelectionModel().getSelectedItem().toString();
		// txtrech.setText(choix);
	}
	//*******************************************************
	  @FXML
	    void OnCandidatures(ActionEvent event) throws IOException {
		  Navigate.changerScene(event, "ListCandidatures.fxml", "Mes candidatures");

	    }
	  @FXML
	    void onRefresh(ActionEvent event)throws IOException {
		  
		  loadEmploi(true);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<String> listrech = FXCollections.observableArrayList("Show all","ID", "Reference", "Titre", "Secteur",
				"Date");
		combrech.setItems(listrech);

		try {
			ArrayList<Emploi> list = empModel.readAll();
			this.remplirTab(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remplirTab(ArrayList<Emploi> list) throws SQLException {

		ObservableList<Emploi> obs = FXCollections.observableArrayList(list);
		definingColumn();

		table.setItems(obs);
	}
	
	
	 private void alert(String titulo, String headerText, String contentText, Alert.AlertType type){
	        Alert alert = new Alert(type);
	        alert.setTitle(titulo);
	        alert.setHeaderText(headerText);
	        alert.setContentText(contentText);
	        alert.showAndWait();
	    }
	 public boolean loadEmploi(boolean cleanTable){
	        
	        try{

	            if (cleanTable){
	                cleanTable();
	            }
	            
	            definingColumn();
	        
	            setListEmploi(empModel.readAll());

	            ObservableList<Emploi> obs = FXCollections.observableArrayList(listEmploi);
	            table.setItems(obs);
	            
	        }catch(Exception e){
	            alert("Error", null, "An error occurred while retrieving data", Alert.AlertType.ERROR);
	            return false;
	        }
	        
	        return true;
	    }
	 private void cleanTable(){
	        table.getItems().clear();
	    }
	 public void definingColumn(){
		   // col_id.setCellValueFactory(new PropertyValueFactory<>("id_emploi"));
			col_titre.setCellValueFactory(new PropertyValueFactory<>("titre_emploi"));
			col_sec.setCellValueFactory(new PropertyValueFactory<>("secteur"));
			col_desc.setCellValueFactory(new PropertyValueFactory<>("descriptif_emploi"));
			col_ref.setCellValueFactory(new PropertyValueFactory<>("ref_emploi"));
			col_date.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
	    }
	 public void setListEmploi(List<Emploi> listEmploi) {
	        this.listEmploi= listEmploi;
	    }
	 public void loadEmploi(List<Emploi> arrayListEmploi){
         try{
            cleanTable();
            ObservableList<Emploi> observableListEmploi = FXCollections.observableArrayList(arrayListEmploi);
            table.setItems(observableListEmploi);
        }catch(Exception e){
            alert("Error", null, "An error occurred while retrieving data", Alert.AlertType.ERROR);
        }
    }
}
