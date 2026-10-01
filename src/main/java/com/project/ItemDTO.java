package com.project;

import com.google.gson.annotations.SerializedName;

public class ItemDTO {
    // Camps comuns
    private String name;
    private String image;

    // Camps de Personatges
    private String color;
    private String game;

    // Camps de Consoles
    private String date;
    private String procesador;
    @SerializedName("units_sold")
    private Long unitsSold;

    // Camps de Jocs
    private Integer year;
    private String type;
    private String plot;

    // Getters
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getColor() { return color; }
    public String getGame() { return game; }
    public String getDate() { return date; }
    public String getProcesador() { return procesador; }
    public Long getUnitsSold() { return unitsSold; }
    public Integer getYear() { return year; }
    public String getType() { return type; }
    public String getPlot() { return plot; }

    // Genera la descripció dinàmicament segons la informació disponible
    public String getFormattedDescription() {
        if (plot != null) { // És un Joc
            return "Any: " + year + " | Tipus: " + type + "\n\n" + plot;
        } else if (procesador != null) { // És una Consola
            return "Llançament: " + date + "\nProcessador: " + procesador + 
                   "\nUnitats venudes: " + String.format("%,d", unitsSold);
        } else if (game != null) { // És un Personatge
            return "Joc principal: " + game + "\nColor característic: " + color;
        }
        return "";
    }
}