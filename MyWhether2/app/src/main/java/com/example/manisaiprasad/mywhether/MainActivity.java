package com.example.manisaiprasad.mywhether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result,weatherD,latlon;
    ImageView imageView;
    //http://api.openweathermap.org/data/2.5/weather?q=Tirupati,in&appid=79e9565b6d45f0a48a3ff121a711792c
    String baseURL="http://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=79e9565b6d45f0a48a3ff121a711792c";
    String myURL="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.button);
        city=(EditText)findViewById(R.id.getCity);
        result=(TextView)findViewById(R.id.result);
        weatherD=(TextView)findViewById(R.id.w);
        latlon=(TextView)findViewById(R.id.lat);
        imageView=(ImageView)findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mycity= String.valueOf(city.getText());
                if (Objects.equals(mycity, "")||Objects.equals(mycity,"Enter Your City...")){
                    result.setText("");
                    weatherD.setText("Plz.. Enter The City");
                    latlon.setText("");
                }else {

                    myURL = baseURL + city.getText().toString() + API;
                    Log.i("Url", "My Url is :"+myURL);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    String main,coord = null;
                                    try {
                                        String weather = response.getString("weather");
                                        Log.i("info", "onResponse: "+weather);
                                        String mWeather="",icon="";

                                        JSONArray ar = new JSONArray(weather);

                                        for (int i=0;i<ar.length();i++){
                                            JSONObject parObject = ar.getJSONObject(i);
                                            Log.i("ParObject", i+" is "+parObject);
                                            Log.i("ID", "ID :"+parObject.getString("id"));
                                            mWeather=parObject.getString("main")+"\n"+parObject.getString("description");
                                            weatherD.setText(mWeather);
                                            Log.i("Main", "Main :"+parObject.getString("main"));
                                            Log.i("Description", "Description :"+parObject.getString("description"));
                                            Log.i("Icon", "Icon :"+parObject.getString("icon"));

                                            icon="w"+parObject.getString("icon");
                                            Log.i("Icon ","Icon : "+icon);

                                            switch (icon){
                                                case "w01d":imageView.setImageResource(R.drawable.w01d);break;
                                                case "w01n":imageView.setImageResource(R.drawable.w01n);break;
                                                case "w02d":imageView.setImageResource(R.drawable.w02d);break;
                                                case "w02n":imageView.setImageResource(R.drawable.w02n);break;
                                                case "w03d":imageView.setImageResource(R.drawable.w03d);break;
                                                case "w03n":imageView.setImageResource(R.drawable.w03n);break;
                                                case "w04d":imageView.setImageResource(R.drawable.w04d);break;
                                                case "w04n":imageView.setImageResource(R.drawable.w04n);break;
                                                case "w09d":imageView.setImageResource(R.drawable.w09d);break;
                                                case "w09n":imageView.setImageResource(R.drawable.w09n);break;
                                                case "w10d":imageView.setImageResource(R.drawable.w10d);break;
                                                case "w10n":imageView.setImageResource(R.drawable.w10n);break;
                                                case "w11d":imageView.setImageResource(R.drawable.w11d);break;
                                                case "w11n":imageView.setImageResource(R.drawable.w11n);break;
                                                case "w13d":imageView.setImageResource(R.drawable.w13d);break;
                                                case "w13n":imageView.setImageResource(R.drawable.w13n);break;
                                                case "w50d":imageView.setImageResource(R.drawable.w50d);break;
                                                case "w50n":imageView.setImageResource(R.drawable.w50n);break;
                                            }




                                        }
                                        coord = response.getString("coord");
                                        JSONObject cord =new JSONObject(coord);
                                        String lat=cord.getString("lat");
                                        String lon=cord.getString("lon");
                                        Log.i("Lat","Latitude: "+lat);
                                        Log.i("Lon","longitude: "+lon);
                                        latlon.setText("Latitude: "+lat+"\n"+"Longitude: "+lon);


                                        main = response.getString("main");
                                        Log.i("Main", "Main : " + main);
                                        JSONObject mai = new JSONObject(main);
                                        String temp = mai.getString("temp");
                                        Log.i("Temp", "Temp : " + temp);
                                        // assign to float variable the temperature in Kelvin
                                        float kelvin = Float.parseFloat(temp);
                                        // Kelvin to Degree Celsius Conversion
                                        float celsius = kelvin - 273.15F;

                                        result.setText("Temp: " + celsius + "\n" + "Humidity: " + mai.getString("humidity") + "\n" );

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    weatherD.setText("Somthing Went Wrong "+error);
                                    latlon.setText("");
                                    result.setText("");
                                    imageView.setImageResource(R.drawable.images);
                                }
                            }
                    );
                    MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
                }
            }

        });

    }
}
