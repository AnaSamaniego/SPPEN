package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 19/11/16.
 */

public class WayPay {

    private int id;
    private int idImage;
    private int idName;
    private boolean selected;

    public WayPay(int id, int idImage, int idName) {
        this.id = id;
        this.idImage = idImage;
        this.idName = idName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getIdName() {
        return idName;
    }

    public void setIdName(int idName) {
        this.idName = idName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}