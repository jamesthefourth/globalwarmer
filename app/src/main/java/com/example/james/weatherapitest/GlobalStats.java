package com.example.james.weatherapitest;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
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

    //declare instance variables for city temperatures
    /*double cityOneTempCurrent;
    double cityOneTempAlmanac;
    double cityOneTempDifference;

    double cityTwoTempCurrent;
    double cityTwoTempAlmanac;
    double cityTwoTempDifference;

    double cityThreeTempCurrent;
    double cityThreeTempAlmanac;
    double cityThreeTempDifference;

    double cityFourTempCurrent;
    double cityFourTempAlmanac;
    double cityFourTempDifference;

    double cityFiveTempCurrent;
    double cityFiveTempAlmanac;
    double cityFiveTempDifference;*/


    City cityOne;
    City cityTwo;
    City cityThree;
    City cityFour;
    City cityFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_global);

        //initialize button views for displaying colors
        cityOneButton = (Button) findViewById(R.id.cityOneButton);
        cityTwoButton = (Button) findViewById(R.id.cityTwoButton);
        cityThreeButton = (Button) findViewById(R.id.cityThreeButton);
        cityFourButton = (Button) findViewById(R.id.cityFourButton);
        cityFiveButton = (Button) findViewById(R.id.cityFiveButton);

        //initialize Cities
        cityOne = new City(getString(R.string.cityOne));
        cityTwo = new City(getString(R.string.cityTwo));
        cityThree = new City(getString(R.string.cityThree));
        cityFour = new City(getString(R.string.cityFour));
        cityFive = new City(getString(R.string.cityFive));

        cityRequest cityOneRequest = new cityRequest(cityOneButton, cityOne);
        networkRequest cityTwoRequest = new networkRequest(cityTwoButton, cityTwo);
        networkRequest cityThreeRequest = new networkRequest(cityThreeButton, cityThree);
        networkRequest cityFourRequest = new networkRequest(cityFourButton, cityFour);
        networkRequest cityFiveRequest = new networkRequest(cityFiveButton, cityFive);


        //back button to return to main activity
        Button backToMain = (Button) findViewById(R.id.backToMain);


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(GlobalStats.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }


    //method to return warmer/cooler than average string depending on the difference in temp
    private String isWarmer(double d){
        if(d >= 0)
        {
            return getString(R.string.warmer);
        }
        else{
            return getString(R.string.cooler);
        }
    }

    /**
     * Class that processes the input string and used the wunderground API to
     * return the best match to the search
     */
    class cityRequest extends AsyncTask<String,String,String> {
        String cityCode;
        String cityName;
        private Exception exception;
        Button cityViewRequest;
        HttpURLConnection urlConnection;
        City mCity;

        protected cityRequest(Button button,City city){

            cityViewRequest = button;
            mCity = city;
            cityName = mCity.getName();



            super.execute();

        }


        protected String doInBackground(String... args) {


            StringBuilder result = new StringBuilder();
            JSONObject resultObject = null;
            try {
                String currentCity = cityName;

                //The reason for this is so you can replace all of the spaces
                // it has to be a new string becuase they are immutable
                String currentCity2 = currentCity.replaceAll(" ", "%20");
                mCity.setCompleteCityName(currentCity2);
                cityName = mCity.getName();

                //THIS IS WHERE WE USE AUTO COMPLETE TO GET THE PROPER CITY NAME

                URL url = new URL("http://autocomplete.wunderground.com/aq?query=" + cityName);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                resultObject = new JSONObject(result.toString());

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }


            try {
                //THIS IS WHERE WE VALIDATE THE USER INPUT OF CITYNAME
                //build the output string from the JSON data and the user input city name

                //precaution/testing if/else to make sure we get some kind of city name returned
                if(resultObject != null) {

                    JSONArray values = resultObject.getJSONArray("RESULTS");
                    StringBuilder output = new StringBuilder();

                    output.append(values.getJSONObject(0).get("name"));
                    cityCode = values.getJSONObject(0).get("zmw").toString();
                    mCity.setCityCode(cityCode);
                    mCity.setCompleteCityName((values.getJSONObject(0).get("name").toString()));
                    return output.toString();
                }
                else
                    return cityName;

            }catch (JSONException e){
                return "City name not recognized";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            networkRequest one = new networkRequest(cityViewRequest, mCity);


        }

    }



    //initiate new thread for network connected api call in order to avoid slowing UI thread.
    //this class handles gathering the current temperature, the next class will handle almanac data

    class networkRequest extends AsyncTask<String,String,String> {
        String cityName;
        private Exception exception;
        Button responseView;
        HttpURLConnection urlConnectionOne;
        City mCity;



        protected networkRequest(Button mButton, City city){
            responseView = mButton;
            mCity = city;
            cityName = mCity.getCityCode();
            super.execute();

        }


        protected String doInBackground(String... args) {
            StringBuilder result = new StringBuilder();
            JSONObject resultObject = null;
            try {
                URL url = new URL("http://api.wunderground.com/api/f1650fb7e0ae610e/conditions/q/zmw:"+ cityName +".json");
                urlConnectionOne = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnectionOne.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                resultObject = new JSONObject(result.toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnectionOne.disconnect();
            }


            try {
                //build the output string from the JSON data and the user input city name
                JSONObject values = resultObject.getJSONObject("current_observation");
                mCity.setCurrentTemp(values.getDouble("temp_f"));
                return "success.";


            } catch (JSONException e) {
                return "Sorry, we don't have current weather for that city.";
            }


        }



        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            secondNetworkRequest one = new secondNetworkRequest(responseView, mCity);


        }

        //second network request handles almanac data-----------------------------------------------------------------
        class secondNetworkRequest extends AsyncTask<String,String,String> {

            String cityName;
            private Exception exception;
            Button responseView;
            HttpURLConnection urlConnection;
            City mCity;

            protected secondNetworkRequest(Button button, City city){

                responseView = button;
                mCity = city;
                cityName = city.getCityCode();
                super.execute();

            }

            protected String doInBackground(String... args) {


                StringBuilder result = new StringBuilder();
                JSONObject resultObject = null;
                try {
                    URL url = new URL("http://api.wunderground.com/api/f1650fb7e0ae610e/almanac/q/zmw:"+ cityName +".json");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    resultObject = new JSONObject(result.toString());

                }catch( Exception e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }

                try {
                    //THIS IS WHERE WE TALK ABOUT ALMANAC DATA
                    //build the output string from the JSON data and the user input city name
                    JSONObject values = resultObject.getJSONObject("almanac");

                    mCity.almanacTemp = values.getJSONObject("temp_high").getJSONObject("normal").getDouble("F");
                    return "success.";

                }catch (JSONException e){
                    return "Sorry, we don't have info for that city yet! Please try again.";
                }

            }

            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                mCity.differenceTemp = mCity.currentTemp - mCity.almanacTemp;
                String differenceTempString = String.format("%.2f", mCity.differenceTemp);


                if(mCity.getName() == getString(R.string.cityOne)){
                    //set the color of the button based on the difference in temperature
                    cityOneButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityOneButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");

                }
                if(mCity.getName() == getString(R.string.cityTwo)){
                    //set the color of the button based on the difference in temperature
                    cityTwoButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityTwoButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");

                }
                if(mCity.getName() == getString(R.string.cityThree)){
                    //set the color of the button based on the difference in temperature
                    cityThreeButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityThreeButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");

                }
                if(mCity.getName() == getString(R.string.cityFour)){
                    //set the color of the button based on the difference in temperature
                    cityFourButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFourButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");

                }
                if(mCity.getName() == getString(R.string.cityFive)){
                    //set the color of the button based on the difference in temperature
                    cityFiveButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFiveButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");

                }





            }

        }

    }



}

