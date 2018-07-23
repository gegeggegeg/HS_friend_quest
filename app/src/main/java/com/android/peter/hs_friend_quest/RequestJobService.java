package com.android.peter.hs_friend_quest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

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

public class RequestJobService extends JobService {

    private static final String TAG = "RequestJobService";
    public static final String BASE_URL = "https://www.ptt.cc/bbs/";
    ArrayList<AquiredData> stack = new ArrayList<>();
    private Call<ResponseBody> call;


    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob: Job Start");
        final JobParameters jobParameters = job;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        CallService service = retrofit.create(CallService.class);
        call = service.url();
        Log.d(TAG, "onStartJob: Enqueue Call");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    HTMLParse(response.body().string());
                    if(stack != null){
                        sendNotification();
                    }
                    //Log.d(TAG, "onResponse: try to invoke jobfinished");
                    Toast.makeText(RequestJobService.this, "Done", Toast.LENGTH_SHORT).show();
                    jobFinished(jobParameters,false);
                }catch (Exception e){
                    Log.e(TAG, "onResponse: "+e.getMessage() );
                    //jobFinished(jobParameters,false);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                //jobFinished(jobParameters,false);
            }
        });
        //jobFinished(job,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob: job finished");
        if(call != null)
            call.cancel();
        return false;
    }

    private void HTMLParse(String body){
        Document data = Jsoup.parse(body);
        Elements pushes = data.select(".push");
        Element last = pushes.last();
        String last_content = pushes.last().select("span").get(2).text();
        Log.d(TAG, "HTMLParse: last_content: "+last_content);
        SharedPreferences setting = getSharedPreferences("setting",0);
        SharedPreferences.Editor editor = setting.edit();
        Log.d(TAG, "HTMLParse: last: "+last);
        int limit = pushes.size()-1;
        Log.d(TAG, "HTMLParse: limit: "+limit);
        Log.d(TAG, "HTMLParse: condition: " + !pushes.get(limit).select("span").get(2).text().equals(setting.getString("last_content",last_content)));
        while(!pushes.get(limit).select("span").get(2).text().equals(setting.getString("last_content",last_content))) {
            Log.d(TAG, "HTMLParse: SharedPreference: "+ setting.getString("last_content",last_content) );
            stack.add(new AquiredData(
                    pushes.get(limit).select("span").get(1).text(),
                    pushes.get(limit).select("span").get(2).text(),
                    pushes.get(limit).select("span").get(3).text(),
                    null
            ));
            Log.d(TAG, "HTMLParse: ID: "+ pushes.get(limit).select("span").get(1).text());
            Log.d(TAG, "HTMLParse: content: " + pushes.get(limit).select("span").get(2).text());
            Log.d(TAG, "HTMLParse: time: "+ pushes.get(limit).select("span").get(3).text());
            limit--;
            Log.d(TAG, "HTMLParse: limit--: "+ limit);
        }
        editor.putString("last_content",last_content);
        editor.apply();
    }

    private void sendNotification() {
        for(AquiredData data: stack){
            NotificationChannel channel = new NotificationChannel("Channel1","mychannel", NotificationManager.IMPORTANCE_DEFAULT);
            Notification notification = new Notification.Builder(this,"Channel1")
                    .setContentTitle("Friend Quest notification")
                    .setContentText(data.getID()+": "+data.getCONTENT()+" "+data.getTIME())
                    .setSmallIcon(R.drawable.ic_notification).build();
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            manager.notify(1234,notification);
        }
        Log.d(TAG, "sendNotification: clear stack");
        stack.clear();
    }
}
