package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 24/07/16.
 */

public class Service {

    private int idService;
    private String name;
    private String lastName;
    private int typeService;
    private String photo;
    private Integer cost;
    private String grade;

    public Service() {
    }

    public Service(int idService, String name, String lastName, int typeService, String photo, Integer cost, String grade) {
        this.idService = idService;
        this.name = name;
        this.lastName = lastName;
        this.typeService = typeService;
        this.photo = photo;
        this.cost = cost;
        this.grade = grade;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTypeService() {
        return typeService;
    }

    public void setTypeService(int typeService) {
        this.typeService = typeService;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}