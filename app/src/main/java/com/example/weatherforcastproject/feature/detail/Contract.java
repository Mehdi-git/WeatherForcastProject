package com.example.weatherforcastproject.feature.detail;
import android.content.Context;

import com.example.weatherforcastproject.pojo.forecast.List;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;



public interface Contract {

    interface View {
        Context getContext();
        void showWeatherInfo(WeatherPojo weather);
        void showForecastInfo(java.util.List<List> forecastInfo);
        void showFailed();
    }

    interface Presenter{
        void getWeatherDataByName(String searchKey);
        void getForecastDataByName(String searchKey);
        void getWeatherDataById(int id);
        void getForecastDataById(int id);


        void saveDataToDB (WeatherPojo weather);
    }
}
