package com.example.weatherforcastproject.feature.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherforcastproject.R;
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


public class DetailActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtLine;
    TextView txtTemp;
    TextView txtDescription;
    ImageView imgIcon;
    TextView txtTemperature;
    List<String> searchList;
    String searchKey;
    String api = "70224ba51fd21c4b9ff96c4ad7e2288c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DetailPresenter presenter = new DetailPresenter();

        txtName=findViewById(R.id.txtName);
        txtTemp=findViewById(R.id.txtTemp);
        txtLine=findViewById(R.id.txtLine);
        imgIcon=findViewById(R.id.imgIcon);
        txtDescription=findViewById(R.id.txtDescription);
        txtTemperature= findViewById(R.id.txtTemperature);
        txtLine.setVisibility(View.INVISIBLE);


        Intent i = getIntent();
        searchKey= i.getStringExtra("name");

        fetchAndSetDataForWeather(searchKey,api);
        fetchAndSetDataForForecast(searchKey,api);

    }

    private void fetchAndSetDataForWeather (String searchKey, String api){

        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                         + searchKey + "&units=metric&APPID=" + api;
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Gson gson = new Gson();
                    WeatherClass weather = gson.fromJson(response.toString(), WeatherClass.class);

                    if(!(weather == null) & (weather.getCod().equals(200))) {
                        txtLine.setVisibility(View.VISIBLE);
                        setWeatherInfoToDetailView(weather);
                        saveDataToDB(weather);
                    } else
                        {
                        Log.d("MyTag","The City Could Not Be Found!!");
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("MyTag","Fail on fetching data by AsyncHttpClient on whether");
                    // Toast.makeText(DetailActivity.this, "Sorry! city not found", Toast.LENGTH_LONG).show();

                    txtName.setText("Sorry, City Not Found!");
                    imgIcon.setImageResource(R.drawable.not_found);
                    imgIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);



                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  fetchAndSetDataForForecast(String searchKey, String api){
        try {
            String url="http://api.openweathermap.org/data/2.5/forecast?q=" + searchKey + "&units=metric&APPID=" + api;
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Gson gson =new Gson();
                    ForcastFive forecast = gson.fromJson(response.toString(),ForcastFive.class);
                    List<com.example.weatherforcastproject.pojo.forcastFive.List> forecastList = forecast.getList();
                    setForecastInfoToDetailView(forecastList);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("MyTag","some error happened during fetch data by AsyncHttpClient on Forecast");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDataToDB (WeatherClass weather){
        final CitySQLiteHelper helper = new CitySQLiteHelper(DetailActivity.this,"Cities",null,1);
        helper.insertCity(weather.getName(),
                weather.getSys().getCountry(),
                weather.getId());
    }

    @SuppressLint("SetTextI18n")
    private void setWeatherInfoToDetailView(WeatherClass weather){
        try {
            long temp = Math.round(weather.getMain().getTemp());
            long tempMin = Math.round(weather.getMain().getTempMin());
            long tempMax = Math.round(weather.getMain().getTempMax());
            txtName.setText(weather.getName() + "," + weather.getSys().getCountry());
            txtTemperature.setText(String.valueOf(temp) + Html.fromHtml("&#8451;"));
            txtTemp.setText("Min: " + tempMin + Html.fromHtml("&#8451;") + "   " + "Max: " + String.valueOf(tempMax) + Html.fromHtml("&#8451;"));
            txtDescription.setText(weather.getWeather().get(0).getDescription());
            setImgIcon(imgIcon,weather.getWeather().get(0));
//                        Glide.with(DetailActivity.this)
//                                .load("http://openweathermap.org/img/wn/" + weather.getWeather().get(0).getIcon() + "@2x.png")
//                                .centerCrop()
//                                .into(imgIcon);
        }catch (Exception ex){
            Log.d("MyTag","Some error during set data to Detail Activity Views");
        }
    }

    private void setForecastInfoToDetailView(List<com.example.weatherforcastproject.pojo.forcastFive.List> forecastList){
        RecyclerView recycler = findViewById(R.id.recyclerMain);

        Adapter adapter = new Adapter(forecastList);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(DetailActivity.this,RecyclerView.HORIZONTAL,false));
    }

    private void setImgIcon(ImageView imgIcon , Weather list) {
        this.imgIcon = imgIcon;
        if(!(list.getIcon().isEmpty())&& !(list.getIcon()==null)){
            String icon =list.getIcon();
            switch (icon) {
                case "01d":
                    imgIcon.setImageResource(R.drawable.clear_sky_day_1);
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
