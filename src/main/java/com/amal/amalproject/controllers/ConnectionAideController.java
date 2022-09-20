package com.amal.amalproject.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amal.amalproject.utils.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConnectionAideController{

    @FXML
    private Button btnconnect;

    @FXML
    private AnchorPane btnsinscrire;

    @FXML
    private TextField pwd;

    @FXML
    private TextField txtlogin;

    @FXML
    void Oninscriptionload(ActionEvent event) {

    }

    @FXML
    void seconnecter(ActionEvent event) {
    	try { 
    		Connection connection = DBConnection.getConnection();
    		 PreparedStatement ps = connection.prepareStatement("SELECT * FROM compte WHERE login =? AND password=?");
    		 ps.setString(1, txtlogin.getText().toString());
    		 ps.setString(2, pwd.getText().toString());
    		 ResultSet rs=ps.executeQuery();
    		 txtlogin.clear();
    		 pwd.clear();
    		 if(rs.next()) {
    			 System.out.println("connexion");
    			 String idUser = rs.getString("id_compte");
    			 System.out.println("connexion"+idUser);
    			 String login = rs.getString("login");
    			 System.out.println("connexion" +login);
    			 
    			 
    			
    		 }else {
    			 Alert alert = new Alert(AlertType.INFORMATION);
             	alert.setTitle("Echec Connexion!");
             	alert.setHeaderText("Information");
             	alert.setContentText("Verifier votre login et mot de passe");
             	alert.showAndWait();
    		 }
             
    		
    	}catch(Exception exception) {
            System.out.println(exception.getMessage());
    }
    }

  }


