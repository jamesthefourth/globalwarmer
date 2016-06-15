package com.example.james.weatherapitest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    City cityOne;
    City cityTwo;
    City cityThree;
    City cityFour;
    City cityFive;

    TextView cityOneName;
    TextView cityTwoName;
    TextView cityThreeName;
    TextView cityFourName;
    TextView cityFiveName;

    TextView globalStatsHeader;


    /*String[] cityTwoArray = {getString(R.string.cityTwo),getString(R.string.citySeven),getString(R.string.cityTwelve),getString(R.string.citySeventeen),getString(R.string.cityTwentyTwo)};
    String[] cityThreeArray = {getString(R.string.cityThree),getString(R.string.cityEight),getString(R.string.cityThirteen),getString(R.string.cityEighteen),getString(R.string.cityTwentyThree)};
    String[] cityFourArray={getString(R.string.cityFour),getString(R.string.cityNine),getString(R.string.cityFourteen),getString(R.string.cityNineteen),getString(R.string.cityTwentyFour)};
    String[] cityFiveArray = {getString(R.string.cityFive),getString(R.string.cityTen),getString(R.string.cityFifteen),getString(R.string.cityTwenty),getString(R.string.cityTwentyFive)};*/

    int region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_global);

        //access the shared preferences file to get and edit information
        final SharedPreferences sharedPref = getSharedPreferences("region_data", Context.MODE_PRIVATE);
        region = sharedPref.getInt("region_data", 0);

        //initialize button views for displaying colors
        cityOneButton = (Button) findViewById(R.id.cityOneButton);
        cityTwoButton = (Button) findViewById(R.id.cityTwoButton);
        cityThreeButton = (Button) findViewById(R.id.cityThreeButton);
        cityFourButton = (Button) findViewById(R.id.cityFourButton);
        cityFiveButton = (Button) findViewById(R.id.cityFiveButton);
        //hide them until load

        cityOneButton.setVisibility(View.INVISIBLE);
        cityTwoButton.setVisibility(View.INVISIBLE);
        cityThreeButton.setVisibility(View.INVISIBLE);
        cityFourButton.setVisibility(View.INVISIBLE);
        cityFiveButton.setVisibility(View.INVISIBLE);



        //intialize TextViews
        cityOneName = (TextView)findViewById(R.id.cityOneName);
        cityTwoName= (TextView)findViewById(R.id.cityTwoName);
        cityThreeName= (TextView)findViewById(R.id.cityThreeName);
        cityFourName= (TextView)findViewById(R.id.cityFourName);
        cityFiveName= (TextView)findViewById(R.id.cityFiveName);
        globalStatsHeader = (TextView)findViewById(R.id.globalStatsHeader);
        globalStatsHeader.setText("loading...");
        //initialize Cities based on current region
        if(region == 0) {
            cityOne = new City(getString(R.string.cityOne));
            cityTwo = new City(getString(R.string.cityTwo));
            cityThree = new City(getString(R.string.cityThree));
            cityFour = new City(getString(R.string.cityFour));
            cityFive = new City(getString(R.string.cityFive));
            cityRequest cityOneRequest = new cityRequest(cityOneButton, cityOne);
            cityRequest cityTwoRequest = new cityRequest(cityTwoButton, cityTwo);
            cityRequest cityThreeRequest = new cityRequest(cityThreeButton, cityThree);
            cityRequest cityFourRequest = new cityRequest(cityFourButton, cityFour);
            cityRequest cityFiveRequest = new cityRequest(cityFiveButton, cityFive);
        }else if(region == 1){
            cityOne = new City(getString(R.string.citySix));
            cityTwo = new City(getString(R.string.citySeven));
            cityThree = new City(getString(R.string.cityEight));
            cityFour = new City(getString(R.string.cityNine));
            cityFive = new City(getString(R.string.cityTen));
            cityRequest cityOneRequest = new cityRequest(cityOneButton, cityOne);
            cityRequest cityTwoRequest = new cityRequest(cityTwoButton, cityTwo);
            cityRequest cityThreeRequest = new cityRequest(cityThreeButton, cityThree);
            cityRequest cityFourRequest = new cityRequest(cityFourButton, cityFour);
            cityRequest cityFiveRequest = new cityRequest(cityFiveButton, cityFive);

        }else if(region == 2){
            cityOne = new City(getString(R.string.cityEleven));
            cityTwo = new City(getString(R.string.cityTwelve));
            cityThree = new City(getString(R.string.cityThirteen));
            cityFour = new City(getString(R.string.cityFourteen));
            cityFive = new City(getString(R.string.cityFifteen));
            cityRequest cityOneRequest = new cityRequest(cityOneButton, cityOne);
            cityRequest cityTwoRequest = new cityRequest(cityTwoButton, cityTwo);
            cityRequest cityThreeRequest = new cityRequest(cityThreeButton, cityThree);
            cityRequest cityFourRequest = new cityRequest(cityFourButton, cityFour);
            cityRequest cityFiveRequest = new cityRequest(cityFiveButton, cityFive);
        }else if(region == 3){
            cityOne = new City(getString(R.string.citySixteen));
            cityTwo = new City(getString(R.string.citySeventeen));
            cityThree = new City(getString(R.string.cityEighteen));
            cityFour = new City(getString(R.string.cityNineteen));
            cityFive = new City(getString(R.string.cityTwenty));
            cityRequest cityOneRequest = new cityRequest(cityOneButton, cityOne);
            cityRequest cityTwoRequest = new cityRequest(cityTwoButton, cityTwo);
            cityRequest cityThreeRequest = new cityRequest(cityThreeButton, cityThree);
            cityRequest cityFourRequest = new cityRequest(cityFourButton, cityFour);
            cityRequest cityFiveRequest = new cityRequest(cityFiveButton, cityFive);
        }else if (region == 4){
            cityOne = new City(getString(R.string.cityTwentyOne));
            cityTwo = new City(getString(R.string.cityTwentyTwo));
            cityThree = new City(getString(R.string.cityTwentyThree));
            cityFour = new City(getString(R.string.cityTwentyFour));
            cityFive = new City(getString(R.string.cityTwentyFive));
            cityRequest cityOneRequest = new cityRequest(cityOneButton, cityOne);
            cityRequest cityTwoRequest = new cityRequest(cityTwoButton, cityTwo);
            cityRequest cityThreeRequest = new cityRequest(cityThreeButton, cityThree);
            cityRequest cityFourRequest = new cityRequest(cityFourButton, cityFour);
            cityRequest cityFiveRequest = new cityRequest(cityFiveButton, cityFive);
        }




        //back button to return to main activity
        Button backToMain = (Button) findViewById(R.id.backToMain);


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(GlobalStats.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        Button nextRegion = (Button) findViewById(R.id.nextButton);
        nextRegion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //change the region variable by one, circling back if it reaches 5th region (4)
                final SharedPreferences sharedPref = getSharedPreferences("region_data", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPref.edit();
                region = sharedPref.getInt("region_data", 0);
                if(region <4)
                {
                    region++;
                }
                else
                {
                    region = 0;
                }
                editor.putInt("region_data", region);
                editor.apply();
                //restart the activity with the new region info
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    //check if a city name is in the expected city array
    public static boolean useLoop(String[] arr, String targetValue) {
        for(String s: arr){
            if(s.equals(targetValue))
                return true;
        }
        return false;
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

                // african cities
                if(mCity.getName() == getString(R.string.cityOne)){
                    //set the color of the button based on the difference in temperature
                    cityOneButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityOneButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityOneName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityTwo)){
                    //set the color of the button based on the difference in temperature
                    cityTwoButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityTwoButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityTwoName.setText(mCity.getCompleteCityName());
                }
                if(mCity.getName() == getString(R.string.cityThree)){
                    //set the color of the button based on the difference in temperature
                    cityThreeButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityThreeButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityThreeName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityFour)){
                    //set the color of the button based on the difference in temperature
                    cityFourButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFourButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFourName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityFive)){
                    //set the color of the button based on the difference in temperature
                    cityFiveButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFiveButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFiveName.setText(mCity.getCompleteCityName());
                    globalStatsHeader.setText("Today's Stats: Africa");

                }


                //next set - usa

                if(mCity.getName() == getString(R.string.citySix)){
                    //set the color of the button based on the difference in temperature
                    cityOneButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityOneButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityOneName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.citySeven)){
                    //set the color of the button based on the difference in temperature
                    cityTwoButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityTwoButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityTwoName.setText(mCity.getCompleteCityName());
                }
                if(mCity.getName() == getString(R.string.cityEight)){
                    //set the color of the button based on the difference in temperature
                    cityThreeButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityThreeButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityThreeName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityNine)){
                    //set the color of the button based on the difference in temperature
                    cityFourButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFourButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFourName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityTen)){
                    //set the color of the button based on the difference in temperature
                    cityFiveButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFiveButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFiveName.setText(mCity.getCompleteCityName());
                    globalStatsHeader.setText("Today's Stats: USA");

                }

                //next set - asia

                if(mCity.getName() == getString(R.string.cityEleven)){
                    //set the color of the button based on the difference in temperature
                    cityOneButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityOneButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityOneName.setText(mCity.getCompleteCityName());
                    globalStatsHeader.setText("Today's Stats: Asia");

                }
                if(mCity.getName() == getString(R.string.cityTwelve)){
                    //set the color of the button based on the difference in temperature
                    cityTwoButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityTwoButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityTwoName.setText(mCity.getCompleteCityName());
                }
                if(mCity.getName() == getString(R.string.cityThirteen)){
                    //set the color of the button based on the difference in temperature
                    cityThreeButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityThreeButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityThreeName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityFourteen)){
                    //set the color of the button based on the difference in temperature
                    cityFourButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFourButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFourName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityFifteen)){
                    //set the color of the button based on the difference in temperature
                    cityFiveButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFiveButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFiveName.setText(mCity.getCompleteCityName());

                }

                //next set - europe

                if(mCity.getName() == getString(R.string.citySixteen)){
                    //set the color of the button based on the difference in temperature
                    cityOneButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityOneButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityOneName.setText(mCity.getCompleteCityName());
                    globalStatsHeader.setText("Today's Stats: Europe");

                }
                if(mCity.getName() == getString(R.string.citySeventeen)){
                    //set the color of the button based on the difference in temperature
                    cityTwoButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityTwoButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityTwoName.setText(mCity.getCompleteCityName());
                }
                if(mCity.getName() == getString(R.string.cityEighteen)){
                    //set the color of the button based on the difference in temperature
                    cityThreeButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityThreeButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityThreeName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityNineteen)){
                    //set the color of the button based on the difference in temperature
                    cityFourButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFourButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFourName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityTwenty)){
                    //set the color of the button based on the difference in temperature
                    cityFiveButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFiveButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFiveName.setText(mCity.getCompleteCityName());

                }

                //next set - latin america

                if(mCity.getName() == getString(R.string.cityTwentyOne)){
                    //set the color of the button based on the difference in temperature
                    cityOneButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityOneButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityOneName.setText(mCity.getCompleteCityName());
                    globalStatsHeader.setText("Today's Stats: Latin America");


                }
                if(mCity.getName() == getString(R.string.cityTwentyTwo)){
                    //set the color of the button based on the difference in temperature
                    cityTwoButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityTwoButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityTwoName.setText(mCity.getCompleteCityName());
                }
                if(mCity.getName() == getString(R.string.cityTwentyThree)){
                    //set the color of the button based on the difference in temperature
                    cityThreeButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityThreeButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityThreeName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityTwentyFour)){
                    //set the color of the button based on the difference in temperature
                    cityFourButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFourButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFourName.setText(mCity.getCompleteCityName());

                }
                if(mCity.getName() == getString(R.string.cityTwentyFive)){
                    //set the color of the button based on the difference in temperature
                    cityFiveButton.setBackgroundColor(Color.parseColor(MainActivity.getColor(mCity.differenceTemp)));
                    cityFiveButton.setText(differenceTempString + " degrees " + isWarmer(mCity.differenceTemp) + " today.");
                    cityFiveName.setText(mCity.getCompleteCityName());

                }

                cityOneName.setVisibility(View.VISIBLE);
                cityTwoName.setVisibility(View.VISIBLE);
                cityThreeName.setVisibility(View.VISIBLE);
                cityFourName.setVisibility(View.VISIBLE);
                cityFiveName.setVisibility(View.VISIBLE);

                cityOneButton.setVisibility(View.VISIBLE);
                cityTwoButton.setVisibility(View.VISIBLE);
                cityThreeButton.setVisibility(View.VISIBLE);
                cityFourButton.setVisibility(View.VISIBLE);
                cityFiveButton.setVisibility(View.VISIBLE);





            }

        }

    }



}

