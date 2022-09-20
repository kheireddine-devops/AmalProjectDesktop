package com.amal.amalproject.models;
import com.amal.amalproject.entities.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.amal.amalproject.utils.*;
public class EmploiModel implements Iservice <Emploi>{

	Connection connection = DBConnection.getConnection();
	@Override
	public void add(Emploi t) {
		try {
			Compte compte = SessionUtils.getCurrentUser();
			PreparedStatement ps = connection.prepareStatement("INSERT INTO emplois(ID_EMPLOI,TITRE_EMPLOI,DESCRIPTIF_EMPLOI,SECTEUR,REF_EMPLOI,DATE_EXPIRATION,ID_COMPTE) VALUES (NULL,?,?,?,?,?,?)");

			ps.setString(1,t.getTitre_emploi());
			ps.setString(2,t.getDescriptif_emploi());
			ps.setString(3,t.getSecteur());
			ps.setString(4, t.getRef_emploi());
			ps.setDate(5, t.getDate_expiration());
			ps.setInt(6,compte.getCompteId());



			int n = ps.executeUpdate();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

	}

	@Override
	public void delete(Emploi t) {

		String deleteSQL = "DELETE FROM emplois WHERE id_emploi = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1,t.getId_emploi());

			int rowCount = preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
		}


	}

	@Override
	public Emploi readById(int id) {
		Emploi e = null ;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois WHERE id_emploi = ?;");
			ps.setInt(1,id);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				e=new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration);

			}

		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return e;
	}

	@Override
	public ArrayList<Emploi> readAll() {
		ArrayList<Emploi> emplois = new ArrayList<Emploi>();
		try {
			Compte compte = SessionUtils.getCurrentUser();
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois WHERE id_compte = ?;");
			ps.setInt(1,compte.getCompteId());
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				emplois.add(new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration));

			}

		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return emplois;
	}


	public ArrayList<Emploi> readAllOffres() {
		ArrayList<Emploi> emplois = new ArrayList<Emploi>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				emplois.add(new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration));

			}

		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return emplois;
	}




	@Override
	public void update(Emploi e) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE emplois SET titre_emploi=?,descriptif_emploi=?,secteur=?,ref_emploi=?,date_expiration=? WHERE id_emploi = ?;");
			ps.setString(1,e.getTitre_emploi());
			ps.setString(2,e.getDescriptif_emploi());
			ps.setString(3,e.getSecteur());
			ps.setString(4,e.getRef_emploi());
			ps.setDate(5,e.getDate_expiration());
			ps.setInt(6,e.getId_emploi());
			ps.executeUpdate();
		}catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
	}
	//recherche by titre
	public ArrayList<Emploi> readByTitre(String titre) {
		ArrayList<Emploi> emplois = new ArrayList<Emploi>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois WHERE titre_emploi = ?;");
			ps.setString(1,titre);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				emplois.add(new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration));

			}

		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return emplois;
	}
	//recherche by secteur
	public ArrayList<Emploi> readBySecteur(String sect) {
		ArrayList<Emploi> emplois = new ArrayList<Emploi>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois WHERE secteur = ?;");
			ps.setString(1,sect);
			ResultSet resultSet = ps.executeQuery();


			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				emplois.add(new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration));
			}
		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return emplois;
	}

	//Recherche by reference

	public ArrayList<Emploi> readByReference(String ref) {
		ArrayList<Emploi> emplois = new ArrayList<Emploi>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois WHERE ref_emploi = ?;");
			ps.setString(1,ref);
			ResultSet resultSet = ps.executeQuery();


			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				emplois.add(new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration));
			}
		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return emplois;
	}
	//Recherche by Date

	public ArrayList<Emploi> readByDate(java.sql.Date dat) {
		ArrayList<Emploi> emplois = new ArrayList<Emploi>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT id_emploi,titre_emploi,descriptif_emploi,secteur,ref_emploi,date_expiration FROM emplois WHERE date_expiration = ?;");
			ps.setDate(1, dat);
			ResultSet resultSet = ps.executeQuery();


			while (resultSet.next()) {

				int id_emploi = resultSet.getInt("id_emploi");
				String titre_emploi = resultSet.getString("titre_emploi");
				String descriptif_emploi = resultSet.getString("descriptif_emploi");
				String secteur = resultSet.getString("secteur");
				String ref_emploi = resultSet.getString("ref_emploi");
				Date date_expiration = resultSet.getDate("date_expiration");

				emplois.add(new Emploi (id_emploi,titre_emploi, descriptif_emploi,secteur,ref_emploi,(java.sql.Date) date_expiration));
			}
		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return emplois;
	}


}
