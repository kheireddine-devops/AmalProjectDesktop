package com.amal.amalproject.controllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.amal.amalproject.entities.Candidature;
import com.amal.amalproject.entities.Emploi;
import com.amal.amalproject.models.CandidatureModel;
import com.amal.amalproject.models.EmploiModel;
import com.amal.amalproject.utils.JavaMailAttachement;
import com.amal.amalproject.utils.MailUtils;
import com.amal.amalproject.utils.Navigate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CandidatureController implements Initializable {

    @FXML
    private ComboBox<String> combretude;

    @FXML
    private TextField txtetude;
    @FXML
    private Button btnimportcv;

    @FXML
    private TextField txturlcv;

    @FXML
    private Button btnconfirm;

    @FXML
    private Button btnannulerc;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private TextArea txtmessage;

    
    //EmploiModel empModel = new EmploiModel();
    CandidatureModel candModel=new CandidatureModel();
	private Emploi emp ;
	
    @FXML
    void OnAnnuler(ActionEvent event) throws IOException {
//    	Navigate.changerScene(event, "ListOffres.fxml", "Liste des offres");
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    }

    @FXML
    void OnConfirmer(ActionEvent event) throws IOException {
     if (isInputValid()) {
		 // envoi du mail de cadidature

		 //final String from = "sabrine.hasni@esprit.tn";
		 final String to = "sabrinehasni3@gmail.com";
		 final String title = "Candidature au poste de référence :" + emp.getRef_emploi();
		 final String content = txtmessage.getText();
		
		 /*final String content = "Madame, Monsieur"
				 + "\r\n"
				 + "je vous présente ma candidature. Pour en savoir plus sur mes compétences mais également mes motivations,"
				 + " je vous joins mon CV ."
				 + "\r\n"
				 + "Disponible dans les plus brefs délais, je reste à votre disposition pour tout complément d’information."
				 + "\r\n" + "Monsieur, mes salutations distinguées.";*/
		 final String anexo = txturlcv.getText();
	 //JavaMailAttachement.sendEmail(from, password, to, title, content, anexo);
        MailUtils.sendMailFile(to,title,content,anexo);
		 Candidature cand = new Candidature(emp.getId_emploi(), 1, emp.getDate_expiration(), txturlcv.getText(), txtetude.getText(),txtmessage.getText());
		 candModel.add(cand);

//     Navigate.changerScene(event, "ListOffres.fxml", "Liste des offres");
		 Node source = (Node) event.getSource();
		 Stage stage = (Stage) source.getScene().getWindow();
		 stage.close();
	 }
    }

    @FXML
    void selectChoix(ActionEvent event) {
    	String choix = combretude.getSelectionModel().getSelectedItem().toString();
    	if((choix.equals("Autre")))
		 txtetude.setText("");
    	else {
    		txtetude.setText(choix);
    	}
    }
    @FXML
    void OnImporter(ActionEvent event) {
     FileChooser fc =new FileChooser();
     fc.setInitialDirectory(new File("C:\\Amal\\CV"));
     fc.getExtensionFilters().addAll( new ExtensionFilter("PDF File","*.pdf"));
     File selectedFile=fc.showOpenDialog(null);
     if(selectedFile!=null) {
    	 txturlcv.setText(selectedFile.getAbsolutePath());
     }
     
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		ObservableList<String> listrech = FXCollections.observableArrayList("Niveau scolair","BTP", "BTS", "BAC+3", "BAC+5",
				"Autre");
		combretude.setItems(listrech);
		
	}
	
	
	public void getFields(Emploi e) {
	
		emp =new Emploi(e.getId_emploi(),e.getRef_emploi(),e.getDate_expiration());
		
	}


	public boolean isInputValid(){

		String errorMessage = "";

		if (txtetude.getText() == null || txtetude.getText().length() == 0){
			errorMessage += " Niveau d'etude est obligatoire!\n";
		}
		
		/*if (!txtmail.getText().matches("^([a-zA-Z]+[a-zA-Z1-9\\\\_\\\\-]*)@esprit.tn$")){
			errorMessage += "Adresse mail doit etre sous forme exemple@esprit.tn\n";
		}*/
		if (txtmessage.getText() == null || txtmessage.getText().length() == 0){
			errorMessage += "Merci de saisir un message d'accompagnement !\n";
		}
		if (txturlcv.getText() == null || txturlcv.getText().length() == 0){
			errorMessage += " Attachement de cv est  obligatoire!\n";
		}


		if (errorMessage.length() == 0){
			return true;
		}else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Veuillez corriger les champs invalide");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}

	}


}
