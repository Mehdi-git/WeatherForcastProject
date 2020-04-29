package com.example.weatherforcastproject.repository;

import android.content.Context;

import com.example.weatherforcastproject.pojo.forecast.ForecastFiveDays;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.repository.db.CitySQLiteHelper;
import com.example.weatherforcastproject.repository.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;

public class Model {

     private RetrofitClient retrofit;
     private CitySQLiteHelper helper;
     Context context;

     public Model(Context context){
         this.context = context;
         this.helper = CitySQLiteHelper.getInstance(context);
         retrofit = RetrofitClient.getInstance();
     }

     public Call<WeatherPojo> getWeatherResponseById (int id, String unit, String api){
         return retrofit.getWeatherResponseById(id,unit,api);
     }

     public Call<WeatherPojo> getWeatherResponseByName (String cityName , String unit, String apiKey) {
         return retrofit.getWeatherResponseByName(cityName, unit, apiKey);
     }

     public Call<ForecastFiveDays> getForecastResponseByName (String cityName , String unit, String api) {
         return retrofit.getForecastResponseByName(cityName, unit, api);
     }

     public List<Integer> getListOfCityIds (){
          return helper.getListOfCityIds();
     }

     public void insertCityToDb(String city, String country, int cityId){
           helper.insertCity(city, country, cityId);
     }
     public void deleteCityFromDb ( int cityId){
           helper.deleteCity(cityId);
     }


}
