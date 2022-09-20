package com.amal.amalproject.controllers;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.amal.amalproject.entities.CommentaireAide;
import com.amal.amalproject.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowCommentaireController {

    @FXML
    private TableColumn<CommentaireAide, String> Commentaire;

    @FXML
    private Button afficherCommentaire;

    @FXML
    private TableColumn<CommentaireAide, Date> date;

    @FXML
    private Label id;

    @FXML
    private TableView<CommentaireAide> tableCommentaire;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtDemande;

    @FXML
    private Label txtSujet;

    @FXML
    private TableColumn<CommentaireAide, String> user;
    @FXML
    private TableColumn<CommentaireAide, String> prenom;
    ObservableList<CommentaireAide> obs = FXCollections.observableArrayList();

    @FXML
    void AficherCommentaire(ActionEvent event) {
    	obs.clear();
    	
    	try {
    		String ida = id.getText();
    	    int  id = Integer.parseInt(ida);
        		Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM commentaireaide WHERE idDemandeAide= ?");
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                
                if(resultSet.next()){
                while(resultSet.next()) {
                    int idCommentaire = resultSet.getInt("idCommentaire");
                    String txtCommentaire = resultSet.getString("txtCommentaire");
                    LocalDate dateCommentaire = resultSet.getDate("dateCommentaire") != null ? resultSet.getDate("dateCommentaire").toLocalDate() : null;
                    int idCompte = resultSet.getInt("idCompte");
                    int idDemande = resultSet.getInt("idDemandeAide");
                    String nom ="";
                    String prenom="";
                    PreparedStatement req = connection.prepareStatement("SELECT * FROM user WHERE id_user=? ");
                    req.setInt(1, idCompte);
                    ResultSet res = req.executeQuery();
                    if(res.next()) {
                        nom = res.getString("nom_user");
                        prenom =res.getString("prenom_user");
                        System.out.println("bonjour" +nom +prenom);

                    }


                    obs.add(new CommentaireAide(idCommentaire, txtCommentaire, dateCommentaire, idCompte, idDemande,nom, prenom));
                }}else {
                	 Alert alert = new Alert(AlertType.INFORMATION);
                    	alert.setTitle("Aucun Commentaire!");
                    	alert.setHeaderText("Information");
                    	alert.setContentText("Ooops! Aucun Commentaire à afficher");
                    	alert.showAndWait();
                }
            }
            catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
    	 
         date.setCellValueFactory(new PropertyValueFactory<CommentaireAide, Date>("dateCommentaire"));
         Commentaire.setCellValueFactory(new PropertyValueFactory<CommentaireAide, String>("txtCommentaire"));
        user.setCellValueFactory(new PropertyValueFactory<CommentaireAide, String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<CommentaireAide, String>("prenom"));
         tableCommentaire.setItems(obs);

    }
    public void getDemande(String ida, String Date,String demande, String sujet) {
    	txtSujet.setText(sujet);
    	txtDemande.setText(demande);
    	txtDate.setText(Date);
    	id.setText(ida);
    }

}
