package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Produit;
import com.amal.amalproject.models.ProduitModel;
import com.amal.amalproject.utils.ImporterImage;
import com.amal.amalproject.utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BoutiqueController implements Initializable {
    @FXML
    private TextArea Bdescriptif;

    @FXML
    private ImageView BimgProduit;

    @FXML
    private Label Blibele;

    @FXML
    private Label Bprix;

    @FXML
    private Button Btncontat;

    @FXML
    private Label boutique;

    @FXML
    private TableColumn<Produit, String> descriptifProduitB;

    @FXML
    private Label fullname;
    @FXML
    private TableColumn<Produit, Integer> idProduitB;

    @FXML
    private TableColumn<Produit, String> imageProduitB;

    @FXML
    private TableColumn<Produit, String> libeleProduitB;

    @FXML
    private TableColumn<Produit, Integer> numVendeurB;

    @FXML
    private TableColumn<Produit, Float> prixProduitB;
    @FXML
    private TableColumn<Produit, LocalDate> dateP;
    @FXML
    private TableColumn<Produit, String> cathegorie;


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
    int xidB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compte compte = SessionUtils.getCurrentUser();

        this.fullname.setText("Dababi Massouda");



        libeleProduitB.setCellValueFactory(new PropertyValueFactory<>("libele"));
        prixProduitB.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
        descriptifProduitB.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
        imageProduitB.setCellValueFactory(new PropertyValueFactory<>("photo_produit"));
        numVendeurB.setCellValueFactory(new PropertyValueFactory<>("numVendeur"));
        cathegorie.setCellValueFactory(new PropertyValueFactory<>("cathegorie"));
        dateP.setCellValueFactory(new PropertyValueFactory<>("dateP"));
        tablePB.setItems(pc.getAllProduct());

//        TableView.TableViewSelectionModel<Produit> selectionModel =  tablePB.getSelectionModel();
//        selectionModel.setSelectionMode(SelectionMode.SINGLE);
//        ObservableList<Produit> selectedItems = selectionModel.getSelectedItems();
//
//        selectedItems.addListener((ListChangeListener.Change<? extends Produit> change) -> {
//            System.out.println(change.getList().get(0).getPhoto_produit());
//
//            idProduitB.setText(change.getList().get(0).getId_produit() + "");
//        });

    }

    public void clickTable(Event e)
    {
        Produit product =  (Produit) tablePB.getSelectionModel().getSelectedItem();
        xidB = product.getId_produit();
        Blibele.setText(product.getLibele());
        Bprix.setText(product.getPrix_produit()+"");
        Bdescriptif.setText(product.getDescription_produit()+"");
        Btncontat.setText(product.getNumVendeur()+"");
        String url = ImporterImage.LoadImage(product.getPhoto_produit());

        Image image = new Image(url);
        BimgProduit.setImage(image);
    }

    public void Search(Event e)
    {

        tablePB.setItems(pc.getSearchProduct(txtsearch.getText()));
    }


}
