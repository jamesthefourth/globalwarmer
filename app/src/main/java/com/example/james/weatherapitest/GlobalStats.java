package com.example.james.weatherapitest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Global stats class will display statistics for current warming/cooling in 5 select cities
 */
public class GlobalStats extends AppCompatActivity {

    //declare buttons globally
    Button cityOneButton;
    Button cityTwoButton;
    Button cityThreeButton;
    Button cityFourButton;
    Button cityFiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize button views for displaying colors
        cityOneButton = (Button) findViewById(R.id.cityOneButton);
        cityTwoButton = (Button) findViewById(R.id.cityTwoButton);
        cityThreeButton = (Button) findViewById(R.id.cityThreeButton);
        cityFourButton = (Button) findViewById(R.id.cityFourButton);
        cityFiveButton = (Button) findViewById(R.id.cityFiveButton);


        //--------------THE following code should be replaced once refactoring of Main is complete
        //attempt to open a new async task thread for gathering data for each city
        // city one:
    }



}

