package com.example.homework1extracredit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Widgets.
    TextView location_name;
    TextView temperature;
    TextView weather_conditions;
    TextView sunrise_time;
    TextView sunset_time;
    EditText city_input;
    Button init_weather;
    ImageView weather_image;
    TextView error_message;

    //API results.
    String city_name;
    String temp;
    String conditions;
    String sunrise;
    String sunset;

    //API access information.
    String url = "https://api.openweathermap.org/data/2.5/weather";
    String api_key = "3663abd9b2e5bdcf0adc34e4c1bfa8e1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Widget instantiation.
        location_name = (TextView) findViewById(R.id.Location_Name);
        temperature = (TextView) findViewById(R.id.Temperature);
        weather_conditions = (TextView) findViewById(R.id.Weather_Conditions);
        sunrise_time = (TextView) findViewById(R.id.Sunrise_Time);
        sunset_time = (TextView) findViewById(R.id.Sunset_Time);
        city_input = (EditText) findViewById(R.id.City_Name_Input);
        init_weather = (Button) findViewById(R.id.Init_Weather);
        weather_image = (ImageView) findViewById(R.id.Weather_Image);
        error_message = (TextView) findViewById(R.id.Input_Error);
        init_weather.setOnClickListener(this);

        //Updating UI.
        fillWeather("Stony Brook");
    }

    // Makes API call. If successful, store all relevant retrieved information. Otherwise, display error message.
    public void fillWeather(String city)
    {
        String api_endpoint = url + "?q=" + city + "&appid=" + api_key;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest response = new StringRequest(Request.Method.GET, api_endpoint, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //api_response = response;
                Log.d("success", response);
                try {
                    JSONObject json_response = new JSONObject(response);
                    Log.d("success", json_response.toString());

                    //Example: "weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}]
                    JSONArray json_weather_array_section = json_response.getJSONArray("weather");

                    //Example: {"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}
                    JSONObject json_weather_section = json_weather_array_section.getJSONObject(0);

                    //Example: "main":{"temp":300.08,"feels_like":302.38,"temp_min":294.63,"temp_max":305.57,"pressure":1020,"humidity":76}
                    JSONObject json_main_section = json_response.getJSONObject("main");

                    //Example: "sys":{"type":2,"id":2011136,"country":"US","sunrise":1623057661,"sunset":1623111733}
                    JSONObject json_sys_section = json_response.getJSONObject("sys");

                    //Storing retrieved data.
                    city_name = city;
                    temp = Math.round(((json_main_section.getDouble("temp") - 273.15) * 100.0)) / 100.0 + "°C";
                    conditions = json_weather_section.getString("description");
                    sunrise = "Sunrise: " + formatTime(new Date(json_sys_section.getLong("sunrise")*1000L));
                    sunset = "Sunset: " + formatTime(new Date(json_sys_section.getLong("sunset")*1000L));

                    //Using stored data to update UI. Pass weather code to update image later.
                    updateUI(json_weather_section.getInt("id"));

                    //Successful call, therefore, remove error message.
                    error_message.setVisibility(error_message.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Unsuccessful call, therefore, display error message.
                error_message.setVisibility(error_message.VISIBLE);
            }
        });
        queue.add(response);
    }

    //Updating widget text.
    public void updateUI(int weather_id)
    {
        location_name.setText(city_name);
        temperature.setText(String.valueOf(temp));
        weather_conditions.setText(conditions);
        sunrise_time.setText(sunrise);
        sunset_time.setText(sunset);
        setImage(weather_id);
    }

    //Setting corresponding image to weather.
    public void setImage(int weather_id)
    {
        if(weather_id >= 200 && weather_id <= 232)
        {
            weather_image.setImageResource(R.drawable.ic_thunderstorm);
        }
        else if(weather_id >= 300 && weather_id <= 321)
        {
            weather_image.setImageResource(R.drawable.ic_drizzle);
        }
        else if(weather_id >= 500 && weather_id <= 531)
        {
            weather_image.setImageResource(R.drawable.ic_rain);
        }
        else if(weather_id >= 600 && weather_id <= 622)
        {
            weather_image.setImageResource(R.drawable.ic_snow);
        }
        else if(weather_id == 800)
        {
            weather_image.setImageResource(R.drawable.ic_clear);
        }
        else if(weather_id >= 800 && weather_id <= 804)
        {
            weather_image.setImageResource(R.drawable.ic_clouds);
        }
        else
        {
            weather_image.setImageResource(R.drawable.ic_misc);
        }
    }

    //Listening for user clicks indicating a request.
    @Override
    public void onClick(View v) {
        if (v == init_weather)
        {
            fillWeather(city_input.getText().toString());
        }
    }

    //Formatting time for sunrise and sunset properly from Unix to standard time format.
    public String formatTime(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return formatter.format(date);
    }

}