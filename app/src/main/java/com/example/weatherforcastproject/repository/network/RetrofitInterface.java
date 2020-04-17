package com.example.weatherforcastproject.repository.network;

import com.example.weatherforcastproject.pojo.weatherPojo.WeatherPojo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
// http://api.openweathermap.org/data/2.5/weather?id=112931&units=metric&APPID=70224ba51fd21c4b9ff96c4ad7e2288c

public interface RetrofitInterface {
    @GET("weather")
    Call<WeatherPojo> listWeather(@Query ("id") int id,
                                  @Query("units") String unit,
                                  @Query ("APPID") String APPID);


}
