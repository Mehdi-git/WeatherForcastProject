package com.example.weatherforcastproject.utils;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.base.BaseActivity;
import com.example.weatherforcastproject.databinding.ActivityHomeBinding;
import com.example.weatherforcastproject.feature.search.SearchView;



public class FirstActivity extends BaseActivity  {

    private static final int PERMISSION_REQUEST_CODE = 6697;
    private Boolean isUserClickOnBack = false;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnPollution.setOnClickListener(v -> {
            if(checkGpsPermission()){
                GpsTracker gpsTracker = new GpsTracker(this);
                if(gpsTracker.canGetLocation()){
                      double lat = gpsTracker.getLatitude();
                      double lon = gpsTracker.getLongitude();
                      Toast.makeText(this,
                        "Location is: \n lat: " + lat + " \n lon:" + lon,
                        Toast.LENGTH_LONG).show();
                       Log.d("MyTag","GPS:"+lat+lon);
                }
                else {
                      gpsTracker.showGpsAlertDialog();
                }
            }
            else {
                askPermission();

            }

        });


        binding.btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(FirstActivity.this, SearchView.class);
            startActivity(intent);
        });
    }

     private boolean checkGpsPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE){
            //do your task
        }
        else{
            Toast.makeText(this,"GPS ACCESS Denied!!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(isUserClickOnBack) {
            super.onBackPressed();
        }
        else{
            isUserClickOnBack=true;
            Toast.makeText(this, R.string.Please_Press_Back_Again_For_Exit, Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() ->
                    isUserClickOnBack=false,
                    2500);

        }
    }


}
