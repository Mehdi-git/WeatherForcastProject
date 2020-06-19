package com.example.weatherforcastproject.feature.detail;

import android.util.Log;

import com.example.weatherforcastproject.base.BaseApplication;
import com.example.weatherforcastproject.pojo.forecast.ForecastFiveDays;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.repository.Model;
import com.example.weatherforcastproject.utils.Constants;
import org.jetbrains.annotations.NotNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailPresenter extends BaseApplication implements Contract.Presenter {

    private Contract.View view;
    private Model model;

    public DetailPresenter( Contract.View view) {
        this.view = view;
        model = new Model(view.getContext());
    }

    /**
     * The following method fetches current weather information of searchKey keyword(city name)
     * by model and if response is successful, pass data to view to show and save city data to db.
     * In the event of failed cases calls view's ShowFailed method.
     */
    public void getWeatherDataByName(String searchKey){
        model.getWeatherResponseByName(searchKey, Constants.UNIT, Constants.API_KEY).enqueue(new Callback<WeatherPojo>() {
        @Override
        public void onResponse(Call<WeatherPojo> call, @NotNull Response<WeatherPojo> response) {

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
    public void getForecastDataByName(String searchKey){
        model.getForecastResponseByName(searchKey, Constants.UNIT, Constants.API_KEY).enqueue(new Callback<ForecastFiveDays>() {
            @Override
            public void onResponse(Call<ForecastFiveDays> call, Response<ForecastFiveDays> response) {

                if( (response.isSuccessful())) {
                    if (response.body() != null && (response.body().getCod().equals("200"))) {
                        view.showForecastInfo(response.body().getList());
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastFiveDays> call, Throwable t) {

            }
        });
    }

    @Override
    public void getWeatherDataById(int id) {

        model.getWeatherResponseById(id, Constants.UNIT, Constants.API_KEY).enqueue(new Callback<WeatherPojo>() {
            @Override
            public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                if( (response.isSuccessful())) {
                    if (response.body() != null && (response.body()).getCod().equals(200)) {
                        view.showWeatherInfo(response.body());
                        saveDataToDB(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<WeatherPojo> call, Throwable t) {
                Log.d("MyTag","Some error during fetch data by id");
            }
        });
    }


    @Override
    public void getForecastDataById(int id) {
        model.getForecastResponseById(id, Constants.UNIT, Constants.API_KEY).enqueue(new Callback<ForecastFiveDays>() {
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








