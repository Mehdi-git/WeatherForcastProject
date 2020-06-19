package com.example.weatherforcastproject.feature.search;

import android.content.Context;

import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import java.util.Collection;
import java.util.List;



public interface Contract {

    interface View {

        Context getContext();
        String getSearchKey ();
        void showRecycler(List<WeatherPojo> detailList);
        void makeIntentById(String key,int id );
        void showToast (int id);
        void showProgressBar();
        void hideProgressBar();
        void makeDelayForGetListReady(int milliSecond);
        void showAlertDialog(int cityId);

    }

    interface Presenter {

        void onSearchButtonClicked();
        void getCityIdByName(String cityName);
        void makeAdapterReady ();
        void fetchWeatherInfoAndSaveToList(int id );
        boolean checkDetailListContainTheCity(Collection<WeatherPojo> c, int cityId);
        void setAdapter();
        void onItemClicked(int id);
        void onLongItemClicked(int cityId);
        void onPositiveButtonClicked(int cityId);
        void onNegativeButtonClicked();

    }


}
