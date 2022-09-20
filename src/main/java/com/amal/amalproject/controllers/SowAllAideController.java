package com.amal.amalproject.controllers;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import com.amal.amalproject.entities.Aide;
import com.amal.amalproject.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SowAllAideController implements Initializable {

    @FXML
    private TableColumn<Aide, Date> date;

    @FXML
    private TableColumn<Aide, String> demande;

    @FXML
    private TableColumn<Aide, String> sujet;

    @FXML
    private TableView<Aide> tableallaide;
    @FXML
    private Label idd;
    @FXML
    private Label textPublication;
    @FXML
    private Button btnAfficher;
    @FXML
    private Label idbenif;
    @FXML
    private Label nameUser;

    @FXML
    private Label prenomuser;

    ObservableList<Aide> obs = FXCollections.observableArrayList();
    int myIndex;
    int id;
    
    
    public void tableallAide() {
    	obs.clear();
    	try {
    		Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM demandeaide");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
        int idAide = resultSet.getInt("id_demande_aide");
	String contenue = resultSet.getString("contenue");
	LocalDate datePublication = resultSet.getDate("date_publication") != null ? resultSet.getDate("date_publication").toLocalDate() : null;
	String sujet = resultSet.getString("sujet");
	int idUser = resultSet.getInt("id_user");

                obs.add(new Aide(idAide, contenue, datePublication,sujet,idUser));

            }
           

        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        date.setCellValueFactory(new PropertyValueFactory<Aide, Date>("datePublication"));
        demande.setCellValueFactory(new PropertyValueFactory<Aide, String>("contenue"));
        sujet.setCellValueFactory(new PropertyValueFactory<Aide, String>("sujet"));
       
        
        tableallaide.setItems(obs);
    	tableallaide.setRowFactory( tv -> {
            TableRow<Aide> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  tableallaide.getSelectionModel().getSelectedIndex();
                  id = Integer.parseInt(String.valueOf(tableallaide.getItems().get(myIndex).getIdAide()));
                  textPublication.setText(String.valueOf(tableallaide.getItems().get(myIndex).getContenue()));
                  idd.setText(String.valueOf(tableallaide.getItems().get(myIndex).getIdAide()));
                  idbenif.setText(String.valueOf(tableallaide.getItems().get(myIndex).getIdUser()));
                  
               }
            });
               return myRow;
                          });

    }
    @FXML
    void AfficherDetail(ActionEvent event) throws IOException{
    	
    	String ida = idd.getText();
    	String contenue = textPublication.getText();
    	int iduser = Integer.parseInt(idbenif.getText());
    	if(!(ida.isEmpty())  && !(contenue.isEmpty()) ){
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/amal/amalproject/details-aide-view.fxml"));
		Parent root = (Parent) loader.load();
		DetailsAideController controller =loader.getController();
		 controller.getDemande(ida, contenue);
		 DetailsAideController.getidUser(iduser);
		 Scene scene = new Scene(root);
		 Stage stage = new Stage();
		 stage.setScene(scene);
		 stage.show();
		 }
    	else { Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Aucun Element selectionnée!");
    	alert.setHeaderText("Information");
    	alert.setContentText("Ooops!S’il vous plaît sélectionner une demande ");
    	alert.showAndWait();}
    	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableallAide() ;
		
	}

}

