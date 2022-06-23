package com.demo.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_getCityID,btn_getWeatherbyID,btn_getWeatherbyName ;
    EditText et_dataInput;
    ListView lv_weatherReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        btn_getCityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherbyID = findViewById(R.id.btn_getWeatherbyCityID);
        btn_getWeatherbyName = findViewById(R.id.btn_getWeatherbyCityName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReports = findViewById(R.id.lv_weatherReports);

        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        btn_getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Instantiate the RequestQueue.
               // RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

               weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                   @Override
                   public void onError(String message) {
                       Toast.makeText(MainActivity.this, "Something's Wrong", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onResponse(String cityID) {
                       Toast.makeText(MainActivity.this, "Returned an ID: "+cityID, Toast.LENGTH_SHORT).show();
                   }
               });

            }
        });

        btn_getWeatherbyID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               weatherDataService.getCityForcastByID(et_dataInput.getText().toString(), new WeatherDataService.ForcastByIDResponse() {
                   @Override
                   public void onError(String message) {
                       Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onResponse(List<WeatherReportModel> weatherReportModels) {
                       //Toast.makeText(MainActivity.this, weatherReportModel.toString(), Toast.LENGTH_LONG).show();
                       ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModels);
                       lv_weatherReports.setAdapter(arrayAdapter);
                   }
               });

            }

        });

        btn_getWeatherbyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataService.getCityForcastByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForcastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        //Toast.makeText(MainActivity.this, weatherReportModel.toString(), Toast.LENGTH_LONG).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        lv_weatherReports.setAdapter(arrayAdapter);
                    }
                });

                //Toast.makeText(MainActivity.this, "You typed "+et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}