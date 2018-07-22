package com.android.peter.hs_friend_quest;

import android.content.SharedPreferences;
import android.util.Log;

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
    private static final String BASE_URL = "https://www.ptt.cc/bbs/";
    ArrayList<AquiredData> stack = new ArrayList<>();

    @Override
    public boolean onStartJob(JobParameters job) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        CallService service = retrofit.create(CallService.class);
        Call<ResponseBody> call = service.url();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    HTMLParse(response.body().string());
                    if(stack != null){
                        sendNotification();
                    }
                }catch (Exception e){
                    Log.e(TAG, "onResponse: "+e.getMessage() );
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    private void HTMLParse(String body){
        Document data = Jsoup.parse(body);
        Elements pushes = data.select(".push");
        Element last = pushes.last();
        String last_content = pushes.last().select("span").get(2).text();
        SharedPreferences setting = getSharedPreferences("setting",0);
        SharedPreferences.Editor editor = setting.edit();
        Log.d(TAG, "HTMLParse: last: "+last);
        int limit = pushes.size();
        while(pushes.get(limit).select("span").get(2).text() != setting.getString("last_content",last_content)) {
            stack.add(new AquiredData(
                    pushes.get(limit).select("span").get(1).text(),
                    pushes.get(limit).select("span").get(2).text(),
                    pushes.get(limit).select("span").get(3).text()
            ));
            limit--;
        }
        editor.putString("last_content",last_content);
        editor.apply();
    }
    private void sendNotification() {
        for(AquiredData data: stack){
            if(data.getCONTENT().contains("友誼")){

            }
        }
        stack.clear();
    }
}
