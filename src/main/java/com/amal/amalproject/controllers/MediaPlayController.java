package com.amal.amalproject.controllers;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javafx.scene.control.Hyperlink;
import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.entities.Video;
import com.amal.amalproject.models.FormationModel;
import com.amal.amalproject.models.TutorielModel;
import com.amal.amalproject.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class MediaPlayController implements Initializable {

	@FXML
	private Button btnImpor;
	@FXML
	private Button btn2;

	@FXML
	private Button back;
	@FXML
	private ListView<Video> lview;

	@FXML
	private MediaView mediaView;
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	@FXML
	private Stage stage;

	TutorielModel tutorielModel = new TutorielModel();
	int ID;
	ObservableList<Video> VEDIOS = FXCollections.observableArrayList();

	// BOUTTON RETOUR A LA PAGE ACCEUIL
	@FXML
	public void backCLICK(ActionEvent event) {

		try {

			if (event.getSource() == back) {
				stage = (Stage) back.getScene().getWindow();

				FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ProfilFormation.fxml"));
				Scene scene = new Scene(fxmlLoader.load(), 800, 600);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.UTILITY);
				stage.show();
			}
		} catch (IOException ex) {
			Logger.getLogger(ProfilFormation.class.getName()).log(Level.SEVERE, null, ex);

		}
	}

	// BOUTTON FERMER LA fenétre
	@FXML
	public void close(ActionEvent event) {
		System.exit(0);

	}

	// LECTEUR VIDEO BOUTTON PLAY
	@FXML
	public void play(ActionEvent event) {
		mediaPlayer.play();
	}

	// LECTEUR VIDEO BOUTTON PAUSE
	@FXML
	public void pause(ActionEvent event) {

		mediaPlayer.pause();
	}
	// LECTEUR VIDEO BOUTTON STOP

	@FXML
	public void stop(ActionEvent event) {
		mediaPlayer.seek(mediaPlayer.getStopTime());
		mediaPlayer.stop();
	}

	// AJOUTER VIDEO PATH AU LISTE DES VIDEO
	@FXML
	public void AjouterVideo(ActionEvent event) throws MalformedURLException {

		// choisir le fichier a ajouer au liste
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("choisir un vidéo", "*.mp4"));
		
		File SelectedFile = (File) fc.showOpenDialog(null);
		
		if (SelectedFile != null) {
			String path = SelectedFile.getAbsolutePath();
			lview.refresh();
			Video ved = new Video(path);
			tutorielModel.addTutoriel(ved);
			SelectedFile.getAbsolutePath();
			lview.setItems(tutorielModel.getAllVideo());
			System.out.println("testtttttttttt 	ajout" + tutorielModel.getAllVideo());

			lview.refresh();

		} else {
			System.out.println("vidéo non trouver !!");

		}

	}

	// SUPPRIMER VIDEO
	@FXML
	public void supprimerVideo(ActionEvent event) throws MalformedURLException {

		Video VED = (Video) lview.getSelectionModel().getSelectedItem();
		if (VED == null) {
			System.out.println("aucune VIDEO a supprimer !");
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Supression d'une formation !");
		alert.setContentText("Etes vous totalement sur de vouloir supprimer la vidéo <" + VED.getId_video() + "-"
				+ VED.getUrl() + "> ??\n");
		alert.getDialogPane().setMinHeight(ID);
		alert.setHeight(400);
		Optional<ButtonType> reponse = alert.showAndWait();
		if (reponse.get().equals(ButtonType.OK)) {
			try {
				Connection connection = DBConnection.getConnection();
				Statement sqlCommand = connection.createStatement();
				sqlCommand.execute(

						("Delete  FROM `video` WHERE `id_video` = " + VED.getId_video()));

				lview.getItems().remove(lview.getSelectionModel().getSelectedItem());

				lview.refresh();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO Auto-generated method stub

		lview.setItems(tutorielModel.getAllVideo());

		

		Video VIDEOS = (Video) lview.getSelectionModel().getSelectedItem();

		System.out.println("" + "t3adiiiiiiiiiiiiiiit  haw star eli 3maltooo selectl" + VIDEOS);
		

		String pathname = "C:\\Amal\\videos\\handicap et scolarité.mp4";
	

		media = new Media(new File(pathname).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.setAutoPlay(false);

	}

	/*******************************************************************/

	// selection des lignes

	public void clickTable(Event e) {
		Video VIDEOS = (Video) lview.getSelectionModel().getSelectedItem();
		ID = VIDEOS.getId_formation();
		String url = VIDEOS.getUrl();

	}

}
