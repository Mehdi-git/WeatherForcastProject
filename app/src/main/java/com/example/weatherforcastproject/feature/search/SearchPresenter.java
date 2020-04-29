package com.example.weatherforcastproject.feature.search;


import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.repository.Model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements Contract.Presenter {

    String apiKey = "70224ba51fd21c4b9ff96c4ad7e2288c";
    String unit = "metric";
    private List<WeatherPojo> detailList = new ArrayList<>();
    private Contract.View view;
    private Model model;

    public SearchPresenter(@NonNull Contract.View view) {
        this.view = view;
        model = new Model((Context) view);
    }
    /*
      Firstly this method creates a list of all cities's ID.
      Secondly it calls fetchWeatherInfoAndSaveToList() method for
      each city.
     */
    @Override
    public void makeAdapterReady() {
        detailList.clear();
        List<Integer> historyCityId = model.getListOfCityIds();
        if (!(historyCityId == null)) {
            if((historyCityId.isEmpty())){
                setAdapter();
            }else
            view.showProgressBar();
            for (int id : historyCityId) {
                fetchWeatherInfoAndSaveToList(id);
            }
            view.makeDelay(2000);
        }
    }

    /**
    This method fetches weather information of the city which id has been given and
    add each one to list for show to the SearchView.
     */
    @Override
    public void fetchWeatherInfoAndSaveToList(int id) {
        model.getWeatherResponseById(id, unit, apiKey)
                .enqueue(new Callback<WeatherPojo>() {
                    @Override
                    public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                        if (response.isSuccessful()) {
                            if (!checkDetailListContainTheCity(detailList, response.body().getId())) {
                                detailList.add(response.body());
                                Log.d("MyTag", "detailList onResponse is :" + detailList.toString());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<WeatherPojo> call, Throwable t) {
                        Log.d("MyTag", "Error while fetching data by retrofit" + t.getMessage());
                    }
                });

    }

    /**
    This method checks whether given List has the particular city Weather info or not.
     */
    @Override
    public boolean checkDetailListContainTheCity(Collection<WeatherPojo> c, int cityId) {
        for (WeatherPojo o : c) {
            if (o != null && o.getId().equals(cityId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSearchButtonClicked() {
       String searchKey = view.getSearchKey();
        if (searchKey.isEmpty()) {
            view.showToast("Pleas enter a city name first!");
        } else {
            view.makeIntent("name",searchKey);
        }
    }

    /**
    This method send list to SearchView
     */
    @Override
    public void setAdapter() {
        if (!(detailList == null)) {
            view.showAdapter(detailList);
        }
        view.hideProgressBar();
    }

    @Override
    public void onItemClicked(String cityName){
        view.makeIntent("name", cityName);
    }

    @Override
    public void onLongItemClicked(int cityId) {
        view.showAlertDialog(cityId);

    }

    @Override
    public void onPositiveButtonClicked(int cityId) {
        model.deleteCityFromDb(cityId);
        makeAdapterReady();
        view.showToast("City Deleted!");


    }


}



