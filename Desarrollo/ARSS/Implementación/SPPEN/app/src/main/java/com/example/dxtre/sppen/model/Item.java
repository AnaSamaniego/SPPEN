package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 13/07/16.
 */

public class Item {
    private String id;
    private String name;
    private String color;
    private Boolean selected=false;

    public Item() {
        super();
    }

    public Item(String id, String name, Boolean selected) {
        super();
        this.setId(id);
        this.setName(name);
        this.setSelected(selected);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}