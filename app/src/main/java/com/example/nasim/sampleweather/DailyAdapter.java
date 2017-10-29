package com.example.nasim.sampleweather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 9/23/2017.
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.CarViewHolder> {

    private Context context;
    private ArrayList<HourlyModel>hourlyModels;


    public DailyAdapter(Context context, ArrayList<HourlyModel> hourlyModels){
        this.context = context;
        this.hourlyModels = hourlyModels;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.daily_single_row,parent,false);
        CarViewHolder carViewHolder = new CarViewHolder(v);
        return carViewHolder;
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
       // holder.carIV.setImageResource(cars.get(position).getCarImage());
        //holder.carNameTV.setText(cars.get(position).getCarName());

        DecimalFormat df = new DecimalFormat("#.#");
        String TemPerature = df.format((hourlyModels.get(position).getTemperature())-273.15);

        String date1 =hourlyModels.get(position).getDate();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormatter.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Get time from date
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String displayValue = timeFormatter.format(date);


        holder.date.setText(displayValue);


        holder.temp.setText(TemPerature +"°C");
        holder.desc.setText(hourlyModels.get(position).getDescription());

        String icon = hourlyModels.get(position).getIcon();

        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
        Picasso.with(context).load(iconUrl).into(holder.wicon);



    }

    @Override
    public int getItemCount() {
        return hourlyModels.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder{

        ImageView wicon;
        TextView date, temp, desc;

        public CarViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Toast.makeText(context, hourlyModels.get(pos).getDescription(), Toast.LENGTH_SHORT).show();
                }
            });

            wicon = itemView.findViewById(R.id.icon);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
            desc =itemView.findViewById(R.id.description);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }
}
