package com.example.covid;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.TextView;

public class Search {
    //Manages the search being done using user inputs from the Input object
    //Gets values of the search from the Result object
    //Displays the search results on the main layout and adds them to the log
    public Activity activity;
    Input inputs;
    String week_select;
    String region_select;

    public Search (Activity _activity, Input inputs) {
        this.activity = _activity;
        this.inputs = inputs;
        this.week_select = inputs.week_select;
        this.region_select = inputs.region_select;
    }

    public void makeSearch(Log log) {
        //Makes a search using given region and time
        if (this.week_select.equals("Select Week")) {
            System.out.println("Search failed - No Time Selected");
        }
        else if (this.region_select.equals("Select Region")) {
            System.out.println("Search failed - No Region Selected");
        }
        else {
            Result result = new Result(this.region_select, this.week_select);
            String[] values = result.values;
            displaySearch(this.region_select, values[0], values[1], values[2], values[3], 2);
            log.addLog(this.region_select, this.week_select, values[0], values[1], values[2], values[3]);
        }
    }

    public void saveHome(Log log) {
        //Makes a search using the given region and last week
        CheckBox simpleCheckBox = this.activity.findViewById(R.id.checkBox);
        if (!simpleCheckBox.isChecked()) { //when unchecking the checkbox
            String title = "Home - Latest Data";
            displaySearch(title, "Number of\nCases", "Number of\nDeaths", "Number of\nTests", "Number of\nPeople", 1);
        }
        else if (this.region_select.equals("Select Region")) {
            System.out.println("Search failed - No Region Selected");
        }
        else {
            Result result = new Result(this.region_select, this.week_select);
            String[] values = result.values;
            if (values[0].equals("Data not available")) {
                if (values[1].equals("Data not available")) {
                    if (values[2].equals("Data not available")) {
                        if (values[3].equals("Data not available")) {
                            //if there is no data, do another search with the previous week instead
                            this.inputs.GetDate(this.inputs.day); //inputs.week_select is shifted one week back
                            this.week_select = this.inputs.week_select;
                            saveHome(log);
                            return; //when the loop is over displaySearch and addLog are only called once
                        }
                    }
                }
            }
            displaySearch(this.region_select + " - Latest Data, Week " + this.inputs.week, values[0], values[1], values[2], values[3], 1);
            log.addLog(this.region_select, this.week_select, values[0], values[1], values[2], values[3]);
        }
    }

    public void displaySearch(String title, String cases, String deaths, String testing, String population, int opt) {
        //Prints the results of the search on the displays in the main view
        if (opt == 1) { //upper textview
            TextView CaseText = this.activity.findViewById(R.id.idHomeCases);
            CaseText.setText(cases);
            TextView DeathText = this.activity.findViewById(R.id.idHomeDeaths);
            DeathText.setText(deaths);
            TextView TestingText = this.activity.findViewById(R.id.idHomeTesting);
            TestingText.setText(testing);
            TextView PopulationText = this.activity.findViewById(R.id.idHomePopulation);
            PopulationText.setText(population);
            final TextView textViewToChange = this.activity.findViewById(R.id.HomeTextview);
            textViewToChange.setText(title);
        } else if (opt == 2) { //lower textview
            TextView CaseText = this.activity.findViewById(R.id.idRegionCases);
            CaseText.setText(cases);
            TextView DeathText = this.activity.findViewById(R.id.idRegionDeaths);
            DeathText.setText(deaths);
            TextView Testing = this.activity.findViewById(R.id.idRegionTesting);
            Testing.setText(testing);
            TextView PopulationText = this.activity.findViewById(R.id.idRegionPopulation);
            PopulationText.setText(population);
            final TextView textViewToChange = this.activity.findViewById(R.id.SearchTextview);
            textViewToChange.setText(title);
        }
    }
}
