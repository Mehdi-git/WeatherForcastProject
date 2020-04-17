package com.example.weatherforcastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.weatherforcastproject.pojo.weatherPojo.WeatherPojo;
import com.example.weatherforcastproject.repository.db.CitySQLiteHelper;
import com.example.weatherforcastproject.repository.Pair;
import com.example.weatherforcastproject.repository.network.Model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearchCity,btnRefresh;
    TextView txtNew;
    String searchKey;
    List<Pair<String, String>> historyOfSearch;
    List<Integer> historyCityId;
    String api ="70224ba51fd21c4b9ff96c4ad7e2288c";
    List<WeatherPojo> detailList = new ArrayList<>();
    AdapterSearch adapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        btnSearchCity = findViewById(R.id.btnSearchCity);
        btnRefresh = findViewById(R.id.btnRefresh);
        progressBar = findViewById(R.id.progress);

        btnSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = edtSearch.getText().toString();
                if (searchKey.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "Pleas enter a city name first!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(SearchActivity.this, DetailActivity.class);
                    i.putExtra("name", searchKey);
                    startActivity(i);
                }
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRefresh.setVisibility(View.INVISIBLE);
                makeAdapterReady();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeAdapterReady();




    }
    private void makeAdapterReady () {
        final CitySQLiteHelper helper = new CitySQLiteHelper(SearchActivity.this,
                "Cities", null, 1);
        historyCityId = helper.getListOfCityIds();
        Log.d("MyTag"," HistoryCityId after fetching is : "+ historyCityId.toString());
        if (historyCityId != null && !historyCityId.isEmpty()) {
            for (int id : historyCityId) {
                setResponseToList(id);
                progressBar.setVisibility(View.VISIBLE);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    setAdapter();
                    btnRefresh.setVisibility(View.VISIBLE);
                }

            }, 3500);
        }
        //btnRefresh.setVisibility(View.VISIBLE);
    }
    public void setResponseToList(int id ){
        Model.getWeather(id,"metric",api)
                .enqueue(new Callback<WeatherPojo>() {
                    @Override
                    public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                        if(response.isSuccessful()) {
                            if (!checkListContainCity(detailList,response.body().getId())) {
                                detailList.add(response.body());
                                Log.d("MyTag","detailList on is :"+ detailList.toString());
                           }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherPojo> call, Throwable t) {
                        Log.d("MyTag","Error while fetching data by retrofit"+t.getMessage());

                    }
                });

    }

    private void setAdapter (){
        RecyclerView recycler=findViewById(R.id.RecycleCityList);

            if (detailList!=null) {
                adapter = new AdapterSearch(detailList);
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false) {
                });
            }



    }
    public static boolean checkListContainCity(Collection <WeatherPojo> c, int cityId) {
        for(WeatherPojo o : c) {
            if(o != null && o.getId().equals(cityId)) {
                return true;
            }
        }
        return false;
    }
}






