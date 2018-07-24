package com.android.peter.hs_friend_quest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class RedditFragment extends Fragment {
    private static final String TAG = "RedditFragment";
    private static final String BASE_URL = "https://www.reddit.com/";
    private ArrayList<AquiredData> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void RequestData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        redditService service = retrofit.create(redditService.class);
        Call<ResponseBody> call = service.getCall();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    HTMLParse(response.body().string());
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
        Elements Top = data.select("div.s136il31-0");
        list = new ArrayList<>();
        for (Element element:Top) {
            String name = element.select("a.s1461iz-1").text();
            String content = element.select("p.s570a4-10").text();
            String time =  element.select("a.s1xnagc2-13").select("span").text();
        }
        for(int i = 0; i<30;){
            if(Top.get(i).select("p.s570a4-10").text().contains("one")||
                    Top.get(i).select("p.s570a4-10").text().contains("finish")) {
                continue;
            }
            list.add(new AquiredData(
                    Top.get(i).select("a.s1461iz-1").text(),
                    Top.get(i).select("p.s570a4-10").text(),
                    Top.get(i).select("a.s1xnagc2-13").select("span").text(),
                    "Done: No"
            ));
            Log.d(TAG, "HTMLParse: ID:" +list.get(i).getID());
            Log.d(TAG, "HTMLParse: CONTENT:" +list.get(i).getCONTENT());
            Log.d(TAG, "HTMLParse: TIME: "+list.get(i).getTIME());
            i++;
        }
    }
}
