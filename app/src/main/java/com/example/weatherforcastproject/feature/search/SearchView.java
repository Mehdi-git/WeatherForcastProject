package com.example.weatherforcastproject.feature.search;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.databinding.ActivitySearchBinding;
import com.example.weatherforcastproject.di.DaggerSearchComponent;
import com.example.weatherforcastproject.di.SearchModule;
import com.example.weatherforcastproject.feature.detail.DetailView;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.utils.ShakeDetector;
import java.util.List;
import javax.inject.Inject;

public class SearchView  extends AppCompatActivity implements Contract.View, ClickListener {

    private ActivitySearchBinding binding;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    //

    @Inject
    Contract.Presenter presenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DaggerSearchComponent.builder()
                .searchModule(new SearchModule(this))
                .build().injectSearchView(this);


        binding.btnSearchCity.setOnClickListener(v ->
                presenter.onSearchButtonClicked());

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(count -> {
            /**
             * The following method you would use
             * to setup whatever you want done once the
             * device has been shaken.
             */
            presenter.makeAdapterReady();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.makeAdapterReady();
    }

    @Override
    public String getSearchKey() {
        return binding.edtSearch.getText().toString();
    }

    /**
     * Shift to detail activity for show present and forecast weather of the city
     * @param key is intent key
     * @param id is city's id in openWeatherMap server
     */
    @Override
    public void makeIntentById(String key, int id) {
        Intent intent = new Intent(SearchView.this, DetailView.class);
        intent.putExtra(key, id);
        startActivity(intent);
    }

    @Override
    public void showRecycler(List<WeatherPojo> detailList) {
        AdapterSearch adapter = new AdapterSearch(detailList, this);
        binding.RecycleCityList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.RecycleCityList.setAdapter(adapter);
    }

    /**
     * aks user whether want to delete the city or not
     * @param cityId is id of the city which user long press on it
     */
    @Override
    public void showAlertDialog(int cityId) {
        AlertDialog dialog = new  AlertDialog.Builder(SearchView.this).create();
        dialog.setMessage(getString(R.string.Do_you_want_to_delete_this_city));
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), (dialog12, which) -> {
            presenter.onPositiveButtonClicked(cityId);
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.No), (dialog1, which) -> {
            //Do nothing
        });
        dialog.show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showProgressBar() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progress.setVisibility(View.INVISIBLE);
    }

    /**
     * following method makes delay for item of recyclerView get complete
     * and then will ask presenter for set the list to adapter
     * @param milliSecond is delay length
     */
    @Override
    public void makeDelayForGetListReady(int milliSecond) {
        new Handler().postDelayed(() -> {
            presenter.setAdapter();
        }, milliSecond);
    }


    @Override
    public void showToast(int id) {
        Toast.makeText(SearchView.this, getContext().getString(id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int cityId) {
        presenter.onItemClicked(cityId);
    }

    @Override
    public void onItemLongClick(int cityId) {
        presenter.onLongItemClicked(cityId);


    }
}






