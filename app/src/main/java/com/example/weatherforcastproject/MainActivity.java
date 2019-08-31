package com.example.weatherforcastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherforcastproject.forecast.WeatherClass;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtTemp;
    TextView txtDescription;
    ImageView imgIcon;
    TextView txtTemperature;




    String searchKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName=findViewById(R.id.txtName);
        txtTemp=findViewById(R.id.txtTemp);
        txtDescription=findViewById(R.id.txtDescription);
        imgIcon=findViewById(R.id.imgIcon);
        txtTemperature= findViewById(R.id.txtTemperature);





        Intent i = getIntent();
        searchKey= i.getStringExtra("name");



        try {
            String url= "http://api.openweathermap.org/data/2.5/weather?q="+searchKey+"&units=metric&APPID=70224ba51fd21c4b9ff96c4ad7e2288c";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Gson gson = new Gson();
                    WeatherClass w = gson.fromJson(response.toString(), WeatherClass.class);
                    long temp = Math.round(w.getMain().getTemp());
                    long tempMin = Math.round(w.getMain().getTempMin());
                    long tempMax = Math.round(w.getMain().getTempMax());
                    txtName.setText(w.getName() + "," + w.getSys().getCountry());
                    txtTemperature.setText(String.valueOf(temp) + Html.fromHtml("&#8451;"));
                    txtTemp.setText("Min: " + String.valueOf(tempMin) + Html.fromHtml("&#8451;") + "   " + "Max: " + String.valueOf(tempMax) + Html.fromHtml("&#8451;"));
                    txtDescription.setText(w.getWeather().get(0).getDescription());

                    Glide.with(MainActivity.this)
                            .load("https://openweathermap.org/img/wn/" + w.getWeather().get(0).getIcon() + "@2x.png")
                            .centerCrop()
                            .into(imgIcon);

                }





                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    //  txtTest.setText("fail");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String url="https://api.openweathermap.org/data/2.5/forecast?q="+searchKey+"&units=metric&APPID=19dc69cdc6b7304758697de9e2fa6e25";


            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url,new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    RecyclerView recycler = findViewById(R.id.recyclerMain);

                    Gson g =new Gson();
                    ForcastFive  forecast = g.fromJson(response.toString(),ForcastFive.class);


                      List<com.example.weatherforcastproject.List> forecastList = forecast.getList();
                      Adapter adapter = new Adapter(forecastList);
                      recycler.setAdapter(adapter);
                      recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("m","some error happend");


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
