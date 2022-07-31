package com.example.covid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"; //to save log when closing the app
    String home_region = "Select Region"; //to save selected home region when closing the app
    private SharedPreferences mPrefs;
    Log log = new Log(); //log object is only made here, gets passed to every search

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onResume() {
        //gets checkbox state and log when opening app
        super.onResume();
        mPrefs = getSharedPreferences(getLocalClassName(), MODE_PRIVATE); //retrieving saved data
        this.home_region = mPrefs.getString("home_key", home_region);

        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        if (!json.equals("")) {
            this.log = gson.fromJson(json, Log.class); //get the log
        }
        if (!this.home_region.equals("Select Region")) {
            //OnResume does search for home_region as checkbox was left checked when the app last closed
            CheckBox simpleCheckBox = this.findViewById(R.id.checkBox);
            simpleCheckBox.setChecked(true);
            Input inputs = new Input(this, 2);
            inputs.region_select = this.home_region;
            Search instance = new Search(this, inputs);
            instance.saveHome(log);
        }
    }

    @Override
    protected void onPause() {
        //Store values for checkbox state and log when closing the app
        super.onPause();
        CheckBox simpleCheckBox = this.findViewById(R.id.checkBox);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (simpleCheckBox.isChecked()) {
            //if checked saves new home region
            editor.putString("home_key", this.home_region);
        }
        else {
            //home_region is "Select Region" unless something else is checked
            editor.putString("home_key", "Select Region");
        }
        Gson gson = new Gson();
        String json = gson.toJson(this.log);
        editor.putString("MyObject", json);
        editor.commit(); //data saved with SharedPreferences
    }

    public void Search(View v) {
        //Searches for data of selected region and time and displays in the lower display
        Input inputs = new Input(this, 1);
        Search instance = new Search(this, inputs);
        instance.makeSearch(log);
    }

    public void Home(View v) {
        //Searches for data of selected region in the last week and displays in the upper display
        Input inputs = new Input(this, 2);
        this.home_region = inputs.region_select;
        Search instance = new Search(this, inputs);
        instance.saveHome(log);
    }

    public void showLog(View v) {
        //Shows new activity with all log objects
        Intent intent = new Intent(this, Log_layout.class);
        intent.putExtra(EXTRA_MESSAGE, log.printLog());
        startActivity(intent);
    }
}
