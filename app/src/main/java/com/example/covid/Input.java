package com.example.covid;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Locale;

public class Input {
    //Get user input values from the region and week spinners for searches
    //For the home search gets the last week as the week value
    public Activity activity;
    String week_select;
    String region_select;
    Calendar day;
    int week;

    public Input (Activity _activity, int opt) {
        this.activity = _activity;
        Spinner spinner_week = this.activity.findViewById(R.id.spinner_weeks);
        this.week_select = spinner_week.getSelectedItem().toString();
        Spinner spinner_region = this.activity.findViewById(R.id.spinner_regions);
        this.region_select = spinner_region.getSelectedItem().toString();

        CheckBox simpleCheckBox = this.activity.findViewById(R.id.checkBox);
        if (simpleCheckBox.isChecked() && opt == 2) { //get the last week for home search
            Locale finnish = new Locale("fi", "FI");
            Calendar today = Calendar.getInstance(finnish);
            today.set(Calendar.HOUR_OF_DAY, 0);
            GetDate(today);
        }
    }

    public void GetDate (Calendar today) {
        //Get previous week number
        today.add(Calendar.DAY_OF_MONTH, -7); //shifts time to the previous week
        int weekOfYear = today.get(Calendar.WEEK_OF_YEAR); //week number
        int Year = today.get(Calendar.YEAR);
        this.day = today;
        this.week = weekOfYear;
        this.week_select = "Year " + Year + " Week " + weekOfYear;
    }
}

