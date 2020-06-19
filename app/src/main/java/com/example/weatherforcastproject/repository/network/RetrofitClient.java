package com.example.weatherforcastproject.repository.network;

import com.example.weatherforcastproject.pojo.forecast.ForecastFiveDays;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.utils.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static Retrofit retrofit;
    private static RetrofitClient instance = null;

    // make single instance of RetrofitClient in hole project
    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static RetrofitInterface getWeather() {
            //instance of retrofit with base URL
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL_OWM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            //instance of retrofit with implementation of interface
        return retrofit.create(RetrofitInterface.class);
    }


    public Call<WeatherPojo> getWeatherResponseById (int id, String unit, String api) {
        return getWeather().getWeatherById(id,unit,api);
    }

    public Call<ForecastFiveDays> getForecastResponseByName (String cityName, String unit, String api){
        return  getWeather().getForecastByName(cityName, unit, api);
    }

    public Call<WeatherPojo> getWeatherResponseByName (String cityName, String unit, String apiKey){
        return  getWeather().getWeatherByName(cityName, unit, apiKey);

    }
    public Call<ForecastFiveDays> getForecastResponseById (int id, String unit, String api) {
        return getWeather().getForecastById(id,unit,api);
    }



}
