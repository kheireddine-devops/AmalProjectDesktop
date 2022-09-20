package com.amal.amalproject.models;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.entities.Video;
import com.amal.amalproject.utils.DBConnection;

import com.amal.amalproject.utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TutorielModel extends FormationModel implements ITutorielModel {
	Statement state;
	Connection connection = DBConnection.getConnection();

	// return all data as observable list because table parmetar is observable

	@Override
	public void addTutoriel(Video video) {
		// TODO Auto-generated method stub

		try {

			PreparedStatement ps = connection.prepareStatement("INSERT INTO video(" + "id_video,url,id_compte )" + " VALUES (?,?,?)"

			);

			ps.setInt(1, video.getId_video());

			ps.setString(2, video.getUrl());

			Compte compte = SessionUtils.getCurrentUser();
			ps.setInt(3,compte.getCompteId());

			int n = ps.executeUpdate();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

	}

	// affichage lise video

	// return all data as observable list because table parmetar is observable

	public ObservableList<Video> getAllVideo() {

		ObservableList<Video> VEDIOS = FXCollections.observableArrayList();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT  * FROM `video` ");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {

				// afficher chaque valeurs des champs

				Video VD = new Video();

				VD.setId_video(resultSet.getInt(1));

				VD.setUrl(resultSet.getString(2));

				System.out.println("============> " + VD.getUrl());

				VEDIOS.add(VD);

			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());

		}
		System.out.println("tous les videos" + VEDIOS);
		return VEDIOS;

	}

}
