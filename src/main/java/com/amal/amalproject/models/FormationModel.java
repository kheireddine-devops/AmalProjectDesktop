package com.amal.amalproject.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.entities.User;
import com.amal.amalproject.utils.DBConnection;
import com.amal.amalproject.utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FormationModel implements IFormationModel {
	Statement state;
	Connection connection = DBConnection.getConnection();

//ajout dan la base 
	@Override
	public void addFormation(Formation formation) {
		// TODO Auto-generated method stub
		System.out.println("Start addFormation");
		try {

			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO formation(" + "theme,descriptif,date_debut, dateFin, Nbr_jours, Nbr_personnes,id_compte) "
							                 + "VALUES (?,?,?,?,?,?,?)"
						);

			//ps.setInt(1, formation.getId_formation());
			// TEST ID

			ps.setString(1, formation.getTheme());
			ps.setString(2, formation.getDescriptif());

			if (formation.getDate_publication() != null)
				ps.setDate(3, Date.valueOf(formation.getDate_publication()));
			else
				ps.setDate(3, null);

		
			if (formation.getDate_publicationf() != null)
				ps.setDate(4, Date.valueOf(formation.getDate_publicationf()));
			else
				ps.setDate(4, null);
			
			ps.setString(5, formation.getNbr_jours());
			
			ps.setString(6, formation.getNbr_personnes());

			Compte compte = SessionUtils.getCurrentUser();
			
			ps.setInt(7, compte.getCompteId());
			
			
			int n = ps.executeUpdate();

			System.out.println("NB + "+ n);

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		System.out.println("End addFormation");

	}

	// return all data as observable list because table parmetar is observable

	public ObservableList<Formation> getAllFormation() {

		ObservableList<Formation> formations = FXCollections.observableArrayList();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM formation;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				// afficher  chaque valeurs des champs 

				Formation fr = new Formation();

				fr.setId_formation(resultSet.getInt(1));

				fr.setTheme(resultSet.getString(2));

				fr.setDescriptif(resultSet.getString(3));
				
				// test si la date de début est vide 
				Date date = resultSet.getDate(4);
				
				  if (date != null) {
					fr.setDate_publication(date.toLocalDate());
				} else {
					System.out.println("la date est null");
				}
				 
				  
				  
				// test si la date de date_Fin est vide 
				  
					Date dateF = resultSet.getDate(5);
					
					if (dateF != null) {
						fr.setDate_publicationf(dateF.toLocalDate());
						
					} else {
						System.out.println("la date est null");
					}
				
				fr.setNbr_jours(resultSet.getString(6));
				
				fr.setNbr_personnes(resultSet.getString(7));
				
				fr.setId_compte(resultSet.getInt(8));
				

				System.out.println("============> iciii " + fr);

				formations.add(fr);

			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());

		}
		System.out.println("tous les formations" + formations);
		return formations;

	}

	// modifier formation dans la base
	@Override
	public void updateFormation(Formation formation) {

		try {

			PreparedStatement ps = connection.prepareStatement("UPDATE formation set   theme = " + "'"+ formation.getTheme() + "'"
			        + ", descriptif = " + "'" + formation.getDescriptif() + "'"
				    + ", date_debut = " + "'" + formation.getDate_publication() + "'" + ""
				    + ", dateFin = " + "'" + formation.getDate_publicationf() + "'" + ""
				    + ", Nbr_jours = " + "'" + formation.getNbr_jours() + "'" + ""
				    + ", Nbr_personnes = " + "'" + formation.getNbr_personnes() + "'" + ""
					
					+ " WHERE id_formation = " + formation.getId_formation()

			);

		
			int n = ps.executeUpdate();
			
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

	}

}
