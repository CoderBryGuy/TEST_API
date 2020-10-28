package com.example.test_api;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAPIGetter myAPIGetter = new MyAPIGetter();
        String result;

            System.out.println("execute()");

            //need to add my api key to use this url
            myAPIGetter.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=");
//         Log.i("Result", result);



    }



    public class MyAPIGetter extends AsyncTask<String, Void, String>{

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        @Override
        protected String doInBackground(String... urls) {

            try{
                System.out.println("doInBackground()");
                url =  new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }


                return result;


            }catch (Exception e){
                e.printStackTrace();
                return "failed";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("JSON", s);

            System.out.println("onPostExecute()");

            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                Log.i("weather info", weatherInfo);
                JSONArray array = new JSONArray(weatherInfo);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonPart = array.getJSONObject(i);
                    Log.i("main", jsonPart.getString("main"));
                    Log.i("description", jsonPart.getString("description"));
                }
                
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}