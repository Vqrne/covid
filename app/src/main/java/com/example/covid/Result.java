package com.example.covid;

import org.json.JSONException;
import org.json.JSONObject;

public class Result {
    //Get a json String from the Reader object
    //Get values from the search from a json String
    String[] values = new String [] {null, null, null, null};

    public Result(String region_select, String week_select){
        Reader Json = new Reader(region_select, week_select);

        try {
            Json.GetJson(Json.url + Json.idCase);
            JSONObject jObjectCase = new JSONObject(Json.json);
            JSONObject jDatasetCase = jObjectCase.getJSONObject("dataset");
            JSONObject jValueCase = jDatasetCase.getJSONObject("value");
            values[0] = jValueCase.getString("0");
        } catch (JSONException e) {
            values[0] = "Data not available";
        }

        try {
            //deaths are only available when searching for nationwide data
            Json.GetJson(Json.url + Json.idDeath);
            JSONObject jObjectDeath = new JSONObject(Json.json);
            JSONObject jDatasetDeath = jObjectDeath.getJSONObject("dataset");
            JSONObject jValueDeath = jDatasetDeath.getJSONObject("value");
            values[1] = jValueDeath.getString("0");
        } catch (JSONException e) {
            values[1] = "Data not available";
        }

        try {
            Json.GetJson(Json.url + Json.idTesting);
            JSONObject jObjectTesting = new JSONObject(Json.json);
            JSONObject jDatasetTesting = jObjectTesting.getJSONObject("dataset");
            JSONObject jValueTesting = jDatasetTesting.getJSONObject("value");
            values[2] = jValueTesting.getString("0");
        } catch (JSONException e) {
            values[2] = "Data not available";
        }

        try {
            Json.GetJson(Json.url + Json.idPopulation);
            JSONObject jObjectPopulation = new JSONObject(Json.json);
            JSONObject jDatasetPopulation = jObjectPopulation.getJSONObject("dataset");
            JSONObject jValuePopulation = jDatasetPopulation.getJSONObject("value");
            values[3] = jValuePopulation.getString("0");
        } catch (JSONException e) {
            values[3] = "Data not available";
        }
        System.out.println("Searched: " + region_select + " - " + week_select);
    }
}
