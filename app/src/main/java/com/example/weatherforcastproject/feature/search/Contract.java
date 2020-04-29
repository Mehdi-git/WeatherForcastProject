package com.example.weatherforcastproject.feature.search;

import com.example.weatherforcastproject.pojo.weather.WeatherPojo;

import java.util.Collection;
import java.util.List;

public interface Contract {

    interface View {


        void showAdapter(List<WeatherPojo> detailList);
        void makeIntent (String key, String searchKey);
        void showToast (String text);
        void showProgressBar();
        void hideProgressBar();
        void makeDelay(int milliSecond);
        void showAlertDialog(int cityId);
        String getSearchKey ();


    }

    interface Presenter {

        void makeAdapterReady ();
        void fetchWeatherInfoAndSaveToList(int id );
        boolean checkDetailListContainTheCity(Collection<WeatherPojo> c, int cityId);
        void onSearchButtonClicked();
        void setAdapter();
        void onItemClicked(String city);
        void onLongItemClicked(int cityId);
        void onPositiveButtonClicked(int cityId);


    }


}
