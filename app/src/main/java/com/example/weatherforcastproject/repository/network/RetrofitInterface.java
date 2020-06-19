package com.example.weatherforcastproject.repository.network;

import com.example.weatherforcastproject.pojo.forecast.ForecastFiveDays;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// http://api.openweathermap.org/data/2.5/weather?id=112931&units=metric&APPID=70224ba51fd21c4b9ff96c4ad7e2288c
//http://api.openweathermap.org/data/2.5/forecast?q=tehran&units=metric&APPID=70224ba51fd21c4b9ff96c4ad7e2288c

public interface RetrofitInterface {

    @GET("weather")
    Call<WeatherPojo> getWeatherByName(@Query("q") String cityName,
                                       @Query("units") String unit,
                                       @Query("APPID") String APPID);
    @GET("weather")
    Call<WeatherPojo> getWeatherById(@Query("id") int id,
                                     @Query("units") String unit,
                                     @Query("APPID") String APPID);

    @GET("forecast")
    Call<ForecastFiveDays> getForecastByName(@Query("q") String cityName,
                                             @Query("units") String unit,
                                             @Query("APPID") String APPID);
    @GET("forecast")
    Call<ForecastFiveDays> getForecastById(@Query("id") int id,
                                           @Query("units") String unit,
                                           @Query("APPID") String APPID);






}

