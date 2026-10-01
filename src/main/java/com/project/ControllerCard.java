package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerCard {

    @FXML private ImageView imgCharacter;
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;

    // Mètode per emplenar la plantilla amb dades externes
    public void setData(String title, String description, String imagePath) {
        lblTitle.setText(title);
        lblDescription.setText(description);
        
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            imgCharacter.setImage(img);
        } catch (Exception e) {
            System.err.println("No s'ha pogut carregar la imatge: " + imagePath);
        }
    }
}