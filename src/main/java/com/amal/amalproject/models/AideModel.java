package com.amal.amalproject.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import com.amal.amalproject.entities.Aide;
import com.amal.amalproject.utils.DBConnection;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AideModel  {
	Connection connection = DBConnection.getConnection();

	
	public Aide AddAide(Aide aide) {
	
		 try {

	            PreparedStatement ps = connection.prepareStatement("INSERT INTO demandeaide(id_demande_aide, contenue, sujet, date_publication, id_user) VALUES (NULL,?,?,?,'3')");

	            aide.setDatePublication(LocalDate.now());
	            
	            ps.setString(1, aide.getContenue());
	            ps.setString(2,aide.getSujet());
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

	        } catch (SQLException exception) {
	            System.out.println(exception.getMessage());
	        }
		return null;
	}
	
	
	public static  int UpdateAide(Aide aide) {
		int n=0;
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement("UPDATE demandeaide SET contenue=? ,date_publication=? ,sujet=?, id_user=? WHERE id_demande_aide=? ");
			aide.setDatePublication(LocalDate.now());
            
            ps.setString(1, aide.getContenue());
            ps.setString(3,aide.getSujet());
            ps.setDate(2, Date.valueOf(aide.getDatePublication())  );
            ps.setInt(4, 3);
            ps.setInt(5, aide.getIdAide());
            
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

			
		}catch (SQLException exception) {
            System.out.println(exception.getMessage());
		}
		return n;
	}


	public static int DeleteAide(int id) {
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
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return n;
	}

	public static Aide GetId(int id) {
		 Aide aide =new Aide();
		 Connection connection = DBConnection.getConnection();
	   try {
		   PreparedStatement ps = connection.prepareStatement("SELECT * FROM demandeaide WHERE id_demande_aide=?");
		   ps.setInt(1, id);
		   ResultSet rst = ps.executeQuery();
		   if (rst.next()) {

		        int idAide = rst.getInt("id_demande_aide");
			String contenue = rst.getString("contenue");
			LocalDate datePublication = rst.getDate("date_publication") != null ? rst.getDate("date_publication").toLocalDate() : null;
			String sujet = rst.getString("sujet");
			int idUser = rst.getInt("id_user");

			   aide.setIdAide(idAide);
			   aide.setContenue(contenue);
			   aide.setDatePublication(datePublication);
			   aide.setSujet(sujet);
			   aide.setIdUser(idUser);

		            }
		   else {
			   Alert alert = new Alert(AlertType.INFORMATION);
           	alert.setTitle("Recherche non valide!");
           	alert.setHeaderText("Information");
           	alert.setContentText("Ooops! Entrer un ID valide");
           	alert.showAndWait();
		   }
		   
	   }catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return aide;
	}
	
	

}
