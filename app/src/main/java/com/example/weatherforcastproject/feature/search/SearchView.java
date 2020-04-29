package com.example.weatherforcastproject.feature.search;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.feature.detail.DetailView;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import com.example.weatherforcastproject.utils.ShakeDetector;
import java.util.List;

import javax.xml.transform.Result;

public class SearchView extends AppCompatActivity implements Contract.View, ClickListener {

    private Contract.Presenter presenter = new SearchPresenter(this);

    EditText edtSearch;
    Button btnSearchCity;
    AdapterSearch adapter;
    ProgressBar progressBar;




    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        btnSearchCity = findViewById(R.id.btnSearchCity);
        progressBar = findViewById(R.id.progress);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                /**
                 * The following method you would use
                 * to setup whatever you want done once the
                 * device has been shook.
                 */
                presenter.makeAdapterReady();
            }
        });
        //

        btnSearchCity.setOnClickListener(v ->
                presenter.onSearchButtonClicked());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.makeAdapterReady();
    }

    @Override
    public String getSearchKey() {
        return edtSearch.getText().toString();
    }

    @Override
    public void makeIntent(String key, String searchKey) {
        Intent intent = new Intent(SearchView.this, DetailView.class);
        intent.putExtra(key, searchKey);
        startActivity(intent);
    }

    @Override
    public void showAdapter(List<WeatherPojo> detailList) {
        RecyclerView recyclerView = findViewById(R.id.RecycleCityList);
        adapter = new AdapterSearch(detailList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showAlertDialog(int cityId) {
        AlertDialog dialog = new  AlertDialog.Builder(SearchView.this).create();
        dialog.setMessage("Do you want to delete the city?");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "yes", (dialog12, which) -> {
            presenter.onPositiveButtonClicked(cityId);
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog1, which) -> {
            //Do nothing
        });
        dialog.show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void makeDelay(int milliSecond) {
        new Handler().postDelayed(() -> {
            presenter.setAdapter();
        }, milliSecond);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(SearchView.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(String cityName) {
        presenter.onItemClicked(cityName);
    }

    @Override
    public void onItemLongClick(int cityId) {
        presenter.onLongItemClicked(cityId);

    }
}






