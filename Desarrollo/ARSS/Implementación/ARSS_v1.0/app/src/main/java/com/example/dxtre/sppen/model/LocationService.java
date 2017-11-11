package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 21/08/16.
 */

public class LocationService {

    private int id;
    private String locationName;
    private String latitude;
    private String longitude;
    private String address;

    public LocationService(int id, String location_name) {
        this.id = id;
        this.locationName = location_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}