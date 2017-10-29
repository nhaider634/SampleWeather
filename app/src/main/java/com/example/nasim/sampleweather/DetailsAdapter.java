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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 9/23/2017.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.CarViewHolder> {

    private Context context;
    private ArrayList<Model>model;


    public DetailsAdapter(Context context, ArrayList<Model> model){
        this.context = context;
        this.model = model;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.details_single_row,parent,false);
        CarViewHolder carViewHolder = new CarViewHolder(v);
        return carViewHolder;
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        // holder.carIV.setImageResource(cars.get(position).getCarImage());
        //holder.carNameTV.setText(cars.get(position).getCarName());




        if(position==0)
        {
            holder.date.setText("Today");
        }
        else if (position==1)
        {
            holder.date.setText("Tomorrow");
        }

        else {
            Date createdOn = new Date((model.get(position).getDate())*1000);
            String stringDate = DateFormat.getDateTimeInstance().format(createdOn);
            holder.date.setText(stringDate);
        }

        double temp= model.get(position).getTemperature();
        DecimalFormat df = new DecimalFormat("#.#");
        String TemPerature = df.format(temp-273.15);

        double Tmax =model.get(position).getTmax();
        double Tmin =model.get(position).getTmin();

        String tmaxf =df.format(Tmax-273.15);
        String tminf =df.format(Tmin-273.15);


        holder.description.setText(model.get(position).getDescription()+"");
        holder.wind.setText("Wind: "+ model.get(position).getWind()+" m/s");
        holder.pressure.setText("Pressure: "+model.get(position).getPressure()+" hPa");
        holder.humidity.setText("Humidity: "+model.get(position).getHumidity()+"");
       holder.temperature.setText(TemPerature+"°C");
         holder.tmax.setText(tmaxf+"°C");
         holder.tmin.setText(tminf+"°C");


        String icon= model.get(position).getIcon();
        if(icon.equals("01d"))
        {
            holder.wicon.setImageResource(R.drawable.dclear);
        }
        if(icon.equals("02d"))
        {
            holder.wicon.setImageResource(R.drawable.dfewcloud);
        }
        if(icon.equals("03d"))
        {
            holder.wicon.setImageResource(R.drawable.dscattercloud);
        }
        if(icon.equals("04d"))
        {
            holder.wicon.setImageResource(R.drawable.brokencloud);
        }

        if(icon.equals("09d"))
        {
            holder.wicon.setImageResource(R.drawable.showerrain);
        }

        if(icon.equals("10d"))
        {
            holder.wicon.setImageResource(R.drawable.drain);
        }

        if(icon.equals("11d"))
        {
            holder.wicon.setImageResource(R.drawable.dthunder);
        }
        if(icon.equals("13d"))
        {
            holder.wicon.setImageResource(R.drawable.dsnow);
        }
        if(icon.equals("50d"))
        {
            holder.wicon.setImageResource(R.drawable.mist);
        }

        if(icon.equals("01n"))
        {
            holder.wicon.setImageResource(R.drawable.nclear);
        }
        if(icon.equals("02n"))
        {
            holder.wicon.setImageResource(R.drawable.nfewcloud);
        }
        if(icon.equals("03n"))
        {
            holder.wicon.setImageResource(R.drawable.nscattercloud);
        }

        if(icon.equals("04n"))
        {
            holder.wicon.setImageResource(R.drawable.brokencloud);
        }
        if(icon.equals("09n"))
        {
            holder.wicon.setImageResource(R.drawable.showerrain);
        }

        if(icon.equals("10n"))
        {
            holder.wicon.setImageResource(R.drawable.nrain);
        }
        if(icon.equals("11n"))
        {
            holder.wicon.setImageResource(R.drawable.nthunder);
        }

        if(icon.equals("13n"))
        {
            holder.wicon.setImageResource(R.drawable.nsnow);
        }
        if(icon.equals("50n"))
        {
            holder.wicon.setImageResource(R.drawable.mist);
        }







    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder{

        TextView date,description,wind,pressure,humidity,temperature,tmax,tmin;
        ImageView wicon;

        public CarViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Toast.makeText(context, model.get(pos).getDescription(), Toast.LENGTH_SHORT).show();
                }
            });

            date = itemView.findViewById(R.id.date);
             wicon = itemView.findViewById(R.id.imageView);
             description = itemView.findViewById(R.id.description);
             wind = itemView.findViewById(R.id.wind);
             pressure = itemView.findViewById(R.id.pressure);
             humidity = itemView.findViewById(R.id.humidity);
             temperature = itemView.findViewById(R.id.temperature);
             tmax = itemView.findViewById(R.id.tmax);
             tmin = itemView.findViewById(R.id.tmin);


        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }
}
