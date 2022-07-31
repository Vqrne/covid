package com.example.covid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Reader {
    //Gets the url that contains the wanted data
    //turns the url into a json String
    String region_select;
    String week_select;
    String json = null;
    String url = null;
    String sidTime = null;
    String sidArea = null;
    String idCase = "444833.&fo=1";
    String idDeath = "492118.&fo=1";
    String idTesting = "445356.&fo=1";
    String idPopulation = "445344.&fo=1";

    public Reader(String region_select, String week_select) {
        this.region_select = region_select;
        this.week_select = week_select;
        try {
            String valueTime;
            String valueArea;
            String url = "https://sampo.thl.fi/pivot/prod/en/epirapo/covid19case/fact_epirapo_covid19case.dimensions.json";
            GetJson(url); //json string from the url
            String json = this.json.substring(this.json.indexOf("["));

            JSONArray jMain = new JSONArray(json);
            JSONObject JObjectArea = jMain.getJSONObject(0);
            JSONObject JObjectTime = jMain.getJSONObject(1);

            JSONArray jArrayArea = JObjectArea.getJSONArray("children");
            JSONArray jArrayTime = JObjectTime.getJSONArray("children");

            JSONObject jObjectArea2 = jArrayArea.getJSONObject(0);
            JSONObject jObjectTime2 = jArrayTime.getJSONObject(0);

            JSONArray jArrayArea2 = jObjectArea2.getJSONArray("children");
            JSONArray jArrayTime2 = jObjectTime2.getJSONArray("children");

            for (int i=0; i < jArrayTime2.length(); i++){
                //goes through an array of weeks to get the selected one
                JSONObject jTime = jArrayTime2.getJSONObject(i);
                valueTime = jTime.getString("label");
                if (valueTime.equals(week_select)) {
                    this.sidTime = jTime.getString("sid");
                    break;
                }
                else if (week_select.equals("All times")){
                    //this option isn't in the array
                    this.sidTime = "202001011";
                }
            }
            for (int n=0; n < jArrayArea2.length(); n++) {
                //goes through an array of regions to get the selected one
                JSONObject jArea = jArrayArea2.getJSONObject(n);
                valueArea = jArea.getString("label");
                if (valueArea.equals(region_select)) {
                    this.sidArea = jArea.getString("sid");
                    break;
                }
                else if (region_select.equals("All areas")){
                    //this option isn't in the array
                    this.sidArea = "445222";
                }
            }
            this.url = "https://sampo.thl.fi/pivot/prod/en/epirapo/covid19case/fact_epirapo_covid19case" +
                    ".json?row=dateweek20200101-" + sidTime + ".&column=hcdmunicipality2020-" + sidArea +
                    ".&filter=measure-"; //the url to get data at specified time and region

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GetJson(String uri) {
        try {
            //Get a json String from an url
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder ab = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                ab.append(line).append("\n");
            }
            this.json = ab.toString();
            in.close();

        } catch (ProtocolException e) {
            System.out.println("Unable to access the data");
        } catch (MalformedURLException e) {
            System.out.println("Unable to access the data");
        } catch (IOException e) {
            System.out.println("Unable to access the data");
        } finally {
            System.out.println("Read url: " + uri);
        }
    }
}