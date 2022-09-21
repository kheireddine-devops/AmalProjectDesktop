package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Produit;
import com.amal.amalproject.models.ProduitModel;
import com.amal.amalproject.utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class BoutiqueController implements Initializable {

    @FXML
    private Label boutique;

    @FXML
    private TableColumn<?, ?> descriptifProduitB;

    @FXML
    private Label fullname;
    @FXML
    private TableColumn<?, ?> idProduitB;

    @FXML
    private TableColumn<?, ?> imageProduitB;

    @FXML
    private TableColumn<?, ?> libeleProduitB;

    @FXML
    private TableColumn<?, ?> numVendeurB;

    @FXML
    private TableColumn<?, ?> prixProduitB;


    @FXML
    private ScrollPane scroll;

    @FXML
    private TableView<Produit> tablePB;

    @FXML
    private TextField txtsearch;
    @FXML
    private Button searchbtn;
    ProduitModel pc = new ProduitModel();
    public ObservableList<Produit> Produits = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compte compte = SessionUtils.getCurrentUser();

        this.fullname.setText("Dababi Massouda");



        libeleProduitB.setCellValueFactory(new PropertyValueFactory<>("libele"));
        prixProduitB.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        descriptifProduitB.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
        imageProduitB.setCellValueFactory(new PropertyValueFactory<>("photo_produit"));
        numVendeurB.setCellValueFactory(new PropertyValueFactory<>("numVendeur"));
        tablePB.setItems(pc.getAllProduct());

    }

    public void clickTable(Event e)
    {
        Produit product =  (Produit) tablePB.getSelectionModel().getSelectedItem();

        idProduitB.setText(product.getLibele());
        libeleProduitB.setText(product.getLibele());
        prixProduitB.setText(product.getPrix_produit()+"");
        descriptifProduitB.setText(product.getDescription_produit()+"");
        imageProduitB.setText(product.getPhoto_produit()+"");
        numVendeurB.setText(product.getNumVendeur()+"");
    }

    public void Search(Event e)
    {

        tablePB.setItems(pc.getSearchProduct(txtsearch.getText()));
    }


}
