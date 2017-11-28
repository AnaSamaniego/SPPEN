package com.example.dxtre.sppen.model;

import java.util.ArrayList;

/**
 * Created by DXtre on 21/08/16.
 */

public class DateService {

    private int day;
    private int month;
    private int year;
    private ArrayList<String> hours;

    public DateService(int day, int month, int year, ArrayList<String> hours) {
        this.day = day;
        this.month = month;
        this.year = year;

        if (hours!=null && hours.size()>0)
            this.hours = hours;
        else {
            this.hours = new ArrayList<>();
            this.hours.add("15:00");
            this.hours.add("16:00");
            this.hours.add("17:00");
            this.hours.add("18:00");
            this.hours.add("19:00");
            this.hours.add("20:00");
            this.hours.add("21:00");
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getHours() {
        return hours;
    }

    public void setHours(ArrayList<String> hours) {
        this.hours = hours;
    }
}
