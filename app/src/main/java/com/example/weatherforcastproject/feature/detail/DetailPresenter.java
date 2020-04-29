package com.example.weatherforcastproject.feature.detail;

import android.content.Context;

import androidx.annotation.NonNull;
import com.example.weatherforcastproject.pojo.forecast.ForecastFiveDays;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.repository.Model;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements Contract.Presenter {

    private String apiKey = "70224ba51fd21c4b9ff96c4ad7e2288c";
    private String unit = "metric";
    private Contract.View view;
    private Model model;

    public DetailPresenter(@NonNull Contract.View view) {
        this.view = view;
        model = new Model((Context) view);
    }

    /**
     * The following method fetches current weather information of searchKey keyword(city name)
     * by model and if response is successful, pass data to view to show and save city data to db.
     * In the event of failed cases calls view's ShowFailed method.
     */
    public void getDataForWeather(String searchKey){
        model.getWeatherResponseByName(searchKey, unit, apiKey).enqueue(new Callback<WeatherPojo>() {
            @Override
            public void onResponse(@NonNull Call<WeatherPojo> call, @NotNull Response<WeatherPojo> response) {

                if ((response.body() != null) && response.body().getCod().equals(200)) {
                    view.showWeatherInfo(response.body());
                    saveDataToDB(response.body());
                } else {
                    view.showFailed();
                }
            }
            @Override
            public void onFailure(Call<WeatherPojo> call, Throwable t) {
            }
        });
    }

    /**
     * The following method fetches Five days Forecast weather information
     * of searchKey keyword(city name)by model and if response is successful,
     * pass data to view to show in recycler view.
     */
    public void getDataForForecast(String searchKey){
        model.getForecastResponseByName(searchKey, unit ,apiKey).enqueue(new Callback<ForecastFiveDays>() {
            @Override
            public void onResponse(Call<ForecastFiveDays> call, Response<ForecastFiveDays> response) {

                if( (response.isSuccessful())) {
                    if (response.body() != null && (response.body()).getCod().equals("200")) {
                        view.showForecastInfo(response.body().getList());
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastFiveDays> call, Throwable t) {

            }
        });
    }

    /**
     * The following method store city data into database.
     */
    public void saveDataToDB(WeatherPojo weather){
        model.insertCityToDb(weather.getName(),
                weather.getSys().getCountry(),
                weather.getId());
    }

}








