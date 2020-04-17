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
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.weatherforcastproject.pojo.forcastFive.ForcastFive;
import com.example.weatherforcastproject.pojo.forecast.Weather;
import com.example.weatherforcastproject.pojo.forecast.WeatherClass;
import com.example.weatherforcastproject.repository.db.CitySQLiteHelper;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.LENGTH_LONG;

public class DetailActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtTemp;
    TextView txtDescription;
    ImageView imgIcon;
    TextView txtTemperature;
    List<String> searchList;
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
        final CitySQLiteHelper helper = new CitySQLiteHelper(DetailActivity.this,"Cities",null,1);


        Intent i = getIntent();
        searchKey= i.getStringExtra("name");




        try {
            String url= "https://api.openweathermap.org/data/2.5/weather?q="+searchKey+"&units=metric&APPID=70224ba51fd21c4b9ff96c4ad7e2288c";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    Gson gson = new Gson();
                    WeatherClass w = gson.fromJson(response.toString(), WeatherClass.class);

                    helper.insertCity(w.getName(), w.getSys().getCountry(), w.getId());

                    if(w.getCod()==404) {
                        Toast toast = Toast.makeText(DetailActivity.this, "Sorry! city not found", LENGTH_LONG);
                        toast.show();

                    } else if (w.getCod() == 200) {

                    try {

                    }catch (Exception ex){
                        Log.d("MyTag","Some error during set data to Detail Activity Views");
                    }

                        long temp = Math.round(w.getMain().getTemp());
                        long tempMin = Math.round(w.getMain().getTempMin());
                        long tempMax = Math.round(w.getMain().getTempMax());
                        txtName.setText(w.getName() + "," + w.getSys().getCountry());
                        txtTemperature.setText(String.valueOf(temp) + Html.fromHtml("&#8451;"));
                        txtTemp.setText("Min: " + String.valueOf(tempMin) + Html.fromHtml("&#8451;") + "   " + "Max: " + String.valueOf(tempMax) + Html.fromHtml("&#8451;"));
                        txtDescription.setText(w.getWeather().get(0).getDescription());
                        setImgIcon(imgIcon,w.getWeather().get(0));


//                        Glide.with(DetailActivity.this)
//                                .load("http://openweathermap.org/img/wn/" + w.getWeather().get(0).getIcon() + "@2x.png")
//                                .centerCrop()
//                                .into(imgIcon);
                    }

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
            String url="http://api.openweathermap.org/data/2.5/forecast?q="+searchKey+"&units=metric&APPID=70224ba51fd21c4b9ff96c4ad7e2288c";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url,new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    RecyclerView recycler = findViewById(R.id.recyclerMain);
                    Gson gson =new Gson();
                    ForcastFive forecast = gson.fromJson(response.toString(),ForcastFive.class);
                      List<com.example.weatherforcastproject.pojo.forcastFive.List> forecastList = forecast.getList();
                      Adapter adapter = new Adapter(forecastList);
                      recycler.setAdapter(adapter);
                      recycler.setLayoutManager(new LinearLayoutManager(DetailActivity.this,RecyclerView.HORIZONTAL,false));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("MyTag","some error happened during fetch data by AsyncHttpClient");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    public void setImgIcon(ImageView imgIcon , Weather list) {
        this.imgIcon = imgIcon;
        if(!(list.getIcon().isEmpty())&& !(list.getIcon()==null)){
            String icon =list.getIcon();
            switch (icon) {
                case "01d":
                    imgIcon.setImageResource(R.drawable.clear_sky_day_new);

                    break;
                case "01n":
                    imgIcon.setImageResource(R.drawable.clear_sky_night_new);
                    break;
                case "02d":
                    imgIcon.setImageResource(R.drawable.few_clouds_day);
                    break;
                case "02n":
                    imgIcon.setImageResource(R.drawable.few_clouds_night);
                    break;
                case "03d":
                case "03n":
                    imgIcon.setImageResource(R.drawable.scattered_clouds);
                    break;
                case "04d":
                    imgIcon.setImageResource(R.drawable.broken_clouds_day);
                    break;
                case "04n":
                    imgIcon.setImageResource(R.drawable.broken_clouds_night);
                    break;
                case "09d":
                case "09n":
                    imgIcon.setImageResource(R.drawable.shower_rain);
                    break;
                case "10d":
                    imgIcon.setImageResource(R.drawable.rain_day);
                    break;
                case "10n":
                    imgIcon.setImageResource(R.drawable.rain_night);
                    break;
                case "11d":
                    imgIcon.setImageResource(R.drawable.thunderstorm_day);
                    break;
                case "11n":
                    imgIcon.setImageResource(R.drawable.thunderstorm_night);
                    break;
                case "13d":
                    imgIcon.setImageResource(R.drawable.snow_day);
                    break;
                case "13n":
                    imgIcon.setImageResource(R.drawable.snow_night);
                    break;
                case "50d":
                    imgIcon.setImageResource(R.drawable.mist_day);
                    break;
                case "50n":
                    imgIcon.setImageResource(R.drawable.mist_night);
                    break;
            }


        }

    }


}
