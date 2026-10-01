package com.project;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ControllerListItem {

    @FXML private Label title;
    @FXML private Label subtitle;
    @FXML private ImageView img;
    @FXML private Circle circle;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    public void setImatge(String imagePath) {
        try (InputStream is = getClass().getResourceAsStream(imagePath)) {
            if (is != null) {
                this.img.setImage(new Image(is));
            } else {
                this.img.setImage(null);
            }
        } catch (Exception e) {
            this.img.setImage(null);
        }
    }

    public void setCircleColor(String color) {
        if (circle != null) {
            circle.setStyle("-fx-fill: " + color + ";");
        }
    }
}