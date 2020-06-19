package com.example.weatherforcastproject.feature.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.di.DaggerDetailComponent;
import com.example.weatherforcastproject.di.DetailModule;
import com.example.weatherforcastproject.pojo.forecast.List;
import com.example.weatherforcastproject.pojo.weather.Weather;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import org.jetbrains.annotations.NotNull;
import java.text.MessageFormat;
import javax.inject.Inject;
import com.example.weatherforcastproject.databinding.ActivityDetailBinding;
import static android.text.Html.*;


public class DetailView extends AppCompatActivity implements Contract.View {


    private ActivityDetailBinding binding;

    @Inject
    Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DaggerDetailComponent.builder()
                .detailModule(new DetailModule(this))
                .build().injectDetailView(this);

        binding.txtLine.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        int cityId = intent.getIntExtra("cityId",0);

        if(!(cityId == 0)) {
            presenter.getWeatherDataById(cityId);
            presenter.getForecastDataById(cityId);

        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showWeatherInfo(@NotNull WeatherPojo weather) {

        long temp = Math.round(weather.getMain().getTemp());
        long tempMin = Math.round(weather.getMain().getTempMin());
        long tempMax = Math.round(weather.getMain().getTempMax());
        long realFeel = Math.round(weather.getMain().getFeelsLike());

        setImgIcon(binding.imgIcon,weather.getWeather().get(0));
        binding.txtLine.setVisibility(View.VISIBLE);
        binding.txtName.setText(String.format("%s,%s", weather.getName(), weather.getSys().getCountry()));
        binding.txtTemperature.setText(String.valueOf(temp));
        binding.txtDegree.setText(fromHtml("&#8451"));
        binding.txtTemp.setText(MessageFormat.format("Min: {0}{1}   Max: {2}{3}", tempMin, fromHtml("&#8451;"), tempMax, fromHtml("&#8451;")));
        binding.txtDescription.setText(weather.getWeather().get(0).getDescription());
        binding.txtRealFeel.setText( "RealFeel:" + realFeel);

    }

    @Override
    public void showForecastInfo(java.util.List<List> forecastInfo) {
        RecyclerView recycler = binding.recyclerMain;
        AdapterDetail adapterDetail = new AdapterDetail(forecastInfo);
        recycler.setAdapter(adapterDetail);
        recycler.setLayoutManager(new LinearLayoutManager(DetailView.this,RecyclerView.HORIZONTAL,false));
    }

    @Override
    public void showFailed() {
        binding.txtName.setText(R.string.City_Not_Found);
        binding.imgIcon.setImageResource(R.drawable.not_found);
        binding.imgIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    /**
     * The following method set desire weather icon image into imageView
     */
    private void setImgIcon(ImageView imgIcon , @NotNull Weather list) {

        if(!(list.getIcon().isEmpty())&& !(list.getIcon()==null)){
            String icon =list.getIcon();
            switch (icon) {
                case "01d":
                    binding.imgIcon.setImageResource(R.drawable.clear_sky_day_new);
                    break;
                case "01n":
                    binding.imgIcon.setImageResource(R.drawable.clear_sky_night_new);
                    break;
                case "02d":
                    binding.imgIcon.setImageResource(R.drawable.few_clouds_day);
                    break;
                case "02n":
                    binding.imgIcon.setImageResource(R.drawable.few_clouds_night);
                    break;
                case "03d":
                case "03n":
                    binding.imgIcon.setImageResource(R.drawable.scattered_clouds);
                    break;
                case "04d":
                    binding.imgIcon.setImageResource(R.drawable.broken_clouds_day);
                    break;
                case "04n":
                    binding.imgIcon.setImageResource(R.drawable.broken_clouds_night);
                    break;
                case "09d":
                case "09n":
                    binding.imgIcon.setImageResource(R.drawable.shower_rain);
                    break;
                case "10d":
                    binding.imgIcon.setImageResource(R.drawable.rain_day);
                    break;
                case "10n":
                    binding.imgIcon.setImageResource(R.drawable.rain_night);
                    break;
                case "11d":
                    binding.imgIcon.setImageResource(R.drawable.thunderstorm_day);
                    break;
                case "11n":
                    binding.imgIcon.setImageResource(R.drawable.thunderstorm_night);
                    break;
                case "13d":
                    binding.imgIcon.setImageResource(R.drawable.snow_day);
                    break;
                case "13n":
                    binding.imgIcon.setImageResource(R.drawable.snow_night);
                    break;
                case "50d":
                    binding.imgIcon.setImageResource(R.drawable.mist_day);
                    break;
                case "50n":
                    binding.imgIcon.setImageResource(R.drawable.mist_night);
                    break;
            }
        }
    }
}







