package com.amal.amalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AmeniMainApplication extends Application {

    private Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(new BorderPane(), 1000, 600);
        loadView("Show-allaide-view");
        stage.setTitle("Amal Application");
        stage.setScene(scene);
//        stage.setResizable(true);
        stage.show();
    }
    public static void loadView(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

        URL url = AmeniMainApplication.class.getResource(fxml + ".css");
        if (url != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(url.toExternalForm());
        }
    }

    public static Parent includeView(String fxml) throws IOException {
        return loadFXML(fxml);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL urlUserHomeCss = AmeniMainApplication.class.getResource("user-home-view.css");
        URL url = AmeniMainApplication.class.getResource(fxml + ".css");
        if (url != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(urlUserHomeCss.toExternalForm());
            scene.getStylesheets().add(url.toExternalForm());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(AmeniMainApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}


