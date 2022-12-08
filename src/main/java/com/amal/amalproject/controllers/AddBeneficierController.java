package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Beneficier;
import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.models.UserModel;
import com.amal.amalproject.utils.MailUtils;
import com.amal.amalproject.utils.TwilioSMSUtils;
import com.amal.amalproject.utils.enums.AccountStatus;
import com.amal.amalproject.utils.enums.RoleEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.lang.RandomStringUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBeneficierController extends SharedController implements Initializable {

    @FXML
    private Label errorCarteHandicapNumberID;
    @FXML
    private Label errorDateExpirationID;
    @FXML
    private Label errorLoginID;
    @FXML
    private Label errorPasswordID;
    @FXML
    private Label errorFirstNameID;
    @FXML
    private Label errorLastNameID;
    @FXML
    private Label errorEmailID;
    @FXML
    private Label errorDateNaissanceID;
    @FXML
    private Label errorTelephoneID;
    @FXML
    private Label errorAdresseID;
    @FXML
    private Label errorSexeGroupID;
    @FXML
    private TextField firstNameID;
    @FXML
    private TextField lastNameID;
    @FXML
    private TextField loginID;
    @FXML
    private PasswordField passwordID;
    @FXML
    private DatePicker dateNaissanceID;
    @FXML
    private TextField emailID;
    @FXML
    private TextField telephoneID;
    @FXML
    private TextField adresseID;
    @FXML
    private TextField carteHandicapNumberID;
    @FXML
    private DatePicker dateExpirationID;
    @FXML
    private RadioButton maleRadioID;
    @FXML
    private RadioButton femaleRadioID;
    @FXML
    private ToggleGroup sexeGroupID;

    private UserModel userModel = new UserModel();


    @FXML
    protected void onAddBeneficierClick() {

        if (isValidBeneficier()) {
            /******************************** Start Entity Compte ********************************/
            Compte compte = new Compte();
            compte.setUsername(loginID.getText());
            compte.setPassword(passwordID.getText());
            compte.setRole(RoleEnum.ROLE_BENEFICIER.toString());
            compte.setStatus(AccountStatus.STATUS_ACTIVE_NOT_VERIFIED_PHONE_NOT_VERIFIED_MAIL.toString());
            compte.setTempValidateMail(RandomStringUtils.random(6, false, true));
            compte.setTempValidatePhone(RandomStringUtils.random(6, false, true));
            compte.setEmail(emailID.getText());
            compte.setPhone(telephoneID.getText());
            compte.setPhoto(null);
            System.out.println(compte);
            /******************************** End Entity Compte ********************************/
            /******************************** Start Entity Benificier ********************************/
//        String photoSelected = photoID.getText();

            Beneficier beneficier = new Beneficier();
            beneficier.setNom(firstNameID.getText());
            beneficier.setPrenom(lastNameID.getText());
            beneficier.setDateNaissance(dateNaissanceID.getValue());
            beneficier.setAdresse(adresseID.getText());
            beneficier.setSexe(((RadioButton)sexeGroupID.getSelectedToggle()).getText());
            beneficier.setCompte(compte);
            beneficier.setCarteHandicapNumber(carteHandicapNumberID.getText());
            beneficier.setDateExpiration(dateExpirationID.getValue());


            System.out.println(beneficier);

            Beneficier savedBeneficier = userModel.addBeneficier(beneficier);

            if (savedBeneficier != null) {

                String subjectMail = "Validation de l'adresse e-mail associée à votre compte";
                String htmlMail = " <div style='background-color:#ffffff;border: solid 1px silver ;padding:1em;color: #2C3333;font-family: Arial, Helvetica, sans-serif;font-size: 14px; line-height: 1.5em;'>\n" +
                        "        <h2 style='text-align:center'>Validation de l'adresse e-mail associée à votre compte</h2>\n" +
                        "        <hr>\n" +
                        "        <h3>Bonjour "+beneficier.getNom()+",</h3>\n" +
                        "\n" +
                        "        <p>Bienvenue chez AmalApplication !</p>\n" +
                        "        <p>Vous faites désormais partie de la communauté tunisienne des beneficiers, des benevoles des medecins et d'organizations collaborant pour aidez les autres et participer à la vie associative</p>\n" +
                        "\n" +
                        "        <p>Une fois connecté(e), rendez vous dans vos paramètres pour valider votre email et compléter votre profil.</p>\n" +
                        "\n" +
                        "        <p>Code de validation: <span style='letter-spacing: 0.3em;border: solid 1px silver  ;padding:0.5em 1em;font-weight: bold;background-color: #B7C4CF;'>"+beneficier.getCompte().getTempValidateMail()+"</span></p>\n" +
                        "\n" +
                        "        <p>À bientôt,<br>L'équipe AmalApplication</p>\n" +
                        "    </div>";
                MailUtils.sendHtmlMail(compte.getEmail(),subjectMail,htmlMail);
                System.out.println("SUCCESS-SEND-MAIL");
                String smsMessage = "Bonjour "+beneficier.getNom()+"\nVotre code de validation : "+beneficier.getCompte().getTempValidatePhone();
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
            /******************************** End Entity Benificier ********************************/
        } else {
            System.out.println("INVALIDE");
        }
    }

    void initBeneificier() {
        loginID.setText("kheireddine");
        passwordID.setText("azeAZE123*");
        firstNameID.setText("kheireddine");
        lastNameID.setText("mechergui");
        emailID.setText("kheireddine.mechergui@gmail.com");
        dateNaissanceID.setValue(LocalDate.now());
        telephoneID.setText("25666888");
        adresseID.setText("Beja");
        carteHandicapNumberID.setText("1122334455");
        dateExpirationID.setValue(LocalDate.now());
    }

    private boolean isValidBeneficier() {
        boolean isValidLogin = false;
        boolean isValidPassword = false;
        boolean isValidFirstName = false;
        boolean isValidLastName = false;
        boolean isValidEmail = false;
        boolean isValidDateOfBirth = false;
        boolean isValidTelephone = false;
        boolean isValidAdresse = false;
        boolean isValidSexe = false;
        boolean isValidCarteHandicapNumber = false;
        boolean isValidDateExpiration = false;

        String login = loginID.getText();
        String password = passwordID.getText();
        String firstName = firstNameID.getText();
        String lastName = lastNameID.getText();
        String email = emailID.getText();
        LocalDate dateOfBirth = dateNaissanceID.getValue();
        String telephone = telephoneID.getText();
        String adresse = adresseID.getText();
        String carteHandicapNumber = carteHandicapNumberID.getText();
        LocalDate dateExpiration = dateExpirationID.getValue();


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


        Pattern carteHandicapNumberPattern = Pattern.compile("^[0-9]{10}$");
        Matcher carteHandicapNumberMatcher = carteHandicapNumberPattern.matcher(carteHandicapNumber);
        if (carteHandicapNumber.isBlank()) {
            errorCarteHandicapNumberID.setText("Carte est obligatoire");
            isValidCarteHandicapNumber = false;
        } else if (!carteHandicapNumberMatcher.matches()) {
            errorCarteHandicapNumberID.setText("Carte n'est pas valide");
            isValidCarteHandicapNumber = false;
        }   else if (userModel.existsBeneficierByCarteHandicapNumber(carteHandicapNumber)) {
            errorCarteHandicapNumberID.setText("Ce numéro de carte est déjà utilisé");
            isValidCarteHandicapNumber = false;
        }else {
            errorCarteHandicapNumberID.setText("");
            isValidCarteHandicapNumber = true;
        }

        RadioButton sexe = (RadioButton) sexeGroupID.getSelectedToggle();

        if(sexe == null) {
            isValidSexe = false;
            errorSexeGroupID.setText("Sexe est obligatoire");
        } else {
            errorSexeGroupID.setText("");
            isValidSexe = true;
        }


        if (dateExpiration == null) {
            errorDateExpirationID.setText("La date d'expiration est obligatoire");
            isValidDateExpiration = false;
        } else if (dateExpiration.isBefore(LocalDate.now())) {
            errorDateExpirationID.setText("La carte a expirée");
            isValidDateExpiration = false;
        } else {
            errorDateExpirationID.setText("");
            isValidDateExpiration = true;
        }

        System.out.println("isValidLogin : " + isValidLogin);
        System.out.println("isValidPassword : " + isValidPassword);
        System.out.println("isValidFirstName : " + isValidFirstName);
        System.out.println("isValidLastName : " + isValidLastName);
        System.out.println("isValidEmail : " + isValidEmail);
        System.out.println("isValidDateOfBirth : " + isValidDateOfBirth);
        System.out.println("isValidTelephone : " + isValidTelephone);
        System.out.println("isValidAdresse : " + isValidAdresse);
        System.out.println("isValidSexe : " + isValidSexe);
        System.out.println("isValidCarteHandicapNumber : " + isValidCarteHandicapNumber);
        System.out.println("isValidDateExpiration : " + isValidDateExpiration);


        return  (isValidLogin && isValidPassword &&  isValidFirstName && isValidLastName &&  isValidEmail && isValidDateOfBirth &&
                isValidTelephone && isValidAdresse && isValidSexe && isValidDateExpiration && isValidCarteHandicapNumber);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("AddBenificierController.initialize()");
        initBeneificier();
    }

    public void onRetourClick(ActionEvent actionEvent) {
        this.switchView("choose-inscription-view");
    }

}

