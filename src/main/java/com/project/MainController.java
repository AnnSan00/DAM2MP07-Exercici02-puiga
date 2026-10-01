package com.project;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML private BorderPane rootPane;
    @FXML private HBox desktopView;
    @FXML private VBox mobileView;

    @FXML private ComboBox<String> cmbCategory;
    @FXML private VBox vboxList;

    @FXML private ImageView imgDetail;
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;

    // Amplada límit per fer el canvi entre mòbil i escriptori
    private static final double MOBILE_BREAKPOINT = 550.0;

    @FXML
    public void initialize() {
        // 1. Configuració inicial del ComboBox
        cmbCategory.getItems().addAll("Jocs", "Personatges", "Consoles");
        cmbCategory.getSelectionModel().select("Jocs");

        // 2. Control d'estat responsive (Adaptatiu)
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateLayout(newVal.doubleValue());
        });

        // 3. Carregar dades d'exemple / llegides del JSON
        loadItems();
    }

    private void updateLayout(double width) {
        boolean isMobile = width < MOBILE_BREAKPOINT;

        // Amaga/Mostra i ajusta la gestió de l'espai
        desktopView.setVisible(!isMobile);
        desktopView.setManaged(!isMobile);

        mobileView.setVisible(isMobile);
        mobileView.setManaged(isMobile);
    }

    private void loadItems() {
        // Neteja la llista actual
        vboxList.getChildren().clear();

        // Exemple d'element carregat (en cas real es llegeix des de data.json)
        addListItem("Pokémon Red i Blue", 
                    "/assets/images/pokemon.jpg", 
                    "Pokémon és una sèrie de jocs on els jugadors capturen i entrenen criatures conegudes com a Pokémon...");
        
        addListItem("Super Mario Bros", 
                    "/assets/images/mario.jpg", 
                    "Un dels jocs de plataformes més iconics de la historia de Nintendo.");
    }

    private void addListItem(String title, String imagePath, String description) {
        HBox itemBox = new HBox(10);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        itemBox.setStyle("-fx-padding: 8px; -fx-background-color: #F0F0F0; -fx-background-radius: 5px; -fx-cursor: hand;");

        // Miniatura
        ImageView thumb = new ImageView();
        thumb.setFitWidth(40);
        thumb.setFitHeight(40);
        thumb.setPreserveRatio(true);

        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is != null) thumb.setImage(new Image(is));
        } catch (Exception ignored) {}

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        itemBox.getChildren().addAll(thumb, titleLabel);

        // Al fer clic sobre l'ítem de la llista
        itemBox.setOnMouseClicked(e -> {
            lblTitle.setText(title);
            lblDescription.setText(description);
            try {
                InputStream is = getClass().getResourceAsStream(imagePath);
                if (is != null) imgDetail.setImage(new Image(is));
            } catch (Exception ignored) {}
        });

        vboxList.getChildren().add(itemBox);
    }
}