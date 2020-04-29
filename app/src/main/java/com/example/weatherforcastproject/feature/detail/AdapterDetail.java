package com.example.weatherforcastproject.feature.detail;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.pojo.forecast.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.RecyclerViewHolder> {

    private java.util.List<List>  myList;

    public AdapterDetail(java.util.List<List> list){

        myList = list;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.onBind(myList.get(position));
    }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView  txtTime;
        TextView  txtTempMin;
        TextView  txtDescribe;
        TextView  txtDate;
        ImageView imgIcon;

        private void onBind (com.example.weatherforcastproject.pojo.forecast.List list) {
            try {
                SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = format1.parse(list.getDtTxt());
                SimpleDateFormat format2=new SimpleDateFormat("EE");
                txtDate.setText(format2.format(date1));
                txtDescribe.setText(list.getWeather().get(0).getDescription());
                txtTime.setText(list.getDtTxt().substring(11,16));
                txtTempMin.setText((Math.round(list.getMain().getTempMin())) +" "+ Html.fromHtml("&#8451;"));
                if(!(list.getWeather().get(0).getIcon() == null)) {
                    String icon = list.getWeather().get(0).getIcon();
                    switch (icon) {
                        case "01d":
                            imgIcon.setImageResource(R.drawable.clear_sky_day_new);
                            break;
                        case "01n":
                            imgIcon.setImageResource(R.drawable.clear_sky_night_new);
                            break;
                        case "02d":
                            imgIcon.setImageResource(R.drawable.few_clouds_day);
                            break;
                        case "02n":
                            imgIcon.setImageResource(R.drawable.few_clouds_night);
                            break;
                        case "03d":
                        case "03n":
                            imgIcon.setImageResource(R.drawable.scattered_clouds);
                            break;
                        case "04d":
                            imgIcon.setImageResource(R.drawable.broken_clouds_day);
                            break;
                        case "04n":
                            imgIcon.setImageResource(R.drawable.broken_clouds_night);
                            break;
                        case "09d":
                        case "09n":
                            imgIcon.setImageResource(R.drawable.shower_rain);
                            break;
                        case "10d":
                            imgIcon.setImageResource(R.drawable.rain_day);
                            break;
                        case "10n":
                            imgIcon.setImageResource(R.drawable.rain_night);
                            break;
                        case "11d":
                            imgIcon.setImageResource(R.drawable.thunderstorm_day);
                            break;
                        case "11n":
                            imgIcon.setImageResource(R.drawable.thunderstorm_night);
                            break;
                        case "13d":
                            imgIcon.setImageResource(R.drawable.snow_day);
                            break;
                        case "13n":
                            imgIcon.setImageResource(R.drawable.snow_night);
                            break;
                        case "50d":
                            imgIcon.setImageResource(R.drawable.mist_day);
                            break;
                        case "50n":
                            imgIcon.setImageResource(R.drawable.mist_night);
                            break;
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
               txtTime = itemView.findViewById(R.id.txtTime);
               txtTempMin = itemView.findViewById(R.id.txtTempMin);
               imgIcon = itemView.findViewById(R.id.imgIcon);
               txtDescribe = itemView.findViewById(R.id.txtDescribe);
               txtDate = itemView.findViewById(R.id.txtDate);

        }
    }
}
