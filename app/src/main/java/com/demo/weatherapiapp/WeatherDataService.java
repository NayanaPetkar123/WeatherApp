package com.demo.weatherapiapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://api.openweathermap.org/data/2.5/forecast?appid=18b2e7e48acf770555ce6d32be36c3a9&cnt=5&q=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://api.openweathermap.org/data/2.5/forecast?appid=18b2e7e48acf770555ce6d32be36c3a9&cnt=5&id=";

    Context context;

    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener){

        String url = QUERY_FOR_CITY_ID+cityName;

        //String url = "https://api.androidhive.info/volley/person_array.json";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONObject cityObj = jsonObject.getJSONObject("city");
                            cityID = cityObj.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(context, "City Id: "+cityID, Toast.LENGTH_SHORT).show();
                        volleyResponseListener.onResponse(cityID);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Error Occurred");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(req);

       // return cityID;

    }

    public interface ForcastByIDResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

        public void getCityForcastByID(String cityID, ForcastByIDResponse forcastByIDResponse){

            List<WeatherReportModel> weatherReportModels = new ArrayList<>();

            String url = QUERY_FOR_CITY_WEATHER_BY_ID+cityID;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                            try {

                                JSONArray list = response.getJSONArray("list");

                                System.out.println("printing list "+list);


                                for(int i = 0; i < list.length(); i++) {

                                    WeatherReportModel one_day_weather = new WeatherReportModel();

                                    JSONObject current_day_from_api1 = list.getJSONObject(i);

                                    System.out.println("printing main " + current_day_from_api1);

                                    JSONObject current_day_from_api = current_day_from_api1.getJSONObject("main");

                                    one_day_weather.setTemp(current_day_from_api.getInt("temp"));
                                    one_day_weather.setFeels_like(current_day_from_api.getInt("feels_like"));
                                    one_day_weather.setPressure(current_day_from_api.getInt("pressure"));
                                    one_day_weather.setHumidity(current_day_from_api.getInt("humidity"));
                                    one_day_weather.setTemp_min(current_day_from_api.getInt("temp_min"));
                                    one_day_weather.setTemp_max(current_day_from_api.getInt("temp_max"));
                                    weatherReportModels.add(one_day_weather);
                                }

                                forcastByIDResponse.onResponse(weatherReportModels);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },  new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            MySingleton.getInstance(context).addToRequestQueue(request);
        }

        public interface GetCityForcastByNameCallback{
            void onError(String message);
            void onResponse(List<WeatherReportModel> weatherReportModels);
        }

        public void getCityForcastByName(String cityName, GetCityForcastByNameCallback getCityForcastByNameCallback){

            getCityID(cityName, new VolleyResponseListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(String cityID) {

                    getCityForcastByID(cityID, new ForcastByIDResponse() {
                        @Override
                        public void onError(String message) {

                        }

                        @Override
                        public void onResponse(List<WeatherReportModel> weatherReportModels) {

                            getCityForcastByNameCallback.onResponse(weatherReportModels);

                        }
                    });
                }
            });

        }

}


