package com.android.peter.hs_friend_quest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch CrawlingSwitch = findViewById(R.id.CrawlingSwitch);
        final SharedPreferences settings = getSharedPreferences("setting",0);
        CrawlingSwitch.setChecked(settings.getBoolean("switch",false));
        CrawlingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
                if(isEnabled){
                }
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switch",isEnabled);
                editor.apply();
            }
        });
    }
}
