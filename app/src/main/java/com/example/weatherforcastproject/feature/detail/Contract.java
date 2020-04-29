package com.example.weatherforcastproject.feature.detail;
import com.example.weatherforcastproject.pojo.forecast.List;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;



public interface Contract {

    interface View {
        void showWeatherInfo(WeatherPojo weather);
        void showForecastInfo(java.util.List<List> forecastInfo);
        void showFailed();


    }

    interface Presenter{
        void getDataForWeather (String searchKey);
        void getDataForForecast(String searchKey);
        void saveDataToDB (WeatherPojo weather);

    }
}
