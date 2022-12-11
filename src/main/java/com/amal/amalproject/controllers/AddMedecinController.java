package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Medecin;
import com.amal.amalproject.models.UserModel;
import com.amal.amalproject.utils.FileUploaderUtils;
import com.amal.amalproject.utils.MailUtils;
import com.amal.amalproject.utils.TwilioSMSUtils;
import com.amal.amalproject.utils.enums.AccountStatus;
import com.amal.amalproject.utils.enums.RoleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import org.apache.commons.lang.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMedecinController extends SharedController implements Initializable {

    @FXML
    public ComboBox<String> specialiteID;
    @FXML
    public ComboBox<String> assuranceID;
    @FXML
    public Label errorLoginID;
    @FXML
    public Label errorPasswordID;
    @FXML
    public Label errorFirstNameID;
    @FXML
    public Label errorLastNameID;
    @FXML
    public Label errorEmailID;
    @FXML
    public Label errorDateNaissanceID;
    @FXML
    public Label errorTelephoneID;
    @FXML
    public Label errorAdresseID;
    @FXML
    public Label errorCinID;
    @FXML
    public Label errorSexeGroupID;
    @FXML
    public Label errorMatriculeID;
    @FXML
    public Label errorSpecialiteID;
    @FXML
    public Label errorAssuranceID;
    @FXML
    private TextField firstNameID;
    @FXML
    private TextField lastNameID;
    @FXML
    private ImageView imageViewID;
    @FXML
    private TextField loginID;
    @FXML
    private PasswordField passwordID;
    @FXML
    private DatePicker dateNaissanceID;
    @FXML
    private TextField emailID;
    @FXML
    private TextField cinID;
    @FXML
    private TextField matriculeID;
    @FXML
    private TextField telephoneID;
    @FXML
    private TextField adresseID;
    @FXML
    MediaView mediaViewID;
    @FXML
    RadioButton maleRadioID;
    @FXML
    RadioButton femaleRadioID;
    @FXML
    ToggleGroup sexeGroupID;
    @FXML
    TextField photoID;
    @FXML
    ImageView TESTimageViewID;

    private ObservableList<String> specialiteList = FXCollections.observableArrayList();

    private UserModel userModel = new UserModel();


    @FXML
    protected void onAddDoctorClick() {

        if (isValidDoctor()) {
            /******************************** Start Entity Compte ********************************/
            Compte compte = new Compte();
            compte.setUsername(loginID.getText());
            compte.setPassword(passwordID.getText());
            compte.setRole(RoleEnum.ROLE_DOCTOR.toString());
            compte.setStatus(AccountStatus.STATUS_ACTIVE_NOT_VERIFIED_PHONE_NOT_VERIFIED_MAIL.toString());
            compte.setTempValidateMail(RandomStringUtils.random(6, false, true));
            compte.setTempValidatePhone(RandomStringUtils.random(6, false, true));
            compte.setPhone(telephoneID.getText());
            compte.setEmail(emailID.getText());
            compte.setPhoto("DEFAULT-URL");
            System.out.println(compte);
            /******************************** End Entity Compte ********************************/
            /******************************** Start Entity Medecin ********************************/
//        String photoSelected = photoID.getText();

            Medecin medecin = new Medecin();
            medecin.setNom(firstNameID.getText());
            medecin.setPrenom(lastNameID.getText());
            medecin.setDateNaissance(dateNaissanceID.getValue());
            medecin.setAdresse(adresseID.getText());
            medecin.setCin(cinID.getText());
            medecin.setMatricule(matriculeID.getText());
            medecin.setSexe(((RadioButton)sexeGroupID.getSelectedToggle()).getText());
            medecin.setSpecialite(specialiteID.getValue());
            medecin.setAssurance(assuranceID.getValue());
            medecin.setCompte(compte);

            System.out.println(medecin);

            Medecin savedDoctor = userModel.addMedecin(medecin);

            if (savedDoctor != null) {

                String subjectMail = "Validation de l'adresse e-mail associée à votre compte";
                String htmlMail = " <div style='background-color:#ffffff;border: solid 1px silver ;padding:1em;color: #2C3333;font-family: Arial, Helvetica, sans-serif;font-size: 14px; line-height: 1.5em;'>\n" +
                        "        <h2 style='text-align:center'>Validation de l'adresse e-mail associée à votre compte</h2>\n" +
                        "        <hr>\n" +
                        "        <h3>Bonjour "+medecin.getNom()+",</h3>\n" +
                        "\n" +
                        "        <p>Bienvenue chez AmalApplication !</p>\n" +
                        "        <p>Vous faites désormais partie de la communauté tunisienne des beneficiers, des benevoles des medecins et d'organizations collaborant pour aidez les autres et participer à la vie associative</p>\n" +
                        "\n" +
                        "        <p>Une fois connecté(e), rendez vous dans vos paramètres pour valider votre email et compléter votre profil.</p>\n" +
                        "\n" +
                        "        <p>Code de validation: <span style='letter-spacing: 0.3em;border: solid 1px silver  ;padding:0.5em 1em;font-weight: bold;background-color: #B7C4CF;'>"+medecin.getCompte().getTempValidateMail()+"</span></p>\n" +
                        "\n" +
                        "        <p>À bientôt,<br>L'équipe AmalApplication</p>\n" +
                        "    </div>";

                MailUtils.sendHtmlMail(compte.getEmail(),subjectMail,htmlMail);
                System.out.println("SUCCESS-SEND-MAIL");
                String smsMessage = "Bonjour "+medecin.getNom()+"\nVotre code de validation : "+medecin.getCompte().getTempValidatePhone();
                TwilioSMSUtils.sendMessage("+216" + compte.getPhone(),smsMessage);
                System.out.println("SUCCESS-SEND-SMS");

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez terminé avec succès le processus d'inscription\ncliquer sur ok puis s'authentifiez par votre login et mot de passe", ButtonType.OK);
                alert.setTitle("Inscription");
                alert.setHeaderText("Inscription effectuée avec succès");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    System.out.println("OK");
                    this.switchView("login-view");
                }

            }



            /******************************** End Entity Medecin ********************************/
        } else {
            System.out.println("INVALIDE");
        }
    }

    void initDoctor() {
        loginID.setText("kheireddine");
        passwordID.setText("azeAZE123*");
        firstNameID.setText("kheireddine");
        lastNameID.setText("mechergui");
        emailID.setText("kheireddine.mechergui@gmail.com");
        dateNaissanceID.setValue(LocalDate.now());
        telephoneID.setText("25666888");
        adresseID.setText("Beja");
        cinID.setText("78541236");
        specialiteID.setValue("Chirurgien Esthétique");
        assuranceID.setValue("CNAM");
        matriculeID.setText("DOC-12345678");
    }

    private boolean isValidDoctor() {
        boolean isValidLogin = false;
        boolean isValidPassword = false;
        boolean isValidFirstName = false;
        boolean isValidLastName = false;
        boolean isValidEmail = false;
        boolean isValidDateOfBirth = false;
        boolean isValidTelephone = false;
        boolean isValidAdresse = false;
        boolean isValidCIN = false;
        boolean isValidSexe = false;
        boolean isValidSpecialite = false;
        boolean isValidAssurance = false;
        boolean isValidMatricule = false;

        String login = loginID.getText();
        String password = passwordID.getText();
        String firstName = firstNameID.getText();
        String lastName = lastNameID.getText();
        String email = emailID.getText();
        LocalDate dateOfBirth = dateNaissanceID.getValue();
        String telephone = telephoneID.getText();
        String adresse = adresseID.getText();
        String cin = cinID.getText();
        String specialite = specialiteID.getValue();
        String assurance = assuranceID.getValue();
        String matricule = matriculeID.getText();


        Pattern loginPattern = Pattern.compile("^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
        Matcher loginMatcher = loginPattern.matcher(login);
        if (login.isBlank()) {
            errorLoginID.setText("Le nom d'utilisateur est obligatoire");
            isValidLogin = false;
        } else if (!loginMatcher.matches()) {
            errorLoginID.setText("Le nom d'untilisateur doit contenir entre 4 et 20 caractères · doit contenir des chiffres et des lettres");
            isValidLogin = false;
        } else if (userModel.existsCompteByLogin(login)) {
            errorLoginID.setText("Ce nom d'utilisateur est déjà utilisé");
            isValidLogin = false;
        } else {
            errorLoginID.setText("");
            isValidLogin = true;
        }

        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (password.isBlank()) {
            errorPasswordID.setText("Le mot de passe est obligatoire");
            isValidLogin = false;
        } else if(!passwordMatcher.matches()) {
            errorPasswordID.setText("Le mot de passe doit contenir au minimum 8 caractères,\nau moins une lettre minuscule et une lettre majuscule, un caractère spécial et un chiffre.");
            isValidPassword = false;
        } else {
            errorPasswordID.setText("");
            isValidPassword = true;
        }

        Pattern firstNamePattern = Pattern.compile("^[a-zA-Z]{2,25}$");
        Matcher firstNameMatcher = firstNamePattern.matcher(firstName);
        if (firstName.isBlank()) {
            errorFirstNameID.setText("Le nom est obligatoire");
            isValidFirstName = false;
        } else if (!firstNameMatcher.matches()) {
            errorFirstNameID.setText("Le nom doit contenir que des caractères");
            isValidFirstName = false;
        } else {
            errorFirstNameID.setText("");
            isValidFirstName = true;
        }

        Pattern lastNamePattern = Pattern.compile("^[a-zA-Z]{2,25}$");
        Matcher lastNameMatcher = lastNamePattern.matcher(lastName);
        if (lastName.isBlank()) {
            errorLastNameID.setText("Le prénom est obligatoire");
            isValidLastName = false;
        } else if (!lastNameMatcher.matches()) {
            errorLastNameID.setText("Le prénom doit contenir que des caractères");
            isValidLastName = false;
        } else {
            errorLastNameID.setText("");
            isValidLastName = true;
        }

        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(email);
        if (email.isBlank()) {
            errorEmailID.setText("Email est obligatoire");
            isValidEmail = false;
        } else if (!emailMatcher.matches()) {
            errorEmailID.setText("Email n'est pas valide");
            isValidEmail = false;
        } else if (userModel.existsUserByEmail(email)) {
            errorEmailID.setText("Cette adresse e-mail est déjà utilisée");
            isValidEmail = false;
        } else {
            errorEmailID.setText("");
            isValidEmail = true;
        }


        if (dateOfBirth == null) {
            errorDateNaissanceID.setText("La date de naissance est obligatoire");
            isValidDateOfBirth = false;
        } else if (dateOfBirth.isAfter(LocalDate.now())) {
            errorDateNaissanceID.setText("La date de naissance doit etre au passé");
            isValidDateOfBirth = false;
        } else {
            errorDateNaissanceID.setText("");
            isValidDateOfBirth = true;
        }

        Pattern telephonePattern = Pattern.compile("^[1-9][0-9]{7}$");
        Matcher telephoneMatcher = telephonePattern.matcher(telephone);
        if (telephone.isBlank()) {
            errorTelephoneID.setText("Numero de telephone est obligatoire");
            isValidTelephone = false;
        } else if (!telephoneMatcher.matches()) {
            errorTelephoneID.setText("Numero de telephone n'est pas valide");
            isValidTelephone = false;
        }  else if (userModel.existsUserByTelephone(telephone)) {
            errorTelephoneID.setText("Ce numéro de téléphone est déjà utilisé");
            isValidTelephone = false;
        } else {
            errorTelephoneID.setText("");
            isValidTelephone = true;
        }

        if (adresse.isBlank()) {
            errorAdresseID.setText("Adresse est obligatoire");
            isValidAdresse = false;
        } else {
            errorAdresseID.setText("");
            isValidAdresse = true;
        }


        Pattern cinPattern = Pattern.compile("^[0-9]{8}$");
        Matcher cinMatcher = cinPattern.matcher(cin);
        if (cin.isBlank()) {
            errorCinID.setText("CIN est obligatoire");
            isValidCIN = false;
        } else if (!cinMatcher.matches()) {
            errorCinID.setText("CIN n'est pas valide");
            isValidCIN = false;
        } else if (userModel.existsMedecinByCIN(cin)) {
            errorCinID.setText("Ce numéro de carte d'identité national est déjà utilisé");
            isValidCIN = false;
        } else {
            errorCinID.setText("");
            isValidCIN = true;
        }

        RadioButton sexe = (RadioButton) sexeGroupID.getSelectedToggle();
//        System.out.println("Sexe : "+ ((sexe != null) ? sexe.getText() : ""));

        if(sexe == null) {
            isValidSexe = false;
            errorSexeGroupID.setText("Sexe est obligatoire");
        } else {
//            sexeGroupID.getSelectedToggle().isSelected()
//            sexeGroupID.getSelectedToggle().getUserData().toString();
            errorSexeGroupID.setText("");
            isValidSexe = true;
        }


        if (matricule.isBlank()) {
            errorMatriculeID.setText("Matricule est obligatoire");
            isValidMatricule = false;
        } else if (userModel.existsMedecinByMatricule(matricule)) {
            errorMatriculeID.setText("Ce matricule est déjà utilisé");
            isValidMatricule = false;
        }else {
            errorMatriculeID.setText("");
            isValidMatricule = true;
        }

        if (specialite == null) {
            errorSpecialiteID.setText("Specialite est obligatoire");
            isValidSpecialite = false;
        } else {
            errorSpecialiteID.setText("");
            isValidSpecialite = true;
        }

        if (assurance == null) {
            errorAssuranceID.setText("Assurance est obligatoire");
            isValidAssurance = false;
        } else {
            errorAssuranceID.setText("");
            isValidAssurance = true;
        }

        System.out.println("isValidLogin : " + isValidLogin);
        System.out.println("isValidPassword : " + isValidPassword);
        System.out.println("isValidFirstName : " + isValidFirstName);
        System.out.println("isValidLastName : " + isValidLastName);
        System.out.println("isValidEmail : " + isValidEmail);
        System.out.println("isValidDateOfBirth : " + isValidDateOfBirth);
        System.out.println("isValidTelephone : " + isValidTelephone);
        System.out.println("isValidAdresse : " + isValidAdresse);
        System.out.println("isValidCIN : " + isValidCIN);
        System.out.println("isValidSexe : " + isValidSexe);
        System.out.println("isValidAssurance : " + isValidAssurance);
        System.out.println("isValidMatricule : " + isValidMatricule);
        System.out.println("isValidSpecialite : " + isValidSpecialite);


        return  (isValidLogin && isValidPassword &&  isValidFirstName && isValidLastName &&  isValidEmail && isValidDateOfBirth &&
                isValidTelephone && isValidAdresse && isValidCIN && isValidSexe && isValidAssurance && isValidMatricule && isValidSpecialite);

    }

    @FXML
    public void onAddPhotoClick(ActionEvent actionEvent) throws URISyntaxException {
        Node source = (Node) actionEvent.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Images", "*.png")
                ,new FileChooser.ExtensionFilter("JPG Images", "*.jpg")
        );
        File file = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            System.out.println(file.getAbsoluteFile().toURI().toString());
            Image image = new Image(file.getAbsoluteFile().toURI().toString());
            imageViewID.setImage(image);
//            photoSelected = file.getAbsoluteFile().toURI().toString();
            try {
                String photoURL = FileUploaderUtils.savePhoto(file);
                photoID.setText(photoURL);

                System.out.println(photoURL);
                System.out.println(new URL(photoURL).toURI().toString());

                Image imageTEST = new Image(photoURL);
                TESTimageViewID.setImage(imageTEST);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void onAddVideoClick(ActionEvent actionEvent) throws URISyntaxException {
        Node source = (Node) actionEvent.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4 Videos", "*.mp4")
        );
        File file = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            System.out.println(file.getAbsoluteFile().toURI().toString());
            Media media = new Media(file.getAbsoluteFile().toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaViewID.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("AddUserController.initialize()");
        specialiteList.addAll(
                "Cardiologue  (Cœur)",
                "Chirurgien Esthétique",
                "Chirurgien Orthopédiste Traumatologue",
                "Dentiste  (Dents)",
                "Dermatologue  (Peau)",
                "Endocrinologue Diabétologue",
                "Gastro-entérologue",
                "G'énéraliste",
                "Gynécologue Obstétricien",
                "Interniste",
                "Laboratoire d'analyses de biologie médicale",
                "Néphrologue",
                "Neurologue  (Cerveau et Nerfs)",
                "Nutritionniste",
                "Ophtalmologue  (Yeux)",
                "Oto-Rhino-Laryngologiste (ORL)  (Oreille nez gorge)",
                "Pédiatre  (Enfant)",
                "Pneumologue",
                "Psychiatre  (Troubles mentaux)",
                "Psychothérapeute",
                "Radiologue",
                "Rhumatologue",
                "Sexologue",
                "Urologue"
        );
        specialiteID.getItems().addAll(specialiteList);
        //initDoctor();
//        maleRadioID.setToggleGroup(sexeGroupID);
//        femaleRadioID.setToggleGroup(sexeGroupID);
//        ValidationSupport support = new ValidationSupport();
//
//        ValidationSupport validationSupport = new ValidationSupport();
//        validationSupport.registerValidator(firstNameID, Validator.createEmptyValidator("FirstName Is Required"));
    }

    public void onRetourClick(ActionEvent actionEvent) {
        this.switchView("choose-inscription-view");
    }
}
