package com.example.test_api;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAPIGetter myAPIGetter = new MyAPIGetter();
        String result;

        try {
         result = myAPIGetter.execute("http://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&appid=e5f8503d7afa0137063de8152277025d").get();
         Log.i("Result", result);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public class MyAPIGetter extends AsyncTask<String, Void, String>{

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        @Override
        protected String doInBackground(String... urls) {

            try{
                url =  new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    data = reader.read();
                    char current = (char) data;
                    result += current;
                }

                return result;


            }catch (Exception e){
                e.printStackTrace();
                return "failed";
            }

        }
    }
}