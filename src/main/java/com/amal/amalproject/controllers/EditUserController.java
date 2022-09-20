package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.models.UserModel;
import com.amal.amalproject.utils.FileUploaderUtils;
import com.amal.amalproject.utils.SessionUtils;
import com.amal.amalproject.utils.enums.AccountStatus;
import com.amal.amalproject.utils.enums.RoleEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController extends SharedController implements Initializable {
    private UserModel userModel = new UserModel();

    @FXML
    private ImageView imageViewID;
    @FXML
    private TextField photoID;
    @FXML
    public void OnChoosePhotoAction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Images", "*.png")
                ,new FileChooser.ExtensionFilter("JPG Images", "*.jpg")
        );
        File file = fileChooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            String photoURL = FileUploaderUtils.savePhoto(file);
            photoID.setText(photoURL);

            System.out.println(photoURL);

            Image image = new Image(FileUploaderUtils.loadImage(photoURL));
            imageViewID.setImage(image);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compte compte = SessionUtils.getCurrentUser();
        if(compte != null) {
            Image image = new Image(FileUploaderUtils.loadImage(""));
            imageViewID.setImage(image);
        }
    }

    @FXML
    public void OnEditPhotoAction(ActionEvent actionEvent) {
        Compte compte = SessionUtils.getCurrentUser();
        if(compte != null && !photoID.getText().isEmpty()) {

            if (compte.getRole().equals(RoleEnum.ROLE_ORGANIZATION.toString())) {
                this.userModel.editOrganizationProfilePhoto(compte.getCompteId(),photoID.getText());
                System.out.println("PHOTO-ORGANIZATION");
            } else {
                this.userModel.editUserProfilePhoto(compte.getCompteId(),photoID.getText());
                System.out.println("PHOTO-USER");
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Votre image de profile modifier avec succès\npour que l'application utilise correctement votre nouvelle photo ,\nIl faut vous re-connecte");
            alert.setTitle("Photo de Profile");
            alert.setHeaderText("Image de profile modifier avec succès");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                System.out.println("OK");
                SessionUtils.clearSession();
                this.switchView("login-view");
            }
        }
    }
}
