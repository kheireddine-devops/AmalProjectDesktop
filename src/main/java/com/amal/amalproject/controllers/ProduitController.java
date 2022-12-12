package com.amal.amalproject.controllers;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.Produit;
import com.amal.amalproject.models.ProduitModel;
import com.amal.amalproject.utils.ImporterImage;
import com.amal.amalproject.utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ProduitController implements Initializable {

        @FXML
        private TextField libeleProduit;


        @FXML
        private TableColumn<Produit, String> descriptionP;
        @FXML
        private TableColumn<Produit, String> imgUrlP;
        @FXML
        private TableColumn<Produit, String> libeleProduitP;
        @FXML
        private TableColumn<Produit, Integer> contactVP;
        @FXML
        private TableColumn<Produit, Float> prixP;
        @FXML
        private TableColumn<Produit, Integer> idProduitP =null;
        @FXML
        private TableColumn<Produit, LocalDate> dateP;
        @FXML
        private TableColumn<Produit, String> cathegorie;

        @FXML
        private TextField numVendeur;
        @FXML
        private TextField prixProduit;
        @FXML
        private ImageView imageProduit;
        @FXML
        private TextArea descriptionProduit;
        @FXML
        private Button btnAddP;
        @FXML
        private Button btnDeleteP;
        @FXML
        private Button btnUpdateP;
        @FXML
        private TableView<Produit> tableP;
        private String imageURL = null;
        int xid;
        ProduitModel produitModel = new ProduitModel();

        Connection connection = null;
        Statement state;
        Produit pc = new Produit();
        @FXML
        private Button Importe;

        public ObservableList<Produit> Produits = FXCollections.observableArrayList();



         @FXML
        public void insert(ActionEvent event) {
        Produit product = new Produit();
        if(!libeleProduit.getText().equals("")&&!prixProduit.getText().equals("")&&!numVendeur.getText().equals("")){
                        product.setLibele(libeleProduit.getText());
                        product.setNumVendeur(Integer.parseInt(numVendeur.getText()));
                        product.setPrix_produit(Float.parseFloat(prixProduit.getText()));
                        product.setDescription_produit(descriptionProduit.getText());
                        product.setPhoto_produit(imageURL != null ? imageURL: "");

                        produitModel.insertProduct(product);
                        libeleProduit.setText("");
                        prixProduit.setText("");
                        numVendeur.setText("");
                        descriptionProduit.setText("");
                        imageProduit.setImage(null);
        }
                         tableP.setItems(ProduitModel.getAllProduct());
                                                }

    @FXML
        void OnImporter(ActionEvent event) {
                FileChooser fc =new FileChooser();
                fc.setInitialDirectory(new File("C:\\Users\\utilisateur\\Desktop\\massouda\\AmalProject\\src\\main\\resources\\image"));
                fc.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("IMAGE FILE","*.JPEG"));
                File selectedFile=fc.showOpenDialog(null);
                if(selectedFile!=null) {
                        Label imgUrl = null;
                        imgUrl.setText(selectedFile.getAbsolutePath());
                }}


    /*    ************************Importer une Image ******************************     */
        @FXML
        public void ImporteButtonAction(ActionEvent actionEvent) throws Exception{
             FileChooser Chooser = new FileChooser();

                     FileChooser.ExtensionFilter extFilterJPG=new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.jpg");
                     FileChooser.ExtensionFilter extFilterPNG=new FileChooser.ExtensionFilter("PNG files(*.png)","*.png");
                     FileChooser.ExtensionFilter extFilterJPEG=new FileChooser.ExtensionFilter("JPEG files(*.jpeg)","*.jpeg");
              Chooser.getExtensionFilters().addAll(extFilterJPG,extFilterPNG,extFilterJPEG);
              File file = Chooser.showOpenDialog(null);
                BufferedImage bufferedimg = ImageIO.read(file);

                imageURL = ImporterImage.savePhoto(file);
                if (imageURL != null) {
                        Image image = new Image(ImporterImage.LoadImage(imageURL));
                        imageProduit.setImage(image);
                }
        }
        public void update(Event e)
        {
            System.out.println("Start ----------------------------------------");
                Produit product = new Produit();
            product.setId_produit(xid);
            product.setLibele(libeleProduit.getText());
            product.setNumVendeur(Integer.parseInt(numVendeur.getText()));
            product.setPrix_produit(Float.parseFloat(prixProduit.getText()));
            product.setDescription_produit(descriptionProduit.getText());
            product.setPhoto_produit(imageURL != null ? imageURL: "");

            produitModel.Update(product);
            tableP.setItems(ProduitModel.getAllProduct());
            libeleProduit.setText("");
            prixProduit.setText("");
            numVendeur.setText("");
            descriptionProduit.setText("");
            imageProduit.setImage(null);

            System.out.println("End ----------------------------------------");

        }
        public void clickTable(Event e)
        {
            Produit product =(Produit) tableP.getSelectionModel().getSelectedItem();
           // javafx.scene.image.Image imgUrlP = new javafx.scene.image.Image("imageURL");
            xid = product.getId_produit();
            libeleProduit.setText(product.getLibele());
            numVendeur.setText(product.getNumVendeur()+"");
            prixProduit.setText(product.getPrix_produit()+"");
            descriptionProduit.setText(product.getDescription_produit()+"");
            String url = ImporterImage.LoadImage(product.getPhoto_produit());

            /*imageProduit.setVisible(true) ;*/
            Image image = new Image(url);
            imageProduit.setImage(image);

        }


        /*    ************************ Delete Produit ******************************     */
        public void delete(Event e) {


                        produitModel.Delete(xid);
                        libeleProduit.setText("");
                        numVendeur.setText("");
                        prixProduit.setText("");
                        descriptionProduit.setText("");
                        imageProduit.setImage(null);
                        tableP.setItems(ProduitModel.getAllProduct());

                }

            public void initialize(URL url, ResourceBundle resourceBundle) {
                Compte compte = SessionUtils.getCurrentUser();
//                idProduitP.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
                libeleProduitP.setCellValueFactory(new PropertyValueFactory<>("libele"));
                prixP.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
                descriptionP.setCellValueFactory(new PropertyValueFactory<>("description_produit"));
                imgUrlP.setCellValueFactory(new PropertyValueFactory<>("photo_produit"));
                contactVP.setCellValueFactory(new PropertyValueFactory<>("numVendeur"));
//                cathegorie.setCellValueFactory(new PropertyValueFactory<>("cathegorie"));
//                dateP.setCellValueFactory(new PropertyValueFactory<>("dateP"));
//                tableP.setItems(ProduitModel.getAllProduct());

            }


        }


























