package com.example.weatherforcastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {



    Button btnSearch;
    Button btnPollution;
    Button btnAboutUs;
    ImageView imgBackground;
    DrawerLayout drawerLayout;
    Boolean isUserClickOnBack = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if(isUserClickOnBack) {
            super.onBackPressed();
        }
        else{
            isUserClickOnBack=true;
            Toast.makeText(this,"Please Press Back Again For Exit!",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable(){

               @Override
               public void run() {
                   isUserClickOnBack=false;
               }
           },2500);

        }
    }
}
