package com.project;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

    @FXML private BorderPane rootPane;
    @FXML private HBox desktopView;
    @FXML private VBox mobileView;
    @FXML private ComboBox<String> cmbCategory;
    @FXML private VBox vboxList;

    @FXML private ImageView imgDetail;
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar selector de categorías
        cmbCategory.getItems().addAll("Jocs", "Personatges", "Consoles");

        cmbCategory.setOnAction(e -> {
            String selected = cmbCategory.getValue();
            if (selected != null) {
                loadCategoryData(selected);
            }
        });

        // Cambio adaptativo en función del ancho
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() < 600) {
                desktopView.setVisible(false);
                desktopView.setManaged(false);
                mobileView.setVisible(true);
                mobileView.setManaged(true);
            } else {
                desktopView.setVisible(true);
                desktopView.setManaged(true);
                mobileView.setVisible(false);
                mobileView.setManaged(false);
            }
        });

        // Selección por defecto
        cmbCategory.getSelectionModel().select("Jocs");
    }

    @FXML
    private void onMobileCategoryClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String category = btn.getText();
        
        cmbCategory.getSelectionModel().select(category);

        // Cambiar a vista escritorio para ver los detalles cargados
        desktopView.setVisible(true);
        desktopView.setManaged(true);
        mobileView.setVisible(false);
        mobileView.setManaged(false);
    }

    private void loadCategoryData(String category) {
        vboxList.getChildren().clear();
        String jsonPath = "";

        switch (category) {
            case "Personatges":
                jsonPath = "/assets/characters.json";
                break;
            case "Consoles":
                jsonPath = "/assets/consoles.json";
                break;
            case "Jocs":
            default:
                jsonPath = "/assets/games.json";
                break;
        }

        try (InputStream is = getClass().getResourceAsStream(jsonPath)) {
            if (is == null) {
                System.err.println("No s'ha trobat l'arxiu: " + jsonPath);
                return;
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            URL resource = getClass().getResource("/assets/listItem.fxml");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                ControllerListItem itemController = loader.getController();

                String title = item.optString("name", "Sense títol");
                String imageFile = item.optString("image", "");
                String color = item.optString("color", "#1976D2");

                String subtitle = "";
                StringBuilder details = new StringBuilder();

                if (category.equals("Jocs")) {
                    subtitle = String.valueOf(item.optInt("year", 0));
                    details.append("Tipus: ").append(item.optString("type", "-")).append("\n\n");
                    details.append(item.optString("plot", "Sense descripció disponible."));

                } else if (category.equals("Personatges")) {
                    subtitle = item.optString("game", "");
                    details.append("Joc principal: ").append(subtitle).append("\n");
                    details.append("Color associat: ").append(color);

                } else if (category.equals("Consoles")) {
                    subtitle = item.optString("date", "");
                    details.append("Data de llançament: ").append(subtitle).append("\n");
                    details.append("Processador: ").append(item.optString("procesador", "-")).append("\n");
                    details.append("Unitats venudes: ").append(String.format("%,d", item.optLong("units_sold", 0)));
                }

                itemController.setTitle(title);
                itemController.setSubtitle(subtitle);
                itemController.setImatge("/assets/images/" + imageFile);
                itemController.setCircleColor(color);

                // Evento al hacer clic en un elemento de la lista
                final String finalDetails = details.toString();
                final String finalImageFile = imageFile;
                itemTemplate.setOnMouseClicked(e -> showDetail(title, finalDetails, finalImageFile));

                vboxList.getChildren().add(itemTemplate);

                // Mostrar el primer ítem por defecto
                if (i == 0) {
                    showDetail(title, details.toString(), imageFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDetail(String title, String description, String imageFile) {
        lblTitle.setText(title);
        lblDescription.setText(description);

        if (imageFile != null && !imageFile.isEmpty()) {
            try (InputStream is = getClass().getResourceAsStream("/assets/images/" + imageFile)) {
                if (is != null) {
                    imgDetail.setImage(new Image(is));
                } else {
                    imgDetail.setImage(null);
                }
            } catch (Exception e) {
                imgDetail.setImage(null);
            }
        } else {
            imgDetail.setImage(null);
        }
    }
}