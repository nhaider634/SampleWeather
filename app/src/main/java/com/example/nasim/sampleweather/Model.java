package com.example.nasim.sampleweather;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by NASIM on 10/2/2017.
 */

public class Model implements Serializable
{
    private long Date;
    private double Pressure;
    private double Temperature;
    private double Tmax;
    private double Tmin;
    private String Description;
    private double Humidity;
    private double Wind;
    private String Icon;


    public Model(long date, double pressure, double temperature,double tmin, double tmax, String description, double humidity, double wind, String icon) {
        this.Date = date;
        this.Pressure = pressure;
        this.Temperature = temperature;
        this.Description = description;
        this.Humidity = humidity;
        this.Wind = wind;
        this.Icon = icon;
        this.Tmin= tmin;
        this.Tmax =tmax;

    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public double getPressure() {
        return Pressure;
    }

    public void setPressure(double pressure) {
        Pressure = pressure;
    }

    public double getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }

    public double getTmax() {
        return Tmax;
    }

    public void setTmax(double tmax) {
        Tmax = tmax;
    }

    public double getTmin() {
        return Tmin;
    }

    public void setTmin(double tmin) {
        Tmin = tmin;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getHumidity() {
        return Humidity;
    }

    public void setHumidity(double humidity) {
        Humidity = humidity;
    }

    public double getWind() {
        return Wind;
    }

    public void setWind(double wind) {
        Wind = wind;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}
