package com.android.peter.hs_friend_quest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseJobDispatcher dispatcher;
    ArrayList<AquiredData> stack = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestData();
    }


    private void RequestData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestJobService.BASE_URL)
                .build();
        CallService service = retrofit.create(CallService.class);
        Call<ResponseBody> call = service.url();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    HTMLParse(response.body().string());
                    setLayout();
                }catch (Exception e){
                    Log.e(TAG, "onResponse: "+e.getMessage() );
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }

    private void HTMLParse(String body){
        Document data = Jsoup.parse(body);
        Elements pushes = data.select(".push");
        Element last = pushes.last();
        Log.d(TAG, "HTMLParse: last: "+last);
        int limit = pushes.size()-1;
        ArrayList<String> donelist = new ArrayList<>();
        for(int i =0; i <20;){
            if(pushes.get(limit).select("span").get(2).text().contains("Done") ||
                    pushes.get(limit).select("span").get(2).text().contains("done")){
                donelist.add(pushes.get(limit).select("span").get(1).text());
            }
            if(pushes.get(limit).select("span").get(2).text().contains("友誼")) {
                String Done = "Done: No";
                if(donelist.contains(pushes.get(limit).select("span").get(1).text())){
                    Done = "Done: Yes";
                    donelist.remove(pushes.get(limit).select("span").get(1).text());
                }
                stack.add(new AquiredData(
                        pushes.get(limit).select("span").get(1).text(),
                        pushes.get(limit).select("span").get(2).text(),
                        pushes.get(limit).select("span").get(3).text(),
                        Done
                ));
                i++;
            }
            limit--;
        }
    }

    private void setLayout(){
        RecyclerView recyclerView1 = findViewById(R.id.RecyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new DataAdapter(stack,this));
    }

}
