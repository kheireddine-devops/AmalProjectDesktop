package com.amal.amalproject.controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.amal.amalproject.entities.Aide;
import com.amal.amalproject.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GestionAideController implements Initializable {

    @FXML
    private TextArea TxtPublication;

    @FXML
    private Label errorsujet;

    @FXML
    private Label errortxt;

    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnVider;
    @FXML
    private Label idd;
    @FXML
    private Label dated;
    
    @FXML
    private TableColumn<Aide, Date> date;

    @FXML
    
    private TableColumn<Aide, String> demande;

    @FXML
    
    private TableColumn<Aide, String> sujet;

    @FXML
    
    private TableView<Aide> tableAide;

    @FXML
    private TextField txtSujet;
    ObservableList<Aide> obs = FXCollections.observableArrayList();

    @FXML
    void Ajouter(ActionEvent event) {
    	String contenue = TxtPublication.getText();
     	String subject = txtSujet.getText();
    	if(isValid()) {

        	 try {

        	    Connection connection = DBConnection.getConnection();
 	            PreparedStatement ps = connection.prepareStatement("INSERT INTO demandeaide(id_demande_aide, contenue, sujet, date_publication, id_user) VALUES (NULL,?,?,?,'3')");

 	            Aide aide = new Aide();
				aide.setDatePublication(LocalDate.now());

 	            ps.setString(1, contenue);
 	            ps.setString(2, subject);
 	            ps.setDate(3, Date.valueOf(aide.getDatePublication())  );

 	            int n = ps.executeUpdate();

 	            if(n == 1) {
 	            	Alert alert = new Alert(AlertType.INFORMATION);
 	            	alert.setTitle("Ajout Demande!");
 	            	alert.setHeaderText("Information");
 	            	alert.setContentText("Demande ajoutée avec succés!");
 	            	alert.showAndWait();
 	            } else {
 	            	Alert alert = new Alert(AlertType.INFORMATION);
 	            	alert.setTitle("Ajout Demande!");
 	            	alert.setHeaderText("Information");
 	            	alert.setContentText("Ooops! Demande n'est pas ajoutée");
 	            	alert.showAndWait();

 	            }
 	            tableAide();
 	           txtSujet.clear();
 	          TxtPublication.clear();
 	         txtSujet.requestFocus();



 	        } catch (SQLException exception) {
 	            System.out.println(exception.getMessage());
 	        }

        	}
    }

    @FXML
    void Modifier(ActionEvent event) {
    	myIndex =  tableAide.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(tableAide.getItems().get(myIndex).getIdAide()));
        int n=0;
        String contenue = TxtPublication.getText();
     	String subject = txtSujet.getText();
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement("UPDATE demandeaide SET contenue=? ,date_publication=? ,sujet=?, id_user=? WHERE id_demande_aide=? ");
			Aide aide = new Aide();
			aide.setDatePublication(LocalDate.now());

            ps.setString(1, contenue);
            ps.setString(3,subject);
            ps.setDate(2, Date.valueOf(aide.getDatePublication())  );
            ps.setInt(4, 3);
            ps.setInt(5, id);

            n = ps.executeUpdate();

            if(n == 1) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Modifier Demande!");
            	alert.setHeaderText("Information");
            	alert.setContentText("Demande modifiée avec succés!");
            	alert.showAndWait();
            } else {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Modifier Demande!");
            	alert.setHeaderText("Information");
            	alert.setContentText("Ooops! Demande n'est pas modifiée");
            	alert.showAndWait();

            }

            tableAide();
 			txtSujet.clear();
 	        TxtPublication.clear();
 	        txtSujet.requestFocus();
		}catch (SQLException exception) {
            System.out.println(exception.getMessage());
		}

    }

    @FXML
    void Supprimer(ActionEvent event) {
    	 myIndex =  tableAide.getSelectionModel().getSelectedIndex();
         id = Integer.parseInt(String.valueOf(tableAide.getItems().get(myIndex).getIdAide()));
         int n = 0;
 		try {
 			Connection connection = DBConnection.getConnection();
 			PreparedStatement ps = connection.prepareStatement("DELETE FROM demandeaide WHERE id_demande_aide= ?");
 			ps.setInt(1, id);
 			n=ps.executeUpdate();
 			if(n == 1) {
             	Alert alert = new Alert(AlertType.INFORMATION);
             	alert.setTitle("Supprimer Demande!");
             	alert.setHeaderText("Information");
             	alert.setContentText("Demande supprimée avec succés!");
             	alert.showAndWait();
             } else {
             	Alert alert = new Alert(AlertType.INFORMATION);
             	alert.setTitle("Supprimer Demande!");
             	alert.setHeaderText("Information");
             	alert.setContentText("Ooops! Demande n'est pas supprimée");
             	alert.showAndWait();

             }
 			tableAide();
 			txtSujet.clear();
 	        TxtPublication.clear();
 	        txtSujet.requestFocus();
 		}catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}

    }

    @FXML
    void vider(ActionEvent event) {
    	txtSujet.clear();
        TxtPublication.clear();
        txtSujet.requestFocus();
    }
    int myIndex;
    int id;

    public  void tableAide() {
    	obs.clear();


    	try {

    		Connection connection = DBConnection.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM demandeaide WHERE id_user=3");

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


        tableAide.setItems(obs);

        tableAide.setRowFactory( tv -> {
            TableRow<Aide> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myIndex =  tableAide.getSelectionModel().getSelectedIndex();
                  id = Integer.parseInt(String.valueOf(tableAide.getItems().get(myIndex).getIdAide()));
                  txtSujet.setText(tableAide.getItems().get(myIndex).getSujet());
                  TxtPublication.setText(tableAide.getItems().get(myIndex).getContenue());
                  idd.setText(String.valueOf(tableAide.getItems().get(myIndex).getIdAide()));
                  dated.setText(String.valueOf(tableAide.getItems().get(myIndex).getDatePublication()));
               }
            });
               return myRow;
                          });

    }
    // select rows of table

    @FXML
    void afficherCom(ActionEvent event) throws IOException {
    	if(isValid()) {
    	String ida = idd.getText();
    	String sujet = txtSujet.getText();
    	String date = dated.getText();
    	String contenue = TxtPublication.getText();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/amal/amalproject/show-commentaire-aide-view.fxml"));
		Parent root = (Parent) loader.load();
		ShowCommentaireController controller =loader.getController();
		 controller.getDemande(ida, date, contenue, sujet);
		 Scene scene = new Scene(root);
		 Stage stage = new Stage();
		 stage.setScene(scene);
		 stage.show();
    	}
    	else{
    		Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Click!");
        	alert.setHeaderText("Information");
        	alert.setContentText("Slectionner une demande S'il vous plait !");
        	alert.showAndWait();
    	}

    }


    private boolean isValid(){
    	boolean sujetValid=false;
    	boolean txtValid=false;
    	if(txtSujet.getText().isBlank()) {
    		errorsujet.setText("Merci d'indiquer votre sujet");
    	}else {
    		errorsujet.setText("");
    		sujetValid = true;
    	}
    	if(TxtPublication.getText().isBlank()) {
    		errortxt.setText("Merci d'indiquer votre demande en détail");
    		sujetValid = false;
    	}else {
    		errortxt.setText("");
    		txtValid = true;
    	}
    	return (sujetValid && txtValid);

    }
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		tableAide();
	}

}
