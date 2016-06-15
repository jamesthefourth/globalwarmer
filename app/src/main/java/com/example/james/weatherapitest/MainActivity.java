package com.example.james.weatherapitest;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Current build of Globally Warmer app final project for Grant Zukowski and James Orrell
 * 5/31/16
 */

public class MainActivity extends AppCompatActivity {

    TextView responseView;
    TextView responseViewAlmanac;
    TextView differenceViewTemp;
    TextView cityViewRequest;
    EditText currentInput;
    double currentTemp;
    double averageTemp;
    double differenceTemp;
    String completeCityName;
    networkRequest one;
    secondNetworkRequest two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        differenceViewTemp = (Button)findViewById(R.id.differenceViewTemp);
        differenceViewTemp.setVisibility(View.INVISIBLE);


        //back button to return to main activity
        Button globalStatsButton = (Button) findViewById(R.id.globalStatsButton);


        globalStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GlobalStats.class);
                startActivity(myIntent);
            }
        });
    }



    public void searchButton(View v){

        //reset values
        currentTemp = 0;
        averageTemp = 0;
        differenceTemp = 0;



        currentInput = (EditText)findViewById(R.id.enterText);
        String currentCity = currentInput.getText().toString();

        //The reason for this is so you can replace all of the spaces
        // it has to be a new string becuase they are immutable
        String currentCity2 = currentCity.replaceAll(" ", "%20");

        //initialize response views to prepare for response
        responseView = (TextView)findViewById(R.id.responseView);
        responseViewAlmanac = (TextView)findViewById(R.id.responseViewAlmanac);
        differenceViewTemp = (TextView)findViewById(R.id.differenceViewTemp);

        differenceViewTemp.setVisibility(View.INVISIBLE);

        cityViewRequest = (TextView)findViewById(R.id.cityViewRequest);

        //get complete city name using autocomplete api
        cityRequest myCityRequest = new cityRequest(cityViewRequest, currentCity2);
        //get current temperature from city



    }

    //new button to click once data has loaded
    public void amIWarmingButton(View v){

        setDifferenceViewTemp();
    }

    //method to check if an async task has completed ---- currently not in use
    private boolean asyncStatusCheck(AsyncTask task){

        if(task.getStatus() == AsyncTask.Status.PENDING){

                return false;

        }

        if(task.getStatus() == AsyncTask.Status.RUNNING){

                return false;

        }

        if(task.getStatus() == AsyncTask.Status.FINISHED){

            return true;
        }
        return false;
    }
    //method to calculate the difference between current and almanac data and set color of diff view
    private void setDifferenceViewTemp(){

            differenceTemp = currentTemp - averageTemp;

            String differenceTempString = String.format("%.2f", differenceTemp);
            differenceViewTemp.setText(differenceTempString + " degrees " + isWarmer(differenceTemp) + " today.");
            differenceViewTemp.setBackgroundColor(Color.parseColor(getColor(differenceTemp)));

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
     * Method that returns a value of a color string and takes a double value
     * @param temp
     * @returns String color
     */
    public static String getColor(Double temp) {
        String color;
        color = "";

        if (temp >= 7.0) {
            color = "#FF0000";
        } else if (temp < 5.0 && temp >= 2.0) {
            color = "#FFA500";
        } else if (temp < 2.0 && temp >= 0.5) {
            color = "#FFFF00";
        } else if (temp < 0.5 && temp >= -0.5) {
            color = "#008000";
        } else if (temp < -0.5 && temp >= -2.0) {
            color = "#0000FF";
        } else if (temp < -2.0) {
            color = "#00FFFF";
        }
        return color;
    }

/**
 * The following three embedded classes are network requests designed to perform three functions:
 * 1 - use weather underground autocomplete API to get a valid city name from user input
 * 2 - get current weather conditions for this city
 * 3 - get historic data for this city
 */


    /**
     * Class that processes the input string and used the wunderground API to
     * return the best match to the search
     */
    class cityRequest extends AsyncTask<String,String,String> {
        String cityCode;
        String cityName;
        private Exception exception;
        TextView cityViewRequest;
        HttpURLConnection urlConnection;

        protected cityRequest(TextView textView,String city){

            cityViewRequest = textView;
            cityName = city;

            super.execute();

        }


        protected String doInBackground(String... args) {


            StringBuilder result = new StringBuilder();
            JSONObject resultObject = null;
            try {

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
                    completeCityName = (values.getJSONObject(0).get("name").toString());
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
            cityViewRequest.setText(response);

            networkRequest one = new networkRequest(responseView, cityCode);
            //get historic temperature from city
            secondNetworkRequest two = new secondNetworkRequest(responseViewAlmanac, cityCode);

        }

    }


    //initiate new thread for network connected api call in order to avoid slowing UI thread.
//this class handles gathering the current temperature, the next class will handle almanac data

    class networkRequest extends AsyncTask<String,String,String> {
        String cityName;
        private Exception exception;
        TextView responseView;
        HttpURLConnection urlConnection;

        protected networkRequest(TextView textView,String city){

            responseView = textView;
            cityName = city;
            super.execute();

        }


        protected String doInBackground(String... args) {


            StringBuilder result = new StringBuilder();
            JSONObject resultObject = null;
            try {
                URL url = new URL("http://api.wunderground.com/api/f1650fb7e0ae610e/conditions/q/zmw:"+ cityName +".json");
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
                //build the output string from the JSON data and the user input city name
                JSONObject values = resultObject.getJSONObject("current_observation");
                StringBuilder output = new StringBuilder();
                output.append("The current temperature in ");
                output.append(completeCityName);
                output.append(" is ");
                output.append(values.getString("temp_f"));
                currentTemp = values.getDouble("temp_f");
                return output.toString();

            }catch (JSONException e){
                return "Sorry, we don't have info for that city yet! Please try again.";
            }
        }

        protected void onProgressUpdate(){
            TextView responseView = (TextView) findViewById(R.id.responseView);
            responseView.setText("fetching current condtions...");

        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            TextView responseView = (TextView) findViewById(R.id.responseView);
            responseView.setText(response);


        }

    }

    //second network request handles almanac data-----------------------------------------------------------------
    class secondNetworkRequest extends AsyncTask<String,String,String> {
        String cityName;
        private Exception exception;
        TextView responseViewAlmanac;
        HttpURLConnection urlConnection;

        protected secondNetworkRequest(TextView textView, String city){

            responseViewAlmanac = textView;
            cityName = city;
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
                StringBuilder output = new StringBuilder();
                output.append("The historic average temperature in ");
                output.append(completeCityName);
                output.append(" for today is ");
                averageTemp = (values.getJSONObject("temp_high").getJSONObject("normal").getDouble("F") + values.getJSONObject("temp_low").getJSONObject("normal").getDouble("F"))/2.0;
                output.append(averageTemp);

                return output.toString();

            }catch (JSONException e){
                return "Sorry, we don't have info for that city yet! Please try again.";
            }

        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            responseViewAlmanac.setText(response);
            differenceViewTemp.setVisibility(View.VISIBLE);
            differenceViewTemp.setBackgroundColor(Color.LTGRAY);
            differenceViewTemp.setText(R.string.warmingButton);

        }

    }


}
