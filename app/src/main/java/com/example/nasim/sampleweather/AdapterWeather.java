package com.example.nasim.sampleweather;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 9/15/2017.
 */

public class AdapterWeather extends ArrayAdapter<Model> {

    private Context context;
    private ArrayList<Model>model;
    private int count = 0;

    public AdapterWeather(@NonNull Context context, ArrayList<Model>model) {
        super(context,R.layout.weather_single_row, model);

        this.context = context;
        this.model = model;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

     //    Log.e("List view", "getView called "+(count++));


        if(convertView==null)
        {
            LayoutInflater layoutInflater= LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.weather_single_row, parent,false);

        }



        TextView date = convertView.findViewById(R.id.date);
        ImageView wicon = convertView.findViewById(R.id.imageView);
        TextView description = convertView.findViewById(R.id.description);
      //  TextView wind = convertView.findViewById(R.id.wind);
      //  TextView pressure = convertView.findViewById(R.id.pressure);
      //  TextView humidity = convertView.findViewById(R.id.humidity);
        TextView temperature = convertView.findViewById(R.id.temperature);
      //  TextView tmax = convertView.findViewById(R.id.tmax);
     //   TextView tmin = convertView.findViewById(R.id.tmin);

         if(position==0)
         {
             date.setText("Today");
         }
         else if (position==1)
         {
             date.setText("Tomorrow");
         }

         else {
             Date createdOn = new Date((model.get(position).getDate())*1000);
             String stringDate = DateFormat.getDateTimeInstance().format(createdOn);
             date.setText(stringDate);
             }

        double temp= model.get(position).getTemperature();
        DecimalFormat df = new DecimalFormat("#.#");
        String TemPerature = df.format(temp-273.15);

        double Tmax =model.get(position).getTmax();
        double Tmin =model.get(position).getTmin();

        String tmaxf =df.format(Tmax-273.15);
        String tminf =df.format(Tmin-273.15);


        description.setText(model.get(position).getDescription()+"");
   //     wind.setText("Wind: "+ model.get(position).getWind()+" m/s");
     //   pressure.setText("Pressure: "+model.get(position).getPressure()+" hPa");
       // humidity.setText("Humidity: "+model.get(position).getHumidity()+"");
        temperature.setText(TemPerature+"°C");
       // tmax.setText(tmaxf+"°C");
       // tmin.setText(tminf+"°C");


        String icon= model.get(position).getIcon();
        if(icon.equals("01d"))
        {
            wicon.setImageResource(R.drawable.dclear);
        }
        if(icon.equals("02d"))
        {
            wicon.setImageResource(R.drawable.dfewcloud);
        }
        if(icon.equals("03d"))
        {
            wicon.setImageResource(R.drawable.dscattercloud);
        }
        if(icon.equals("04d"))
        {
            wicon.setImageResource(R.drawable.brokencloud);
        }

        if(icon.equals("09d"))
        {
            wicon.setImageResource(R.drawable.showerrain);
        }

        if(icon.equals("10d"))
        {
            wicon.setImageResource(R.drawable.drain);
        }

        if(icon.equals("11d"))
        {
            wicon.setImageResource(R.drawable.dthunder);
        }
        if(icon.equals("13d"))
        {
            wicon.setImageResource(R.drawable.dsnow);
        }
        if(icon.equals("50d"))
        {
            wicon.setImageResource(R.drawable.mist);
        }

        if(icon.equals("01n"))
        {
            wicon.setImageResource(R.drawable.nclear);
        }
        if(icon.equals("02n"))
        {
            wicon.setImageResource(R.drawable.nfewcloud);
        }
        if(icon.equals("03n"))
        {
            wicon.setImageResource(R.drawable.nscattercloud);
        }

        if(icon.equals("04n"))
        {
            wicon.setImageResource(R.drawable.brokencloud);
        }
        if(icon.equals("09n"))
        {
            wicon.setImageResource(R.drawable.showerrain);
        }

        if(icon.equals("10n"))
        {
            wicon.setImageResource(R.drawable.nrain);
        }
        if(icon.equals("11n"))
        {
            wicon.setImageResource(R.drawable.nthunder);
        }

        if(icon.equals("13n"))
        {
            wicon.setImageResource(R.drawable.nsnow);
        }
        if(icon.equals("50n"))
        {
            wicon.setImageResource(R.drawable.mist);
        }

      //  String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
        //Picasso.with(context).load(iconUrl).into(imageView);

        return convertView;

    }
}
