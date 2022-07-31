package com.example.covid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Log{
    //Contains an arraylist where the results of each search is added
    ArrayList<String> LogArray = new ArrayList<>();
    int entry_number = 0;

    public Log () {
    }

    public void addLog(String region_select, String week_select, String cas, String det, String tes, String pop){
        //add the search results to the log after each search
        String time_stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String entry = "[" + entry_number + "]: " + region_select + " - " + week_select
                + " - cases:" + cas + " - deaths:" + det + " - tests:"
                + tes + " - population:" + pop + " (" + time_stamp + ");"; //the log entries formatting
        LogArray.add(entry);
        entry_number = entry_number + 1;
    }

    public String printLog() {
        //display all log objects on the log layout
        StringBuilder message = new StringBuilder();
        for(String i:LogArray) {
            //add every log to a single string
            message.append(i).append("\n\n");
        }
        return message.toString();
    }
}
