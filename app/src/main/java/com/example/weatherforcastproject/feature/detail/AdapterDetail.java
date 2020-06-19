package com.example.weatherforcastproject.feature.detail;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforcastproject.R;
import com.example.weatherforcastproject.databinding.RecyclerItemDetailBinding;
import com.example.weatherforcastproject.pojo.forecast.List;
import java.text.MessageFormat;
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
        RecyclerItemDetailBinding itemBinding = RecyclerItemDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecyclerViewHolder(itemBinding);

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

         TextView  txtDescribe;
         TextView  txtTempMin;
         TextView  txtTime;
         TextView  txtDate;
         ImageView imgIcon;

        private void onBind (com.example.weatherforcastproject.pojo.forecast.List list) {
            try {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = format1.parse(list.getDtTxt());
                SimpleDateFormat format2 = new SimpleDateFormat("EE");
                txtDate.setText(format2.format(date1));
                txtDescribe.setText(list.getWeather().get(0).getDescription());
                txtTime.setText(list.getDtTxt().substring(11,16));
                txtTempMin.setText(MessageFormat.format("{0} {1}", Math.round(list.getMain().getTempMin()), Html.fromHtml("&#8451;")));
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

        public RecyclerViewHolder(@NonNull RecyclerItemDetailBinding itemBinding) {
            super(itemBinding.getRoot());
            txtDescribe = itemBinding.txtDescribe;
            txtTempMin = itemBinding.txtTempMin;
            txtTime = itemBinding.txtTime;
            txtDate = itemBinding.txtDate;
            imgIcon = itemBinding.imgIcon;
        }
    }
}
