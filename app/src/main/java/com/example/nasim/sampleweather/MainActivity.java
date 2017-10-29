package com.example.nasim.sampleweather;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


// Main activity class
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION =101 ;
    protected LocationManager locationManager;
    TextView result1;
    ListView listview;
    AdapterWeather adapter;
    private ArrayList<Model> arraylist;
    Button detailButton;
    TextView city , temp, des, wind, pressure, humidity, sunrise, sunset,update;
    ImageView wicon;
    String country,City;
    Model model;
    EditText cityName;
    double lat = 0;
    double lon= 0;
    double current_lat=0;
    double current_lon=0;
    LinearLayout LL ;
    String strAdd = "";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Geocoder geocoder;
    private List<Address> address;

   ProgressDialog progress1;
    // This method would be called when What's the weather button would be pressed




    public void findtheweather(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search for City");

// Set an EditText view to get user input
        final EditText input = new EditText(this);


        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String s = input.getText().toString();

                getLocationFromAddress(MainActivity.this, s);
                //do what you want with your result
                // Executing download task and change this " uk&appid=9f681051b003f104ae5e2a0cbef19a02" with your own API KEY


            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }
















    public void getLocationFromAddress(Context context,String strAddress) {


        Geocoder coder = new Geocoder(context);
        List<Address> address = null;
        Address location= null;
        // May throw an IOException
        try {
            address = coder.getFromLocationName(strAddress, 1);
            location = address.get(0);
            lat=  location.getLatitude();
            lon=    location.getLongitude();


            getCompleteAddressString(lat,lon);

            current task1 = new current();
            String current= "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&APPID=62f6de3f7c0803216a3a13bbe4ea9914";
            task1.execute(current);

            download task = new download();
            String forecast = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon=" + lon +"&APPID=62f6de3f7c0803216a3a13bbe4ea9914&cnt=16";
            task.execute(forecast);

            //  http://api.openweathermap.org/data/2.5/forecast?q=Dhaka,Bd&APPID=62f6de3f7c0803216a3a13bbe4ea9914

            hourlyForecast hf =new hourlyForecast(progress1);
            String hourly ="http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&APPID=62f6de3f7c0803216a3a13bbe4ea9914";
            hf.execute(hourly);



        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Please enter correct city name",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }






    }



    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LL = (LinearLayout) findViewById(R.id.backgroundLin);
        // Using id to tell where to make changes
        cityName = (EditText) findViewById(R.id.cityname);
        city = (TextView) findViewById(R.id.texta);
        city.setSelected(true);
        listview = (ListView) findViewById(R.id.list);
        temp = (TextView) findViewById(R.id.temp);
        des = (TextView) findViewById(R.id.desc);
        wind = (TextView) findViewById(R.id.wind);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        update = (TextView) findViewById(R.id.update);
        wicon =(ImageView)findViewById(R.id.weathericon);

        detailButton =(Button)findViewById(R.id.detailsbutton);


        progress1 =new ProgressDialog(this);
        progress1.setMessage("Laoding Weather Data...");


        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Daily.class);
                intent.putExtra("mylist", arraylist);
                intent.putExtra("country", country);
                intent.putExtra("city", City);
                startActivity(intent);
            }
        });

        geocoder =new Geocoder(this);



        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);


                for(Location location : locationResult.getLocations())
                {
                    current_lat  =  location.getLatitude();
                    current_lon  =  location.getLongitude();
                }

                getCompleteAddressString(current_lat, current_lon);

                current task1 = new current();
                String current = "http://api.openweathermap.org/data/2.5/weather?lat=" + current_lat + "&lon=" + current_lon + "&APPID=62f6de3f7c0803216a3a13bbe4ea9914";
                task1.execute(current);

                download task = new download();
                String forecast = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + current_lat + "&lon=" + current_lon + "&APPID=62f6de3f7c0803216a3a13bbe4ea9914&cnt=16";
                task.execute(forecast);

                hourlyForecast hf = new hourlyForecast(progress1);
                String hourlyForecast = "http://api.openweathermap.org/data/2.5/forecast?lat=" + current_lat + "&lon=" + current_lon + "&APPID=62f6de3f7c0803216a3a13bbe4ea9914";
                hf.execute(hourlyForecast);

            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();

    }





    private void createLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        //locationRequest.setSmallestDisplacement(100);
        locationRequest.setFastestInterval(50000);
       Toast.makeText(this,"You are now in"+ strAdd ,Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


        }


        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

    }




    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            address= geocoder.getFromLocation(LATITUDE,LONGITUDE,1);
            strAdd=   address.get(0).getAddressLine(0);
          //  String b =address.get(0).getCountryName();
            city.setText(strAdd+" ");

        } catch (IOException e) {
            e.printStackTrace();
        }


        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"My Current loction address ,Canont get Address!", Toast.LENGTH_SHORT).show();

        }
        return strAdd;
    }


    public void findthelocation(View view)
    {


        getCompleteAddressString(current_lat, current_lon);
        LL.setBackgroundResource(R.drawable.j);

        current task1 = new current();
        String current= "http://api.openweathermap.org/data/2.5/weather?lat="+current_lat+"&lon="+current_lon+"&APPID=62f6de3f7c0803216a3a13bbe4ea9914";
        task1.execute(current);

        download task = new download();
        String forecast = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+current_lat+"&lon="+current_lon+"&APPID=62f6de3f7c0803216a3a13bbe4ea9914&cnt=7";
        task.execute(forecast);

        hourlyForecast hf =new hourlyForecast(progress1);
        String hourlyForecast ="http://api.openweathermap.org/data/2.5/forecast?lat="+current_lat+"&lon="+current_lon+"&APPID=62f6de3f7c0803216a3a13bbe4ea9914";
        hf.execute(hourlyForecast);
    }

    // Creating download method with Async Task ( we r going to use this to get data from internet and parse it )
       private class download extends AsyncTask<String, Void, String> {

        // This method execute after doInBackground method and Parse the Json
        public download() {

        }


        @Override
        protected void onPostExecute(String result) {

            arraylist= new ArrayList<Model>();
// Try and catch block to catch any errors
            try {


                // We create out JSONObject from the data
                JSONObject jObj = new JSONObject(result);
                JSONObject jDayForecast= null;
                JSONArray jArr = jObj.getJSONArray("list"); // Here we have the forecast for every day
                // We traverse all the array and parse the data
                for (int i=0; i <jArr.length(); i++) {
                    jDayForecast = jArr.getJSONObject(i);


                    long date= jDayForecast.getLong("dt");

                    double pressure = jDayForecast.getDouble("pressure");
                    double humidity = jDayForecast.getDouble("humidity");
                    double wind = jDayForecast.getDouble("speed");

                    JSONObject temp = jDayForecast.getJSONObject("temp");
                    double temperature = temp.getDouble("day");
                    double tempmin =     temp.getDouble("min");
                    double tempmax =     temp.getDouble("max");

                    JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");

                    JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);

                         String description  =   jWeatherObj.getString("description");
                          String icon = jWeatherObj.getString("icon");



            Model model = new Model(date, pressure,tempmin,tempmax,temperature, description, humidity, wind, icon);


            arraylist.add(model);

                }

                adapter = new AdapterWeather(MainActivity.this, arraylist);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();



//                e.printStackTrace will just print a error report like a normal program do when it crashes u can change this with anything u like
            } catch (Exception e) {
                e.printStackTrace();
            }





        }

        // do doInBackground method we r using this method to download Json from site.
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

//            calling url as url
            URL url;
//            calling HttpUrlConnection as urlConnection
            HttpURLConnection urlConnection;

//            Using try and catch block to find any errors
            try {
                // assigning url value of first object in array called urls which is declared in this start of this method
                url = new URL(urls[0]);

                // using urlConnection to open url which we assigning URL before
                urlConnection = (HttpURLConnection) url.openConnection();

                // Using InputStream to download the content
                InputStream inputStream = urlConnection.getInputStream();
                // Using InputStreamReader to read the inputstream or the data we r downloading
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

//                using it to check if we reached the end of String / Data
                int Data = inputStreamReader.read();
//              using While loop to assign that data to string called result because InputStreamReader reads only one character at a time
                while (Data != -1) {

                    char current = (char) Data;

                    result += current;

                    Data = inputStreamReader.read();
                }

                // returning value of result

                return result;

//                Try and catch block to catch any errors
            } catch (Exception e) {
//                e.printStackTrace will just print a error report like a normal program do when it crashes u can change this with anything u like
                e.printStackTrace();
            }

            return null;
        }


    }


    private class hourlyForecast extends AsyncTask<String, Void, String> {



        // This method execute after doInBackground method and Parse the Json
        ArrayList<HourlyModel> arrayListhm =new ArrayList<>();

        public hourlyForecast(ProgressDialog progress1) {

         progress1 =progress1;
        }

        public void onPreExecute() {
            progress1.show();
        }



        @Override
        protected void onPostExecute(String result) {


// Try and catch block to catch any errors
            try {


                // We create out JSONObject from the data
                JSONObject jObj = new JSONObject(result);
                JSONObject jDayForecast= null;
                JSONArray jArr = jObj.getJSONArray("list"); // Here we have the forecast for every day
                // We traverse all the array and parse the data
                for (int i=0; i <jArr.length(); i++) {
                    jDayForecast = jArr.getJSONObject(i);


                    String date= jDayForecast.getString("dt_txt");

                    JSONObject temp = jDayForecast.getJSONObject("main");
                    double temperature = temp.getDouble("temp");

                    JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");

                    JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);

                    String description  =   jWeatherObj.getString("main");
                    String icon = jWeatherObj.getString("icon");


              HourlyModel hm =new HourlyModel(date,temperature,description,icon);

                    arrayListhm.add(hm);



                }
                RecyclerView  carRV = (RecyclerView) findViewById(R.id.carRV);
                DailyAdapter   carAdapter = new DailyAdapter(MainActivity.this,arrayListhm);

                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                carRV.setLayoutManager(llm);
                carRV.setAdapter(carAdapter);


//                e.printStackTrace will just print a error report like a normal program do when it crashes u can change this with anything u like
            } catch (Exception e) {
                e.printStackTrace();
            }


            progress1.dismiss();
        }

        // do doInBackground method we r using this method to download Json from site.
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

//            calling url as url
            URL url;
//            calling HttpUrlConnection as urlConnection
            HttpURLConnection urlConnection;

//            Using try and catch block to find any errors
            try {
                // assigning url value of first object in array called urls which is declared in this start of this method
                url = new URL(urls[0]);

                // using urlConnection to open url which we assigning URL before
                urlConnection = (HttpURLConnection) url.openConnection();

                // Using InputStream to download the content
                InputStream inputStream = urlConnection.getInputStream();
                // Using InputStreamReader to read the inputstream or the data we r downloading
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

//                using it to check if we reached the end of String / Data
                int Data = inputStreamReader.read();
//              using While loop to assign that data to string called result because InputStreamReader reads only one character at a time
                while (Data != -1) {

                    char current = (char) Data;

                    result += current;

                    Data = inputStreamReader.read();
                }

                // returning value of result

                return result;

//                Try and catch block to catch any errors
            } catch (Exception e) {
//                e.printStackTrace will just print a error report like a normal program do when it crashes u can change this with anything u like
                e.printStackTrace();
            }

            return null;
        }


    }












    private class current extends AsyncTask<String, Void, String> {

        // This method execute after doInBackground method and Parse the Json
        public current() {

        }

        @Override
        protected void onPostExecute(String result) {


// Try and catch block to catch any errors
            try {


                // We create out JSONObject from the data
             JSONObject jObj = new JSONObject(result);
               City= jObj.getString("name");


             JSONArray weather = jObj.getJSONArray("weather");
                String description = weather.getJSONObject(0).getString("description");

             JSONObject winds=  jObj.getJSONObject("wind");
                Double Wind= winds.getDouble("speed");

             JSONObject main=  jObj.getJSONObject("main");
                Double tem =  main.getDouble("temp");
                Double Pressure =  main.getDouble("pressure");
                Double Humidity =  main.getDouble("humidity");

             JSONObject sys=  jObj.getJSONObject("sys");
                long sunRise= sys.getLong("sunrise");
                long sunSet= sys.getLong("sunset");

                if(sys.getString("country")==null)
                {
                    country = "";
                }
                else
                {
                    country =sys.getString("country");
                }



             long Update=  jObj.getLong("dt");


                DateFormat dt =DateFormat.getTimeInstance();
                String date= dt.format(new Date(Update*1000));
                String SunRise= dt.format(new Date(sunRise*1000));
                String SunSet=  dt.format(new Date(sunSet*1000));

                DecimalFormat df = new DecimalFormat("#.#");
                 String TemPerature = df.format(tem-273.15);

               /* if(City.equals("Sāmāir") && country!= null)
                {
                    city.setText("Dhaka"+", "+country);

                }
                else
                {
                    city.setText(City+", "+country);
                }
*/
                temp.setText(TemPerature+"°C");
                des.setText(description);
                wind.setText("Wind: "+Wind+" m/s");
                pressure.setText("Pressure: "+Pressure+" hPa");
                humidity.setText("Humidity: "+Humidity+" %");
                sunrise.setText("Sunrise: "+ SunRise+"");
                sunset.setText("Sunset: "+SunSet+"");
                update.setText("Last Update: "+date+"");



                String icon = weather.getJSONObject(0).getString("icon");
             //   Toast.makeText(MainActivity.this,icon,Toast.LENGTH_SHORT).show();

                if(icon.equals("01d"))
                {
                    wicon.setImageResource(R.drawable.dclear);
                    LL.setBackgroundResource(R.drawable.b);
                }
                if(icon.equals("02d"))
                {
                    wicon.setImageResource(R.drawable.dfewcloud);
                    LL.setBackgroundResource(R.drawable.c);
                }
                if(icon.equals("03d"))
                {
                    wicon.setImageResource(R.drawable.dscattercloud);
                    LL.setBackgroundResource(R.drawable.d);
                }
                if(icon.equals("04d"))
                {
                    wicon.setImageResource(R.drawable.brokencloud);
                    LL.setBackgroundResource(R.drawable.e);
                }

                if(icon.equals("09d"))
                {
                    wicon.setImageResource(R.drawable.showerrain);
                    LL.setBackgroundResource(R.drawable.f);
                }

                if(icon.equals("10d"))
                {
                    wicon.setImageResource(R.drawable.drain);
                    LL.setBackgroundResource(R.drawable.g);
                }

                if(icon.equals("11d"))
                {
                    wicon.setImageResource(R.drawable.dthunder);
                    LL.setBackgroundResource(R.drawable.j);
                }
                if(icon.equals("13d"))
                {
                    wicon.setImageResource(R.drawable.dsnow);
                    LL.setBackgroundResource(R.drawable.i);
                }
                if(icon.equals("50d"))
                {
                    wicon.setImageResource(R.drawable.haze);
                    LL.setBackgroundResource(R.drawable.k);
                }

                if(icon.equals("01n"))
                {
                    wicon.setImageResource(R.drawable.nclear);
                    LL.setBackgroundResource(R.drawable.l);
                }
                if(icon.equals("02n"))
                {
                    wicon.setImageResource(R.drawable.nfewcloud);
                    LL.setBackgroundResource(R.drawable.m);
                }
                if(icon.equals("03n"))
                {
                    wicon.setImageResource(R.drawable.nscattercloud);
                    LL.setBackgroundResource(R.drawable.n);
                }

                if(icon.equals("04n"))
                {
                    wicon.setImageResource(R.drawable.brokencloud);
                    LL.setBackgroundResource(R.drawable.o);
                }
                if(icon.equals("09n"))
                {
                    wicon.setImageResource(R.drawable.showerrain);
                    LL.setBackgroundResource(R.drawable.p);
                }

                if(icon.equals("10n"))
                {
                    wicon.setImageResource(R.drawable.nrain);
                    LL.setBackgroundResource(R.drawable.u);
                }
                if(icon.equals("11n"))
                {
                    wicon.setImageResource(R.drawable.nthunder);
                    LL.setBackgroundResource(R.drawable.r);
                }

                if(icon.equals("13n"))
                {
                    wicon.setImageResource(R.drawable.nsnow);
                    LL.setBackgroundResource(R.drawable.h);

                }
                if(icon.equals("50n"))
                {
                    wicon.setImageResource(R.drawable.haze);
                    LL.setBackgroundResource(R.drawable.t);
                }















//                e.printStackTrace will just print a error report like a normal program do when it crashes u can change this with anything u like
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        // do doInBackground method we r using this method to download Json from site.
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

//            calling url as url
            URL url;
//            calling HttpUrlConnection as urlConnection
            HttpURLConnection urlConnection;

//            Using try and catch block to find any errors
            try {
                // assigning url value of first object in array called urls which is declared in this start of this method
                url = new URL(urls[0]);

                // using urlConnection to open url which we assigning URL before
                urlConnection = (HttpURLConnection) url.openConnection();

                // Using InputStream to download the content
                InputStream inputStream = urlConnection.getInputStream();
                // Using InputStreamReader to read the inputstream or the data we r downloading
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

//                using it to check if we reached the end of String / Data
                int Data = inputStreamReader.read();
//              using While loop to assign that data to string called result because InputStreamReader reads only one character at a time
                while (Data != -1) {

                    char current = (char) Data;

                    result += current;

                    Data = inputStreamReader.read();
                }

                // returning value of result

                return result;

//                Try and catch block to catch any errors
            } catch (Exception e) {
//                e.printStackTrace will just print a error report like a normal program do when it crashes u can change this with anything u like
                e.printStackTrace();
            }

            return null;
        }


    }




}