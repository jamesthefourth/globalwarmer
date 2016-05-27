package com.example.james.weatherapitest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void searchButton(View v){
        responseView = (TextView)findViewById(R.id.responseView);
        networkRequest one = new networkRequest(responseView);
        responseView.setText(one.toString());
    }
}

//initiate new thread for network connected api call in order to avoid slowing UI thread.
 class networkRequest extends AsyncTask<String,String,String> {

    private Exception exception;
     TextView responseView;
     HttpURLConnection urlConnection;

    protected networkRequest(TextView textView){
        super.execute();
        responseView = textView;

    }


     protected String doInBackground(String... args) {

         StringBuilder result = new StringBuilder();
         JSONObject resultObject = null;
         try {
             URL url = new URL("http://api.wunderground.com/api/f1650fb7e0ae610e/conditions/q/CA/San_Francisco.json");
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
             return values.getString("temperature_string");

         }catch (JSONException e){
             return "bad data, JSONException";
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