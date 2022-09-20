package com.amal.amalproject.controllers;

import com.amal.amalproject.MainApplication;
import com.amal.amalproject.utils.FileUploaderUtils;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SharedController {
    protected void switchView(String view) {
        try {
            MainApplication.loadView(view);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
