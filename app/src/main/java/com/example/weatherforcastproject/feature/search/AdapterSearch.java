package com.example.weatherforcastproject.feature.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.pojo.weather.WeatherPojo;
import java.util.List;
import com.example.weatherforcastproject.databinding.RecyclerItemSearchBinding;


import static android.text.Html.*;
import static java.text.MessageFormat.format;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.RecyclerViewHolder> {

    private List<WeatherPojo> searchList;
    private ClickListener listener;

    public AdapterSearch (List<WeatherPojo> list , ClickListener listener) {
       searchList = list;
       this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerItemSearchBinding itemBinding = RecyclerItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecyclerViewHolder(itemBinding);
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
        TextView txtHumidity;
        TextView txtDegree;
        TextView txtTemp;
        TextView txtName;

            private void onBind(WeatherPojo myList)  {
                txtName.setText(String.format("%s,%s", myList.getName(), myList.getSys().getCountry()));
                txtDescription.setText(myList.getWeather().get(0).getDescription());
                txtTemp.setText(format("{0}", Math.round(myList.getMain().getTemp())));
                txtDegree.setText(fromHtml("&#8451"));
                txtHumidity.setText(String.format("%s: %s%s","Humidity",myList.getMain().getHumidity(),"%"));

            if(!(myList.getWeather().get(0).getIcon() == null)) {
                String icon = myList.getWeather().get(0).getIcon();
                switch (icon) {
                    case "01d":
                        imgIconWeather.setImageResource(R.drawable.clear_sky_day_new);
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

        public RecyclerViewHolder(@NonNull RecyclerItemSearchBinding itemBinding) {
            super(itemBinding.getRoot());

            imgIconWeather = itemBinding.imgIconWeather;
            txtDescription = itemBinding.txtDescription;
            txtHumidity = itemBinding.txtHumidity;
            txtDegree = itemBinding.txtDegree;
            txtTemp = itemBinding.txtTemp;
            txtName= itemBinding.txtName;


            itemView.setOnClickListener(v -> {
                if (!searchList.isEmpty())
                listener.onItemClick(searchList.get(getAdapterPosition()).getId());

            });
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(searchList.get(getAdapterPosition()).getId());
                return false;
            });
        }
    }
}
