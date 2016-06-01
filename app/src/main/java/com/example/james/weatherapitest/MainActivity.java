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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView responseView;
    EditText currentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void searchButton(View v){

        currentInput = (EditText)findViewById(R.id.enterText);
        String currentCity = currentInput.getText().toString();
        responseView = (TextView)findViewById(R.id.responseView);
        //pass in the city name to the network request -- plan to get this later from the user location
        networkRequest one = new networkRequest(responseView, currentCity);
        responseView.setText(one.toString());
    }
}

//initiate new thread for network connected api call in order to avoid slowing UI thread.
 class networkRequest extends AsyncTask<String,String,String> {
    String cityName;
    private Exception exception;
     TextView responseView;
     HttpURLConnection urlConnection;

    protected networkRequest(TextView textView, String city){
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
                JSONObject values = resultObject.getJSONObject("current_observation");
                StringBuilder output = new StringBuilder();
                output.append("The current temperature in ");
                output.append(cityName);
                output.append(" is ");
                output.append(values.getString("temperature_string"));
             return output.toString();

         }catch (JSONException e){
             return "Sorry, we don't have info for that city yet! Please try again.";
         }
     }

    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        responseView.setText(response);



    }

    protected String jsonParser(String json){
        JSONObject fullResponse;
        try {
           fullResponse  = new JSONObject(json);
           return fullResponse.getString("'temperature_string'");

        }catch (JSONException e){
            return "bad data, JSONException";
        }


    }

}