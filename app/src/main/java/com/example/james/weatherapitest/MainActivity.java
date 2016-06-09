package com.example.james.weatherapitest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    EditText currentInput;
    double currentTemp;
    double averageTemp;
    double differenceTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchButton(View v){
    //**************need to add input validation here ****************************
        currentInput = (EditText)findViewById(R.id.enterText);
        String currentCity = currentInput.getText().toString();

        //initialize response views to prepare for response
        responseView = (TextView)findViewById(R.id.responseView);
        responseViewAlmanac = (TextView)findViewById(R.id.responseViewAlmanac);
        differenceViewTemp = (TextView)findViewById(R.id.differenceViewTemp);

        //pass in the city name to the network request -- plan to get this later from the user location
        networkRequest one = new networkRequest(responseView, currentCity);



    }




    //initiate new thread for network connected api call in order to avoid slowing UI thread.
//this class handles gathering the current temperature, the next class will handle almanac data
//*** planning to refactor this code so we don't have huge duplicate methods here...****
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
                URL url = new URL("http://api.wunderground.com/api/f1650fb7e0ae610e/conditions/q/CA/"+ cityName +".json");
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
                output.append(cityName);
                output.append(" is ");
                output.append(values.getString("temp_f"));
                currentTemp = values.getDouble("temp_f");
                return output.toString();

            }catch (JSONException e){
                return "Sorry, we don't have info for that city yet! Please try again.";
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            TextView responseView = (TextView) findViewById(R.id.responseView);
            responseView.setText(response);

            secondNetworkRequest two = new secondNetworkRequest(responseViewAlmanac, cityName);




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
                URL url = new URL("http://api.wunderground.com/api/f1650fb7e0ae610e/almanac/q/CA/"+ cityName +".json");
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
                JSONObject values = resultObject.getJSONObject("almanac");
                StringBuilder output = new StringBuilder();
                output.append("The historic average temperature in ");
                output.append(cityName);
                output.append(" for today is ");
                output.append(values.getJSONObject("temp_high").getJSONObject("normal").getString("F"));
                averageTemp = values.getJSONObject("temp_high").getJSONObject("normal").getDouble("F");
                return output.toString();

            }catch (JSONException e){
                return "Sorry, we don't have info for that city yet! Please try again.";
            }

        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            responseViewAlmanac.setText(response);

            differenceTemp = currentTemp - averageTemp;
            differenceViewTemp.setText(String.valueOf(differenceTemp));
        }

    }
}
