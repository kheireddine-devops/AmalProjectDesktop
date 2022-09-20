package com.amal.amalproject.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.models.EmploiModel;
import com.amal.amalproject.utils.Navigate;
import com.amal.amalproject.utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ListOffresController extends SharedController implements Initializable {

    @FXML
    private Button btnpostuler;

    @FXML
    private Button btnannuler;

    @FXML
    private TableView<Emploi> table;

    @FXML
    private TableColumn<Emploi, String>col_titre;

    @FXML
    private TableColumn<Emploi, String>col_sec;

    @FXML
    private TableColumn<Emploi, String>col_desc;

    @FXML
    private TableColumn<Emploi, String>col_ref;

    @FXML
    private TableColumn<Emploi, Date>col_date;

    @FXML
    private ComboBox<String> combrech;

    @FXML
    private TextField txtrech;

    @FXML
    private Button btnrech;

    @FXML
    private Button btncandidature;
    private List<Emploi> listEmploi = new ArrayList();
	EmploiModel empModel = new EmploiModel();
    @Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<String> listrech = FXCollections.observableArrayList("Show all", "Reference", "Titre", "Secteur","Date");
		combrech.setItems(listrech);
		 

		try {
			ArrayList<Emploi> list = empModel.readAllOffres();
		
			this.remplirTab(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void remplirTab(ArrayList<Emploi> list) throws SQLException {

		ObservableList<Emploi> obs = FXCollections.observableArrayList(list);
		definingColumn();

		table.setItems((ObservableList<Emploi>) obs);
	}
    public void definingColumn(){
	   
		col_titre.setCellValueFactory(new PropertyValueFactory<>("titre_emploi"));
		col_sec.setCellValueFactory(new PropertyValueFactory<>("secteur"));
		col_desc.setCellValueFactory(new PropertyValueFactory<>("descriptif_emploi"));
		col_ref.setCellValueFactory(new PropertyValueFactory<>("ref_emploi"));
		col_date.setCellValueFactory(new PropertyValueFactory<>("date_expiration"));
    }
    @FXML
    void OnAnnuler(ActionEvent event) throws IOException {
//    	Navigate.changerScene(event, "Candidature.fxml", "Liste des offres");

    }

    @FXML
    void OnCandidatures(ActionEvent event) throws IOException {
    	Navigate.changerScene(event, "ListCandidatures.fxml", "Mes candidatures");
    }

    @FXML
    void OnPostuler(ActionEvent event) throws IOException {


    	 FXMLLoader loader= Navigate.changerScene(event, "Candidature.fxml", "Liste des offres");
 		//envoi des donnés ente les scénes
 		  CandidatureController controller=loader.getController();
 		  controller.getFields(table.getSelectionModel().getSelectedItem());



//        FXMLLoader loader= MainApplication.loadSubFXML("Candidature");
//        CandidatureController controller=loader.getController();
//        controller.getFields(table.getSelectionModel().getSelectedItem());

    	
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
    
    public void loadEmploi(List<Emploi> arrayListEmploi){
        try{
           cleanTable();
           ObservableList<Emploi> observableListEmploi = FXCollections.observableArrayList(arrayListEmploi);
           table.setItems(observableListEmploi);
       }catch(Exception e){
           alert("Error", null, "An error occurred while retrieving data", Alert.AlertType.ERROR);
       }
   }
    public boolean loadEmploi(boolean cleanTable){
        
        try{

            if (cleanTable){
                cleanTable();
            }
            
            definingColumn();
        
            setListEmploi(empModel.readAllOffres());

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
 public void setListEmploi(List<Emploi> listEmploi) {
     this.listEmploi= listEmploi;
 }
 private void alert(String titulo, String headerText, String contentText, Alert.AlertType type){
     Alert alert = new Alert(type);
     alert.setTitle(titulo);
     alert.setHeaderText(headerText);
     alert.setContentText(contentText);
     alert.showAndWait();
 }

    @FXML
    void selectChoix(ActionEvent event) {

    }

}
