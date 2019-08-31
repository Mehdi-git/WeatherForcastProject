package com.example.weatherforcastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearchCity;
    TextView txtNew;
    String searchKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnSearchCity = findViewById(R.id.btnSearchCity);




        btnSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                edtSearch = findViewById(R.id.edtSearch);
                 searchKey =edtSearch.getText().toString();

                Intent i = new Intent(SearchActivity.this,MainActivity.class);
                i.putExtra("name",searchKey);

                startActivity(i);
            }
        });

    }

}