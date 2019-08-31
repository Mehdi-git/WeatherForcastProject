package com.example.weatherforcastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FirstActivity extends AppCompatActivity {



    Button btnSearch;
    Button btnPollution;
    Button btnAboutUs;
    ImageView imgBackground;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);



        btnSearch=findViewById(R.id.btnSearch);
        btnPollution=findViewById(R.id.btnPollution);
        btnAboutUs=findViewById(R.id.btnAboutUs);
        imgBackground=findViewById(R.id.imgBackground);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
