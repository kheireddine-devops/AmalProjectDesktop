package com.amal.amalproject.controllers;

import com.amal.amalproject.models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    private UserModel userModel = new UserModel();
    @FXML
    private PieChart usersByTypeID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Map<String,Integer> userNumbers = this.userModel.getUserNumbersByRole();

        if (!userNumbers.isEmpty()) {

            usersByTypeID.setLegendSide(Side.BOTTOM);
            usersByTypeID.setLabelsVisible(true);
            usersByTypeID.setLegendVisible(true);
            usersByTypeID.setBackground(Background.fill(Color.WHITE));
            usersByTypeID.setStartAngle(30);

            for (Map.Entry<String,Integer> entry: userNumbers.entrySet()) {
                PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
                usersByTypeID.getData().add(slice);
            }

//            usersByTypeID.getData().forEach(System.out::println);
        }


//        PieChart.Data slice1 = new PieChart.Data("Medecin", 15);
//        PieChart.Data slice2 = new PieChart.Data("Bénéficier", 150);
//        PieChart.Data slice3 = new PieChart.Data("Benevole", 60);
//        PieChart.Data slice4 = new PieChart.Data("Organization", 12);
//
//        usersByTypeID.getData().add(slice1);
//        usersByTypeID.getData().add(slice2);
//        usersByTypeID.getData().add(slice3);
//        usersByTypeID.getData().add(slice4);
//




    }
}
