package com.example.dxtre.sppen.model;

import java.util.ArrayList;

/**
 * Created by DXtre on 21/08/16.
 */

public class InfoService {

    private int idService;
    private String nameClient;
    private String nameService;
    private int typeService;
    private String locationService;
    private String grade;
    private int schedule;
    private String color;
    private ArrayList<LocationService> lstLocationServices;
    private ArrayList<DateService> lstDateServices;
    private ArrayList<ImageService> lstImageGallery;
    private ArrayList<SubService> lstSubServices;
    private ArrayList<Extra> lstExtras;
    private ArrayList<WayPay> lstWayPays;

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getTypeService() {
        return typeService;
    }

    public void setTypeService(int typeService) {
        this.typeService = typeService;
    }

    public String getLocationService() {
        return locationService;
    }

    public void setLocationService(String locationService) {
        this.locationService = locationService;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<LocationService> getLstLocationServices() {
        return lstLocationServices;
    }

    public void setLstLocationServices(ArrayList<LocationService> lstLocationServices) {
        this.lstLocationServices = lstLocationServices;
    }

    public ArrayList<DateService> getLstDateServices() {
        return lstDateServices;
    }

    public void setLstDateServices(ArrayList<DateService> lstDateServices) {
        this.lstDateServices = lstDateServices;
    }

    public ArrayList<ImageService> getLstImageGallery() {
        return lstImageGallery;
    }

    public void setLstImageGallery(ArrayList<ImageService> lstImageGallery) {
        this.lstImageGallery = lstImageGallery;
    }

    public ArrayList<SubService> getLstSubServices() {
        return lstSubServices;
    }

    public void setLstSubServices(ArrayList<SubService> lstSubServices) {
        this.lstSubServices = lstSubServices;
    }

    public ArrayList<Extra> getLstExtras() {
        return lstExtras;
    }

    public void setLstExtras(ArrayList<Extra> lstExtras) {
        this.lstExtras = lstExtras;
    }

    public ArrayList<WayPay> getLstWayPays() {
        return lstWayPays;
    }

    public void setLstWayPays(ArrayList<WayPay> lstWayPays) {
        this.lstWayPays = lstWayPays;
    }
}