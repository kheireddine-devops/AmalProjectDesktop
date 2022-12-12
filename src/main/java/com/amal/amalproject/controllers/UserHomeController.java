package com.amal.amalproject.controllers;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Organization;
import com.amal.amalproject.entities.User;
import com.amal.amalproject.models.UserModel;
import com.amal.amalproject.utils.FileUploaderUtils;
import com.amal.amalproject.utils.SessionUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.amal.amalproject.utils.MenuEnum;
import com.amal.amalproject.utils.enums.RoleEnum;

public class UserHomeController extends SharedController implements Initializable {

    public ImageView imageViewID;
    private UserModel userModel = new UserModel();
    @FXML
    public ScrollPane viewsID;
    @FXML
    public Label fullnameID;
    @FXML
    public VBox sidebarID;
    private List<Button> buttons = new ArrayList<>();

    public void onMenuItemAction(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        MenuEnum data = MenuEnum.valueOf((String) node.getUserData());

        this.buttons.forEach(button -> {
            button.getStyleClass().removeIf(s -> s.equals("menu-item-active"));
        });
        node.getStyleClass().add("menu-item-active");

        switch (data) {
            case MENU_RENDEZ_VOUS_VIEW :
                System.out.println("MENU_RENDEZ_VOUS_VIEW");
                this.viewsID.setContent(MainApplication.includeView("rendez-vous-view"));
                break;
            case MENU_CHOOSE_INSCRIPTION_VIEW:
                System.out.println("MENU_CHOOSE_INSCRIPTION_VIEW");
                this.viewsID.setContent(MainApplication.includeView("choose-inscription-view"));
                break;
            case MENU_INSCRIPTION_DOCTOR_VIEW:
                System.out.println("MENU_INSCRIPTION_DOCTOR_VIEW");
                this.viewsID.setContent(MainApplication.includeView("add-medecin-view"));
                break;
            case MENU_LOGIN_VIEW:
                System.out.println("MENU_ILOGIN_VIEW");
                this.viewsID.setContent(MainApplication.includeView("login-view"));
                break;
            case MENU_EDIT_USER_VIEW:
                System.out.println(MenuEnum.MENU_EDIT_USER_VIEW);
                this.viewsID.setContent(MainApplication.includeView("edit-user-view"));
                break;
            case MENU_SHOW_AIDES_VIEW:
                System.out.println(MenuEnum.MENU_SHOW_AIDES_VIEW);
                this.viewsID.setContent(MainApplication.includeView("Show-allaide-view"));//TODO
                break;
            case MENU_ADMIN_DASHBOARD_VIEW:
                System.out.println(MenuEnum.MENU_ADMIN_DASHBOARD_VIEW);
                this.viewsID.setContent(MainApplication.includeView("admin-dashboard-view"));
                break;
            case MENU_OFFRES_EMPLOI_VIEW:
                System.out.println(MenuEnum.MENU_OFFRES_EMPLOI_VIEW);
                this.viewsID.setContent(MainApplication.includeView("ListOffres"));
                break;
            case MENU_USERS_MANAGEMENT_VIEW:
                System.out.println(MenuEnum.MENU_USERS_MANAGEMENT_VIEW);
                this.viewsID.setContent(MainApplication.includeView("users-management-view"));
                break;
            case MENU_GESTION_AIDE_VIEW:
                System.out.println(MenuEnum.MENU_GESTION_AIDE_VIEW);
                this.viewsID.setContent(MainApplication.includeView("gestion-aide-view"));
                break;
            case MENU_LIST_EMPLOI_VIEW:
                System.out.println(MenuEnum.MENU_LIST_EMPLOI_VIEW);
                this.viewsID.setContent(MainApplication.includeView("ListEmploi"));
                break;
            case MENU_BENEVOLE_GESTION_PUBLICATION_FORMATION:
                System.out.println(MenuEnum.MENU_BENEVOLE_GESTION_PUBLICATION_FORMATION);
                this.viewsID.setContent(MainApplication.includeView("GestionPublicationFormation"));
                break;
            case MENU_BENEVOLE_GESTION_TUTORIEL:
                System.out.println(MenuEnum.MENU_BENEVOLE_GESTION_TUTORIEL);
                this.viewsID.setContent(MainApplication.includeView("Gestiontutoriel"));
                break;
            case MENU_BENEFICIER_GESTION_PUBLICATION_FORMATION:
                System.out.println(MenuEnum.MENU_BENEFICIER_GESTION_PUBLICATION_FORMATION);
                this.viewsID.setContent(MainApplication.includeView("BeneficiairePublicationFormation"));
                break;
            case MENU_BENEFECIER_TUTORIEL:
                System.out.println(MenuEnum.MENU_BENEFECIER_TUTORIEL);
                this.viewsID.setContent(MainApplication.includeView("BeneficiaireTutoriel"));
                break;
            case MENU_PRODUIT_VIEW:
                System.out.println(MenuEnum.MENU_PRODUIT_VIEW);
                this.viewsID.setContent(MainApplication.includeView("Produit"));
                break;
            case MENU_BOUTIQUE_VIEW:
                System.out.println(MenuEnum.MENU_BOUTIQUE_VIEW);
                this.viewsID.setContent(MainApplication.includeView("Boutique"));
                break;
            default:
                System.out.println("MENU : PROBLEM");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compte compte = SessionUtils.getCurrentUser();
        System.out.println("*************" + compte);

        /* admin benevole benificier doctor organization */
        if(compte == null) {
            compte = userModel.login("benificier","azeAZE123*");
            SessionUtils.addCurrentUser(compte);
        }

        if(compte != null) {

            System.out.println(compte);

            List<MenuItemButton> menuItemButtons = new ArrayList<>();
            if (compte.getRole().equals(RoleEnum.ROLE_ORGANIZATION.toString())) {

                Organization organization = userModel.getOrganizationById(compte.getCompteId());
                System.out.println("*".repeat(50));

                System.out.println(organization);
                this.fullnameID.setText(organization.getNom());

                if(!(compte.getPhoto() == null)) {
                    SessionUtils.addCurrentUserPhoto(compte.getPhoto());
                    Image image = new Image(FileUploaderUtils.loadImage(compte.getPhoto()));
                    imageViewID.setImage(image);
                }

            } else {
                User user = userModel.getUserById(compte.getCompteId());
                this.fullnameID.setText(user.getNom() + " " + user.getPrenom());

                System.out.println(user.getNom() + " " + user.getPrenom());
                System.out.println(compte.getPhoto());

                if((compte.getPhoto() != null)) {
                    SessionUtils.addCurrentUserPhoto(compte.getPhoto());
                    Image image = new Image(FileUploaderUtils.loadImage(compte.getPhoto()));
                    imageViewID.setImage(image);
                }
            }




            switch (RoleEnum.valueOf(compte.getRole())) {
                case ROLE_BENEFICIER:
                    menuItemButtons.addAll( List.of(
                            new MenuItemButton("Gestion Aides",MenuEnum.MENU_GESTION_AIDE_VIEW.toString()),
                            new MenuItemButton("Offres",MenuEnum.MENU_OFFRES_EMPLOI_VIEW.toString()),
                            new MenuItemButton("Publication formation",MenuEnum.MENU_BENEFICIER_GESTION_PUBLICATION_FORMATION.toString()),
                            new MenuItemButton("Tutoriels",MenuEnum.MENU_BENEFECIER_TUTORIEL.toString()),
                            new MenuItemButton("Gestion Produit",MenuEnum.MENU_PRODUIT_VIEW.toString()),
                            new MenuItemButton("Profile Management",MenuEnum.MENU_EDIT_USER_VIEW.toString())
                    ));
                    break;
                case ROLE_BENEVOLE:
                    menuItemButtons.addAll( List.of(
                            new MenuItemButton("Rendez-vous",MenuEnum.MENU_RENDEZ_VOUS_VIEW.toString()),
                            new MenuItemButton("Aides Management",MenuEnum.MENU_SHOW_AIDES_VIEW.toString()),
                            new MenuItemButton("Publication formation",MenuEnum.MENU_BENEVOLE_GESTION_PUBLICATION_FORMATION.toString()),
                            new MenuItemButton("Tutoriels",MenuEnum.MENU_BENEVOLE_GESTION_TUTORIEL.toString()),
                            new MenuItemButton("Store",MenuEnum.MENU_BOUTIQUE_VIEW.toString()),
                            new MenuItemButton("Profile Management",MenuEnum.MENU_EDIT_USER_VIEW.toString())
                    ));
                    break;
                case ROLE_DOCTOR:
                    menuItemButtons.addAll( List.of(
                            new MenuItemButton("Rendez-vous",MenuEnum.MENU_RENDEZ_VOUS_VIEW.toString()),
                            new MenuItemButton("Aides Management",MenuEnum.MENU_SHOW_AIDES_VIEW.toString()),
                            new MenuItemButton("Store",MenuEnum.MENU_BOUTIQUE_VIEW.toString()),
                            new MenuItemButton("Rendez-vous Management",MenuEnum.MENU_RENDEZ_VOUS_VIEW.toString()),
                            new MenuItemButton("Profile Management",MenuEnum.MENU_EDIT_USER_VIEW.toString())

                    ));
                    break;
                case ROLE_ORGANIZATION:
                    menuItemButtons.addAll( List.of(
                            new MenuItemButton("Offres des Emplois",MenuEnum.MENU_LIST_EMPLOI_VIEW.toString()),
                            new MenuItemButton("Store",MenuEnum.MENU_BOUTIQUE_VIEW.toString()),
                            new MenuItemButton("Profile Management",MenuEnum.MENU_EDIT_USER_VIEW.toString())
                    ));
                    break;
                case ROLE_ADMIN:
                    menuItemButtons.addAll( List.of(
                            new MenuItemButton("Dashboard",MenuEnum.MENU_ADMIN_DASHBOARD_VIEW.toString()),
                            new MenuItemButton("Users Management",MenuEnum.MENU_USERS_MANAGEMENT_VIEW.toString()),
                            new MenuItemButton("Aides Management",MenuEnum.MENU_SHOW_AIDES_VIEW.toString()),
                            new MenuItemButton("Store",MenuEnum.MENU_BOUTIQUE_VIEW.toString()),
                            new MenuItemButton("Profile Management",MenuEnum.MENU_EDIT_USER_VIEW.toString())
                    ));
                    try {
                        //TODO Profile Admin : Default View [users-management-view]
                        this.viewsID.setContent(MainApplication.includeView("admin-dashboard-view"));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }

            menuItemButtons.forEach(menuItemButton -> {
                Button button = new Button();
                button.setStyle("");
                button.setAlignment(Pos.CENTER_LEFT);
                button.setMaxWidth(Double.POSITIVE_INFINITY);
                button.setUserData(menuItemButton.getUserDate());
                button.setText(menuItemButton.getText());
                button.setOnAction(actionEvent -> {
                    try {
                        this.onMenuItemAction(actionEvent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.buttons.add(button);
            });

            this.sidebarID.getChildren().addAll(this.buttons);
        }


    }

    @FXML
    public void OnLogOutAction(ActionEvent actionEvent) {
        SessionUtils.clearSession();
        this.switchView("login-view");
    }
}

class MenuItemButton {
    private String text;
    private String userDate;

    public MenuItemButton(String text, String userDate) {
        this.setText(text);
        this.setUserDate(userDate);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }
}
