package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 6/10/16.
 */

public class SubService {

    private int idClientSubService;
    private String name;
    private int cost;
    private int time;
    private int count;

    public int getIdClientSubService() {
        return idClientSubService;
    }

    public void setIdClientSubService(int idClientSubService) {
        this.idClientSubService = idClientSubService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}