package com.example.james.weatherapitest;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class City represents a new City object, containing city name, temperature variables
 * and current html temperature color
 */
public class City {



    //instance variables
    double currentTemp;
    double almanacTemp;
    double differenceTemp;
    Color tempColor;
    String name;



    String completeCityName;



    String cityCode;

    Button cityButton;

    //default constructor
    public City(){

    }

    //new named City
    public City(String name){
        this.name = name;
    }


    //getters and setters
    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getAlmanacTemp() {
        return almanacTemp;
    }

    public void setAlmanacTemp(double almanacTemp) {
        this.almanacTemp = almanacTemp;
    }

    public double getDifferenceTemp() {
        return differenceTemp;
    }

    public void setDifferenceTemp(double differenceTemp) {
        this.differenceTemp = differenceTemp;
    }

    public Color getTempColor() {
        return tempColor;
    }

    public void setTempColor(Color tempColor) {
        this.tempColor = tempColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Button getCityButton() {
        return cityButton;
    }

    public void setCityButton(Button cityButton) {
        this.cityButton = cityButton;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCompleteCityName() {
        return completeCityName;
    }

    public void setCompleteCityName(String completeCityName) {
        this.completeCityName = completeCityName;
    }
}
