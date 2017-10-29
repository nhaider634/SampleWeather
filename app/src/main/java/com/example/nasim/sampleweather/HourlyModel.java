package com.example.nasim.sampleweather;

/**
 * Created by NASIM on 10/5/2017.
 */

public class HourlyModel {

    private String Date;
    private double Temperature;
    private String Description;
    private String Icon;

    public HourlyModel(String date, double temperature, String description, String icon) {
        this.Date = date;
        this.Temperature = temperature;
        this.Description = description;
        this.Icon = icon;
    }

    public String  getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}

