package com.example.weatherforcastproject;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerViewHolder> {


    List<com.example.weatherforcastproject.forcastFive.List>  myList;




    public  Adapter(List<com.example.weatherforcastproject.forcastFive.List> e){

        myList=e;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int p) {



        try {
            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format1.parse(myList.get(p).getDtTxt());
            SimpleDateFormat format2=new SimpleDateFormat("EE");

        holder.txtDate.setText(format2.format(date1));
        holder.txtDescribe.setText(myList.get(p).getWeather().get(0).getDescription());
        holder.txtTempMax.setText((Math.round(myList.get(p).getMain().getTempMax())) +" "+ Html.fromHtml("&#8451;"));
        holder.txtTempMin.setText((Math.round(myList.get(p).getMain().getTempMin())) +" "+ Html.fromHtml("&#8451;"));

            Glide.with(holder.itemView)
                .load("http://openweathermap.org/img/wn/" + myList.get(p).getWeather().get(0).getIcon() +"@2x.png")
                .centerCrop()
                .into(holder.imgIcon);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView  txtTempMax;
        TextView  txtTempMin;
        TextView  txtDescribe;
        TextView  txtDate;
        ImageView imgIcon;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
               txtTempMax = itemView.findViewById(R.id.txtTempMax);
               txtTempMin = itemView.findViewById(R.id.txtTempMin);
               imgIcon = itemView.findViewById(R.id.imgIcon);
               txtDescribe = itemView.findViewById(R.id.txtDescribe);
               txtDate = itemView.findViewById(R.id.txtDate);

        }
    }
}
