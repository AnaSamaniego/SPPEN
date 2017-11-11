package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 12/07/16.
 */

public class City {

    private int idCity;
    private String city;

    public City() {
    }

    public City(int idCity, String city) {
        this.idCity = idCity;
        this.city = city;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
