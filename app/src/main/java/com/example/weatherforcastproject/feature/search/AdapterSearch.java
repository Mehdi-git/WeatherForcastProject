package com.example.weatherforcastproject.feature.search;
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
import com.example.weatherforcastproject.pojo.weatherPojo.WeatherPojo;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.RecyclerViewHolder> {

    private List<WeatherPojo> searchList;

    public AdapterSearch (List<WeatherPojo> list) {
       searchList = list;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_item,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
       holder.onBind(searchList.get(position));

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView imgIconWeather;
        TextView txtDescription;
        TextView txtTemp;
        TextView txtName;


        @SuppressLint("SetTextI18n")
        private void onBind(WeatherPojo myList){
            txtName.setText(myList.getName());
            txtDescription.setText(myList.getWeather().get(0).getDescription());
            txtTemp.setText((Math.round(myList.getMain().getTemp())) +" "+ Html.fromHtml("&#8451;"));

            if(!(myList.getWeather().get(0).getIcon() == null)) {
                String icon = myList.getWeather().get(0).getIcon();
                switch (icon) {
                    case "01d":
                        imgIconWeather.setImageResource(R.drawable.clear_sky_day_1);
                        break;
                    case "01n":
                        imgIconWeather.setImageResource(R.drawable.clear_sky_night_new);
                        break;
                    case "02d":
                        imgIconWeather.setImageResource(R.drawable.few_clouds_day);
                        break;
                    case "02n":
                        imgIconWeather.setImageResource(R.drawable.few_clouds_night);
                        break;
                    case "03d":
                    case "03n":
                        imgIconWeather.setImageResource(R.drawable.scattered_clouds);
                        break;
                    case "04d":
                        imgIconWeather.setImageResource(R.drawable.broken_clouds_day);
                        break;
                    case "04n":
                        imgIconWeather.setImageResource(R.drawable.broken_clouds_night);
                        break;
                    case "09d":
                    case "09n":
                        imgIconWeather.setImageResource(R.drawable.shower_rain);
                        break;
                    case "10d":
                        imgIconWeather.setImageResource(R.drawable.rain_day);
                        break;
                    case "10n":
                        imgIconWeather.setImageResource(R.drawable.rain_night);
                        break;
                    case "11d":
                        imgIconWeather.setImageResource(R.drawable.thunderstorm_day);
                        break;
                    case "11n":
                        imgIconWeather.setImageResource(R.drawable.thunderstorm_night);
                        break;
                    case "13d":
                        imgIconWeather.setImageResource(R.drawable.snow_day);
                        break;
                    case "13n":
                        imgIconWeather.setImageResource(R.drawable.snow_night);
                        break;
                    case "50d":
                        imgIconWeather.setImageResource(R.drawable.mist_day);
                        break;
                    case "50n":
                        imgIconWeather.setImageResource(R.drawable.mist_night);
                        break;
                }
            }


        }

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIconWeather = itemView.findViewById(R.id.imgIconWeather);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtTemp = itemView.findViewById(R.id.txtTemp);
            txtName = itemView.findViewById(R.id.txtName);

        }
    }

}
