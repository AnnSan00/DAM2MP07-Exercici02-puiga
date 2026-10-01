package com.project;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/assets/layout.fxml");
        
        if (fxmlLocation == null) {
            System.err.println("ERROR CRÍTIC: No s'ha trobat el fitxer /assets/layout.fxml dins de src/main/resources!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        Scene scene = new Scene(root, 750, 500);

        stage.setScene(scene);
        stage.setTitle("Nintendo DB");
        stage.setMinWidth(320);
        stage.setMinHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}