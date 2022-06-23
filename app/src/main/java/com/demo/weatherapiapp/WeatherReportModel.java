package com.demo.weatherapiapp;

public class WeatherReportModel {


    /*private int id;
    private String main;
    private String description;
    private String icon;
*/



    private int temp;
    private int feels_like;
    private int pressure;
    private int humidity;
    private int temp_min;
    private int temp_max;


    public WeatherReportModel(int temp, int feels_like, int pressure, int humidity, int temp_min, int temp_max) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public WeatherReportModel() {
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        System.out.println("hi");
        temp = (int) ( temp - 273.15);
        this.temp = temp;
    }

    public int getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(int feels_like) {
        feels_like = (int) (feels_like - 273.15);
        this.feels_like = feels_like;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        temp_min = (int) ( temp_min - 273.15);
        this.temp_min = temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        temp_max = (int) ( temp_max - 273.15);
        this.temp_max = temp_max;
    }

    @Override
    public String toString() {
        return "WeatherReport: " +
                "Temp=" + temp + "\u2103"+
                ", Feels_like=" + feels_like + "\u2103" +
                ", Pressure=" + pressure + " hPa" +
                ", Humidity=" + humidity + "\u0025" +
                ", MinTemp=" + temp_min + "\u2103" +
                ", MaxTemp=" + temp_max + "\u2103"
                ;
    }
}
