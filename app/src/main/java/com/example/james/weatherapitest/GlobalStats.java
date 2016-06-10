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
        cityOneButton = (Button)findViewById(R.id.cityOneButton);
        cityTwoButton = (Button)findViewById(R.id.cityTwoButton);
        cityThreeButton = (Button)findViewById(R.id.cityThreeButton);
        cityFourButton = (Button)findViewById(R.id.cityFourButton);
        cityFiveButton = (Button)findViewById(R.id.cityFiveButton);

        //attempt to open a new async task thread for gathering data for each city
        // city one:
        class networkRequest extends AsyncTask<String,String,String> {
            String cityName;
            private Exception exception;
            TextView responseView;
            HttpURLConnection urlConnection;

            protected networkRequest(TextView textView,String city){
                super.execute();
                responseView = textView;
                cityName = city;

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

                    return output.toString();

                }catch (JSONException e){
                    return "Sorry, we don't have info for that city yet! Please try again.";
                }
            }

            protected void onPostExecute(String response) {
                super.onPostExecute(response);





            }

        }

    }




}

