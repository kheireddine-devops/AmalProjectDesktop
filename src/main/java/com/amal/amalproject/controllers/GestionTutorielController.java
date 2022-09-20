package com.amal.amalproject.controllers;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Formation;
import com.amal.amalproject.entities.Video;
import com.amal.amalproject.models.FormationModel;
import com.amal.amalproject.models.TutorielModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class GestionTutorielController implements Initializable {

	@FXML
	private Button btnImpor;
	@FXML
	private Button btn2;
	@FXML
	private Button btnmedia;

	@FXML
	private Button back;
	
	@FXML
	private ListView lview;
	@FXML
	private MediaView mediaView;
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	@FXML
	private Stage stage;

	TutorielModel tutorielModel = new TutorielModel();
	int ID;

	ObservableList<Video> v = FXCollections.observableArrayList();

	// selection des lignes

	public void clickTable(Event e) {
		Video tutoriel = (Video) lview.getSelectionModel().getSelectedItem();
		Formation f = (Formation) lview.getSelectionModel().getSelectedItem();
	
		

		ID = tutoriel.getId_video();
		int IDF = tutoriel.getId_formation();

	}

	@FXML
	public void AjouterVideo(ActionEvent event) throws MalformedURLException {

		

		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Videos\\Captures"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("choisir un vidéo", "*.mp4"));
		File SelectedFile = (File) fc.showOpenDialog(null);
		String filePath = SelectedFile.toURI().toString();

		if (SelectedFile != null) {

			lview.getItems().add(SelectedFile.getAbsolutePath());
			// lview.getItems().addListener(SelectedFile.getAbsolutePath());

			
			 mediaPlayer.stop(); media =new Media(file.toURI().toURL().toExternalForm());
			 mediaPlayer=new MediaPlayer(media);
			 
			 mediaView.setMediaPlayer(mediaPlayer);
			 

			// TableVideoView.getItems().add((Video) v);
		//	ListView.setItems((ObservableList<Video>) tutorielModel.getAllVideo());

		} else {
			System.out.println("vidéo non trouver !!");

		}

	}

	@FXML
	public void close(ActionEvent event) {
		System.exit(0);

	}

	
	@FXML
	public void backCLICK(ActionEvent event) {

		try {

			if (event.getSource() == back) {
				stage = (Stage) back.getScene().getWindow();

				FXMLLoader fxmlLoader = new FXMLLoader(
						MainApplication.class.getResource("ProfilFormation.fxml"));
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
	
	// pour afficher la fenetere de lecteur video 

	
	/*@FXML
	public void mediaPlay(ActionEvent event) {

		try {

			if (event.getSource() == btnmedia) {
				stage = (Stage) btnmedia.getScene().getWindow();

				FXMLLoader fxmlLoader = new FXMLLoader(
				MainApplication.class.getResource("mediaPlay.fxml"));
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
	
*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		lview.setCellFactory(new PropertyValueFactory<Video, String>("path"));

		//ListView.setItems((ObservableList<Video>) tutorielModel.getAllVideo());

		System.out.print("*".repeat(50));
		tutorielModel.getAllVideo().forEach(System.out::println);
		System.out.print("*".repeat(50));

		
	};

}
