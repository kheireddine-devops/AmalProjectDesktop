package com.amal.amalproject.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import com.amal.amalproject.entities.Candidature;
import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.utils.DBConnection;
import com.amal.amalproject.utils.SessionUtils;
import com.amal.amalproject.utils.enums.RoleEnum;

public class CandidatureModel implements Iservice<Candidature>{
	Connection connection = DBConnection.getConnection();
	@Override
	public void add(Candidature t) {
		// TODO Auto-generated method stub
		try {

			PreparedStatement ps = connection.prepareStatement("INSERT INTO candidatures(ID_EMPLOI,ID_COMPTE,DATE_CANDIDATURE,URL_CV,NIVEAU,MESSAGE) VALUES (?,?,?,?,?,?)");
			LocalDate todaysDate = LocalDate.now();
	        Date date = java.sql.Date.valueOf(todaysDate);

			Compte compte = SessionUtils.getCurrentUser();

			ps.setInt(1,t.getId_emploi());
			ps.setInt(2,compte.getCompteId());
			ps.setDate(3,date);
			ps.setString(4, t.getUrl_cv());
			ps.setString(5, t.getNiveau());
			ps.setString(6, t.getMessage());
			System.out.println(compte.getCompteId());
			int n = ps.executeUpdate();
           System.out.println(n);
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

	}

	@Override
	public void delete(Candidature t) {
		String deleteSQL = "DELETE FROM candidatures WHERE id_emploi = ? AND id_compte=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1,t.getId_emploi());
			preparedStatement.setInt(2,t.getId_compte());
			//preparedStatement.setInt(2,3);
		int rowCount = preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public Candidature readById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public ArrayList<Candidature> readAll() {
		ArrayList<Candidature> candidatures = new ArrayList<Candidature>();
		try {
			Compte compte = SessionUtils.getCurrentUser();
			 String role =compte.getRole();
			 ResultSet resultSet=null;
			 
			 if (role.equals("ROLE_BENEFICIER")){
			PreparedStatement ps = connection.prepareStatement("SELECT ID_EMPLOI,ID_COMPTE,DATE_CANDIDATURE,URL_CV,NIVEAU,MESSAGE FROM Candidatures WHERE ID_COMPTE = ? ;");
			
			ps.setInt(1,compte.getCompteId());
			 resultSet = ps.executeQuery();
			 } else {
				 PreparedStatement ps = connection.prepareStatement("SELECT CA.ID_EMPLOI,CA.ID_COMPTE,CA.DATE_CANDIDATURE,CA.URL_CV,CA.NIVEAU,CA.MESSAGE FROM Candidatures AS CA JOIN emplois AS E ON CA.id_emploi = E.id_emploi  WHERE E.ID_COMPTE = ? ;");
					
					ps.setInt(1,compte.getCompteId());
					 resultSet = ps.executeQuery();
			 }
			 
			
			

			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				int id_compte = resultSet.getInt("id_compte");
				Date date_candidature = resultSet.getDate("date_candidature");
				String url_cv= resultSet.getString("url_cv");
				String niveau = resultSet.getString("niveau");
				String message = resultSet.getString("message");
				
				
				

				candidatures.add(new Candidature(id_emploi, id_compte, date_candidature, url_cv, niveau,message));

			}

		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return candidatures;
	}
	
	
	///read all candidature of 

	@Override
	public void update(Candidature t) {
		// TODO Auto-generated method stub
		
	}

}
