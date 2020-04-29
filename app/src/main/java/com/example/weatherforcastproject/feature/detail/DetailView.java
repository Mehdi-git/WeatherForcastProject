package com.example.weatherforcastproject.feature.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.pojo.forecast.List;
import com.example.weatherforcastproject.pojo.weather.Weather;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import org.jetbrains.annotations.NotNull;


public class DetailView extends AppCompatActivity implements Contract.View {
    TextView txtName,txtLine,txtTemp,txtDegree,txtDescription,txtTemperature;
    ImageView imgIcon;
    private Weather list;
    String searchKey;

    private Contract.Presenter presenter = new DetailPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTemperature= findViewById(R.id.txtTemperature);
        txtDescription=findViewById(R.id.txtDescription);
        txtDegree = findViewById(R.id.txtDegree);
        txtName = findViewById(R.id.txtName);
        txtTemp = findViewById(R.id.txtTemp);
        txtLine = findViewById(R.id.txtLine);
        imgIcon = findViewById(R.id.imgIcon);
        txtLine.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        searchKey= i.getStringExtra("name");

        presenter.getDataForWeather(searchKey);
        presenter.getDataForForecast(searchKey);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showWeatherInfo(@NotNull WeatherPojo weather) {
        txtLine.setVisibility(View.VISIBLE);
        long temp = Math.round(weather.getMain().getTemp());
        long tempMin = Math.round(weather.getMain().getTempMin());
        long tempMax = Math.round(weather.getMain().getTempMax());
        txtName.setText(weather.getName() + "," + weather.getSys().getCountry());
        txtTemperature.setText(String.valueOf(temp));
        txtDegree.setText(Html.fromHtml("&#8451"));
        txtTemp.setText("Min: " + tempMin + Html.fromHtml("&#8451;") + "   " + "Max: " + String.valueOf(tempMax) + Html.fromHtml("&#8451;"));
        txtDescription.setText(weather.getWeather().get(0).getDescription());
        setImgIcon(imgIcon,weather.getWeather().get(0));

    }

    @Override
    public void showForecastInfo(java.util.List<List> forecastInfo) {
        RecyclerView recycler = findViewById(R.id.recyclerMain);
        AdapterDetail adapterDetail = new AdapterDetail(forecastInfo);
        recycler.setAdapter(adapterDetail);
        recycler.setLayoutManager(new LinearLayoutManager(DetailView.this,RecyclerView.HORIZONTAL,false));
    }

    @Override
    public void showFailed() {
        txtName.setText("Sorry, City Not Found!");
        imgIcon.setImageResource(R.drawable.not_found);
        imgIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    /**
     * The following method set desire weather icon image into imageView
     */
    private void setImgIcon(ImageView imgIcon , @NotNull Weather list) {
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







