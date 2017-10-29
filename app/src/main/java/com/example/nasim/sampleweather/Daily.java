package com.example.nasim.sampleweather;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Daily extends AppCompatActivity {

    private RecyclerView detailsTecycle;
    private DetailsAdapter carAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);


        ArrayList<Model> myList = (ArrayList<Model>) getIntent().getSerializableExtra("mylist");

        String countryName =getIntent().getStringExtra("country");
        String cityName =getIntent().getStringExtra("city");
        TextView tv = (TextView)findViewById(R.id.CityName);
        if(cityName.equals("Sāmāir"))
        {
            tv.setText("Dhaka"+", "+countryName);
        }
        else
        {
            tv.setText(cityName+", "+countryName);

        }




        DetailsAdapter detailsAdapter =new DetailsAdapter(this,myList);


        RecyclerView  carRV = (RecyclerView) findViewById(R.id.detailRecyle);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        carRV.setLayoutManager(llm);
        carRV.setAdapter(detailsAdapter);

    }


}
