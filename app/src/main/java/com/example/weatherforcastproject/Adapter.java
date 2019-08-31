package com.example.weatherforcastproject;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforcastproject.forecast.Weather;
import com.example.weatherforcastproject.forecast.WeatherClass;
import com.squareup.picasso.Picasso;
import com.example.weatherforcastproject.forecast.Main;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerViewHolder> {


    List<com.example.weatherforcastproject.List>  myList;





    public  Adapter(List<com.example.weatherforcastproject.List> e){

        myList=e;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int p) {

        holder.txtItem.setText(myList.get(p).getDtTxt());

        }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txtItem;
        TextView txtTemp;
        ImageView imgIcon;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
             txtItem = itemView.findViewById(R.id.txtItem);
             txtTemp = itemView.findViewById(R.id.txtTemp);
             imgIcon = itemView.findViewById(R.id.imgIcon);


        }
    }
}
