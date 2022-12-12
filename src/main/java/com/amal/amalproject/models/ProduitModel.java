package com.amal.amalproject.models;

import com.amal.amalproject.controllers.BoutiqueController;
import com.amal.amalproject.entities.Produit;
import com.amal.amalproject.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduitModel {

    static Statement state;
    Connection connection = DBConnection.getConnection();
//Inserer des produits//
    public void insertProduct(Produit produit)  {

        try {
             state = DBConnection.getConnection().createStatement();
           // Compte compte = SessionUtils.getCurrentUser();

           // compte.getCompteId()

            PreparedStatement ps = this.connection.prepareStatement("INSERT INTO `produit` (`id_produit`, `libelleP`, `prixP`, `descriptionP`, `photoP`, `numVendeur`, `cathegorie`, `dateP`, `id_beneficier`) VALUES (NULL, ?, ?, ?,?,?, ?,?,?);");

            ps.setString(1,produit.getLibele());
            ps.setFloat(2,produit.getPrix_produit());
            ps.setString(3,produit.getDescription_produit());
            ps.setString(4,produit.getPhoto_produit());
            ps.setInt(5,produit.getNumVendeur());
            ps.setString(6,"ppppppppp");
            ps.setDate(7, Date.valueOf( LocalDate.now()));
            ps.setInt(8,produit.getId_beneficier());

            int n = ps.executeUpdate();

            //                        DBConnection.closeConnection();

        } catch (SQLException ex) {
//                        DBConnection.closeConnection();
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    //Delete les produits//
    public  void Delete (int id) {

        try {
            state = DBConnection.getConnection().createStatement();
            state.executeUpdate("Delete from produit_boutique WHERE id_produit = "+ id);
//                        DBConnection.closeConnection();

        } catch (SQLException ex) {
//                        DBConnection.closeConnection();
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


    //Modifier les produits//

    public  void Update (Produit produit){
        try {
            System.out.println("Start Update ----------------------------------------");
            System.out.println(produit);
            state = DBConnection.getConnection().createStatement();
            String sql = "UPDATE `amal`.`produit_boutique` SET `libele` = '"+produit.getLibele()+"', `prix_produit` = '"+produit.getPrix_produit()+"', `description_produit` = '"+produit.getDescription_produit()
                    +"', `numVendeur` = '"+produit.getNumVendeur()+"' WHERE `produit_boutique`.`id_produit` = "+produit.getId_produit()+";";


            state.executeUpdate(sql);
//                        DBConnection.closeConnection();
            System.out.println("End Update ----------------------------------------");

        } catch (SQLException ex) {
//                        DBConnection.closeConnection();
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }




    //Renvoie les donnees saisi dans la table//
    public static ObservableList<Produit> getAllProduct()
    {
        ObservableList<Produit> product = FXCollections.observableArrayList();
        try {
            state = DBConnection.getConnection().createStatement();
            ResultSet result =  state.executeQuery("SELECT * FROM produit");

            while(result.next())
            {
                Produit pr = new Produit();

                pr.setId_produit(result.getInt("id_produit"));
                pr.setLibele(result.getString("libelleP"));
                pr.setPrix_produit(result.getFloat("prixP"));
                pr.setDescription_produit(result.getString("descriptionP"));
                pr.setPhoto_produit(result.getString("photoP"));
                pr.setNumVendeur(result.getInt("numVendeur"));

                pr.setDateP(result.getDate("dateP").toLocalDate());
                pr.setCathegorie(result.getString("cathegorie"));
                pr.setId_beneficier(result.getInt("id_beneficier"));

                product.add(pr);
            }
//                        DBConnection.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
//                        DBConnection.closeConnection();
        }

        return product;
    }

    public ObservableList<Produit> getSearchProduct(String libele) {
        ObservableList<Produit> product = FXCollections.observableArrayList();
        try {
            state = DBConnection.getConnection().createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM produit_boutique WHERE libele LIKE '%" + libele + "%'");

            while (result.next()) {
                Produit pr = new Produit();
                pr.setId_produit(result.getInt(1));
                pr.setLibele(result.getString(2));
                pr.setPrix_produit(result.getFloat(3));
                pr.setDescription_produit(result.getString(4));
                pr.setPhoto_produit(result.getString(5));

                pr.setNumVendeur(result.getInt(7));
                product.add(pr);
            }
//                        DBConnection.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
//                        DBConnection.closeConnection();
        }

        return product;
    }




}


