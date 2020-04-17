package com.example.weatherforcastproject.repository.network;

import com.example.weatherforcastproject.pojo.weatherPojo.Weather;
import com.example.weatherforcastproject.pojo.weatherPojo.WeatherPojo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Model {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";


    public static RetrofitInterface getWeatherById() {
            //instance of retrofit with base URL
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            //instance of retrofit with implementation of interface
        return retrofit.create(RetrofitInterface.class);


    }

    public static Call<WeatherPojo> getWeather(int id,String unit,String APIID){
        return getWeatherById().listWeather(id,unit,APIID);
    }


}
