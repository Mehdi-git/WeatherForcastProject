package com.example.weatherforcastproject.feature.search;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.base.BaseApplication;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.repository.Model;
import com.example.weatherforcastproject.utils.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchPresenter extends BaseApplication implements Contract.Presenter {

    private List<WeatherPojo> detailList = new ArrayList<>();
    private Model model;
    private Contract.View view;


    public   SearchPresenter(Contract.View view ) {
        this.view = view;
        model = new Model(view.getContext());
    }

    /**
     * The following method gets city name which user has entered
     * and pass it to the getCityIdByName method to get id of the city
     */
    @Override
    public void onSearchButtonClicked() {
        String searchKey = view.getSearchKey();
        if (searchKey.isEmpty()) {
            view.showToast(R.string.Pleas_enter_a_city_name_first);
        } else {
            getCityIdByName(searchKey);
        }
    }

    /**
     * The following method finds id of the cities
     * @param cityName is name of city which has entered by user
     * after get id of the city will send that to View for make intent
     */
    @Override
    public void getCityIdByName(String cityName) {
        model.getWeatherResponseByName(cityName,Constants.UNIT,Constants.API_KEY).enqueue(new Callback<WeatherPojo>() {
            @Override
            public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {

                if (!(response.body() == null)&&response.body().getCod().equals(200))   {
                    view.makeIntentById("cityId", response.body().getId());
                }
                else view.showToast(R.string.City_Not_Found);
            }
            @Override
            public void onFailure(Call<WeatherPojo> call, Throwable t) {
                view.showToast(R.string.Some_error_Occurred);

            }
        });
    }

    /**
      Firstly this method creates a list of all cities's ID.
      Secondly it calls fetchWeatherInfoAndSaveToList() method for
      each city.
     */
    @Override
    public void makeAdapterReady() {

        List<Integer> historyCityId = model.getListOfCityIds();
        if (!(historyCityId == null)) {
            if((historyCityId.isEmpty())){
                setAdapter();
            }else
            view.showProgressBar();
            for (int id : historyCityId) {
                fetchWeatherInfoAndSaveToList(id);
            }
            view.makeDelayForGetListReady(2000);
        }
    }

    /**
     Following method fetches weather information of the city which id has been given and
     add each one to list for show to the View.
     */
    @Override
    public void fetchWeatherInfoAndSaveToList(int id) {
        model.getWeatherResponseById(id, Constants.UNIT, Constants.API_KEY).enqueue(new Callback<WeatherPojo>() {
            @Override
            public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                if (response.isSuccessful()) {
                   if (!checkDetailListContainTheCity(detailList, response.body().getId())) {
                        detailList.add(response.body());
                   }
                }
            }
            @Override
            public void onFailure(Call<WeatherPojo> call, Throwable t) {
            }
        });
    }


    /**
    Following method checks whether given List has the particular city Weather info or not.
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

    /**
     Following method send list of city's weather to View for show in recyclerView
     */
    @Override
    public void setAdapter() {
        view.hideProgressBar();
        if (!(detailList == null)) {
            view.showRecycler(detailList);
        }

    }

    @Override
    public void onItemClicked(int id){
        view.makeIntentById("cityId", id);
    }

    @Override
    public void onLongItemClicked(int cityId) {
        view.showAlertDialog(cityId);
    }

    /**
     * when user accept to delete city
     * @param cityId is id of the city which user wanted to delete
     */
    @Override
    public void onPositiveButtonClicked(int cityId) {
        model.deleteCityFromDb(cityId);
        detailList.clear();
        makeAdapterReady();
        view.showToast(R.string.City_Deleted);
    }

    @Override
    public void onNegativeButtonClicked() {
        //Do nothing
    }


}



