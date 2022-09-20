package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.models.UserModel;
import com.amal.amalproject.utils.enums.AccountStatus;
import com.amal.amalproject.utils.enums.RoleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsersManagementController implements Initializable {
    private UserModel userModel = new UserModel();
    private ObservableList<Compte> comptesObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView usersTableViewID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<Compte, Integer> compteIdColumn = new TableColumn<>("ID");
        TableColumn<Compte, String> usernameColumn = new TableColumn<>("Username");
//        TableColumn<Compte, String> passwordColumn = new TableColumn<>("Password");
        TableColumn<Compte, String> roleColumn = new TableColumn<>("Role");
        TableColumn<Compte, String> statusColumn = new TableColumn<>("Status");

        compteIdColumn.setCellValueFactory(new PropertyValueFactory<>("compteId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        usernameColumn.setCellFactory(TextFieldTableCell.<Compte>forTableColumn());

//        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(ComboBoxTableCell.<Compte, String>forTableColumn("ACTIVE", "DESACTIVE"));

        statusColumn.setOnEditCommit( ( TableColumn.CellEditEvent<Compte, String> e ) ->
        {
            String newValue = e.getNewValue();

            int index = e.getTablePosition().getRow();
            Compte compte = ( Compte ) e.getTableView().getItems().get( index );

//            System.out.println("-".repeat(50));
//            System.out.println(newValue);
//            System.out.println(compte);
//            System.out.println("-".repeat(50));

            if(newValue.equals("ACTIVE")) {
                userModel.changeAccountStatus(compte.getCompteId(),AccountStatus.STATUS_ACTIVE_VERIFIED_PHONE_VERIFIED_MAIL);
            }

            if(newValue.equals("DESACTIVE")) {
                userModel.changeAccountStatus(compte.getCompteId(),AccountStatus.STATUS_DEACTIVATE);
            }


        } );

//        TableColumn actionColumn = new TableColumn("Action");
//        actionColumn.setCellValueFactory(new PropertyValueFactory<>("Action"));
//        actionColumn.setCellFactory(new BtnColumn());


        usersTableViewID.getColumns().addAll(compteIdColumn,usernameColumn/*,passwordColumn*/,roleColumn,statusColumn/*,actionColumn*/);
        usersTableViewID.setPlaceholder(new Label("No rows to display"));
        usersTableViewID.setEditable(true);



        List<Compte> comptes = userModel.getAllComptes();
        comptes.forEach(compte -> {
            if(compte.getStatus().startsWith("STATUS_ACTIVE")) {
                compte.setStatus("ACTIVE");
            }
        });
        comptesObservableList.addAll(comptes);
        usersTableViewID.setItems(comptesObservableList);

//        TableView.TableViewSelectionModel<Compte> selectionModel = usersTableViewID.getSelectionModel();
//        selectionModel.setSelectionMode(SelectionMode.SINGLE);
//        ObservableList<Compte> selectedItems = selectionModel.getSelectedItems();

//        selectedItems.addListener(
//                new ListChangeListener<Compte>() {
//                    @Override
//                    public void onChanged(
//                            Change<? extends Compte> change) {
//                        System.out.println(
//                                "Selection changed: " + change.getList());
//                    }
//                });

//        selectedItems.addListener((ListChangeListener.Change<? extends Compte> change) -> {
//            System.out.println(change.getList());
//        });

    }
}
