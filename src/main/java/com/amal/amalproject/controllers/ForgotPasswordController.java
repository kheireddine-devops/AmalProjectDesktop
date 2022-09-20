package com.amal.amalproject.controllers;

import com.amal.amalproject.models.UserModel;
import com.amal.amalproject.utils.MailUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang.RandomStringUtils;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordController extends SharedController implements Initializable {
    private UserModel userModel = new UserModel();
    @FXML
    private TextField emailID;
    @FXML
    private Label errorEmailID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private boolean isValidEmail() {
        boolean isValidEmail = false;
        String email = emailID.getText();

        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(email);
        if (email.isBlank()) {
            errorEmailID.setText("Email est obligatoire");
            isValidEmail = false;
        } else if (!emailMatcher.matches()) {
            errorEmailID.setText("Email n'est pas valide");
            isValidEmail = false;
        } else if (!userModel.existsUserByEmail(email)) {
            errorEmailID.setText(" L'adresse e-mail que vous avez saisie n'est associée à aucun compte");
            isValidEmail = false;
        } else {
            errorEmailID.setText("");
            isValidEmail = true;
        }

        return isValidEmail;
    }

    @FXML
    public void onForgotClick(ActionEvent actionEvent) {

        if (isValidEmail()) {
            String passwordGenerated = RandomStringUtils.random(10, true, true);
            int compteId = userModel.getUserIdByEmail(emailID.getText());
            if(compteId != -1) {
                boolean resultat = userModel.forgotPassword(compteId,passwordGenerated);
                if(resultat) {
                    System.out.println("SUCESS-SEND-MAIL-RESET-PASSWORD : " + compteId + " / " + passwordGenerated);
                    String htmlContent = "<div style='background-color:#ffffff;border: solid 1px silver ;padding:1em;color: #2C3333;font-family: Arial, Helvetica, sans-serif;font-size: 14px; line-height: 1.5em;'>\n" +
                            "        <h2 style='text-align:center'>Récupération de mot de passe sur AmalApplication</h2>\n" +
                            "        <hr>\n" +
                            "        <p>Vous avez demandé à réinitialiser vos identifiants de connexion sur AmalApplication.</p>\n" +
                            "        <p>Cette opération vous attribuera un nouveau mot de passe.</p>\n" +
                            "        <p>votre nouveau mot de passe c'est : <span style='letter-spacing: 0.3em;border: solid 1px silver  ;padding:0.5em 1em;font-weight: bold;background-color: #B7C4CF;'>"+passwordGenerated+"</span></p>\n" +
                            "        <p>À bientôt,<br>L'équipe AmalApplication</p>\n" +
                            "    </div>";
                    MailUtils.sendHtmlMail(emailID.getText(),"Récupérer votre mot de passe",htmlContent);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez terminé avec succès le processus de récupération de votre mot de passe\ncliquer sur ok puis s'authentifiez par votre nouveau mot de passe", ButtonType.OK);
                    alert.setTitle("Réinitialiser le mot de passe");
                    alert.setHeaderText("Mot de passe récupérer avec succès");
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.OK) {
                        System.out.println("OK");
                        this.switchView("login-view");
                    }
                } else {
                    System.out.println("FAILED-SEND-MAIL-RESET-PASSWORD : " + compteId + " / " + passwordGenerated);
                }
            }
        }
    }

    @FXML
    public void onRetourClick(ActionEvent actionEvent) {
        this.switchView("login-view");
    }
}
