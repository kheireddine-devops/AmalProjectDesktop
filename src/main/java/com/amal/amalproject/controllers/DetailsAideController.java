package com.amal.amalproject.controllers;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.amal.amalproject.entities.Aide;
import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.User;
import com.amal.amalproject.utils.DBConnection;

import com.amal.amalproject.utils.SessionUtils;
import com.amal.amalproject.utils.TwilioSMSUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class DetailsAideController implements Initializable {

    @FXML
    private Label adresse;

    @FXML
    private Button btnajouter;

    @FXML
    private TextArea commentaire;

    @FXML
    private Label datenais;

    @FXML
    private Label email;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private TextArea pubAide;

    @FXML
    private Label sexe;

    @FXML
    private Label tel;
    @FXML
    private Label idAide;
    @FXML
    private Button btnuser;
    @FXML
    private Label idUser;
    static  int iduser = 3;
    ObservableList<User> obs = FXCollections.observableArrayList();
    
    public void getDemande(String id, String demande) {
    	idAide.setText(id);
    	pubAide.setText(demande);
    	 
    }
    public static void getidUser (int user){
    	iduser = user;
    }
    
    @FXML
    void ajouter(ActionEvent event) {
    	String ida = idAide.getText();
    	int idaide = Integer.parseInt(ida);
    	
    	String com = commentaire.getText();
    	try {
    		Connection connection = DBConnection.getConnection();
			Compte compte = SessionUtils.getCurrentUser();
    		PreparedStatement ps =connection.prepareStatement("INSERT INTO commentaireaide(idCommentaire, txtCommentaire, dateCommentaire, idCompte, idDemandeAide) VALUES (null,?,?,?,?)");
    		LocalDate dt=LocalDate.now();
    		ps.setString(1, com);
    		ps.setDate(2, Date.valueOf(dt));
    		ps.setInt(3, compte.getCompteId());
    		ps.setInt(4, idaide);
    		int n =ps.executeUpdate();
    		 if(n == 1) {
	            	Alert alert = new Alert(AlertType.INFORMATION);
	            	alert.setTitle("Ajout Commentaire!");
	            	alert.setHeaderText("Information");
	            	alert.setContentText("Commentaire ajoutée avec succés!");
	            	alert.showAndWait();

				 TwilioSMSUtils.sendMessage("+216"+telephone,com);
	            } else {
	            	Alert alert = new Alert(AlertType.INFORMATION);
	            	alert.setTitle("Ajout Commentaire!");
	            	alert.setHeaderText("Information");
	            	alert.setContentText("Ooops! Commentaire n'est pas ajoutée");
	            	alert.showAndWait();
	            	
	            }
    		 commentaire.clear();
    		 commentaire.requestFocus();
    		
    	}catch (SQLException exception) {
    		System.out.println(exception.getMessage());
    	}
    	

    }
    String Nom;
    String Prenom;
    String datenaissance;
	String adremail;
	String telephone;
	String sexeuser;
	String adresseuser;
    
    
    
        public void afficheruser() throws SQLException {
        	
        	System.out.println("bonjour " +iduser);
    	Connection connection = DBConnection.getConnection();
    	PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE id_user = ?");
    	ps.setInt(1, iduser);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
        	 Nom = resultSet.getString("nom_user");
        	 Prenom = resultSet.getString("prenom_user");
        	 datenaissance =resultSet.getString("date_naissance_user");
        	 adremail = resultSet.getString("email_user");
        	 telephone = resultSet.getString("telephone_user");
        	 sexeuser = resultSet.getString("sexe_user");
        	 adresseuser = resultSet.getString("adresse_user");	
        }
        
    }
        public void aff() throws SQLException {
        	afficheruser();
        	nom.setText(Nom);
        	prenom.setText(Prenom);
        	datenais.setText(datenaissance);
        	email.setText(adremail);
        	tel.setText(telephone);
        	sexe.setText(sexeuser);
        	adresse.setText(adresseuser);
        }
   

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		try {
			aff();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

