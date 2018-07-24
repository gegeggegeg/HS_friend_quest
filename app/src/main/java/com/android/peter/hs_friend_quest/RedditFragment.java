package com.android.peter.hs_friend_quest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    private static ArrayList<AquiredData> list;
    private View result;
    private RecyclerView recyclerView;
    private ProgressBar progressBar2;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestData();
        setRetainInstance(true);
        list = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.reddit_layout,container,false);
        recyclerView = result.findViewById(R.id.RecyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar2 = result.findViewById(R.id.progressbar2);
        recyclerView.setAdapter(new RedditAdapter(list, getContext()));
        if(list.isEmpty()){
            Log.d(TAG, "onCreateView: list is null");
            progressBar2.setVisibility(View.VISIBLE);
        }
        else{
            Log.d(TAG, "onCreateView: list is not null");
            progressBar2.setVisibility(View.INVISIBLE);
        }
        return result;
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
                    recyclerView.setAdapter(new RedditAdapter(list,getContext()));
                    progressBar2.setVisibility(View.INVISIBLE);
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
        int i = 0;
        while (list.size()<30){
               String done = "Done: NO";
               if (!Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("done") &&
                       !Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("finish")&&
                    !Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("complete")&&
                    !Top.get(i).select("a.s1461iz-1").text().isEmpty()) {
                   try {
                       list.add(new AquiredData(
                               Top.get(i).select("a.s1461iz-1").text(),
                               Top.get(i).select("p.s570a4-10").text(),
                               Top.get(i).select("a.s1xnagc2-13").select("span").text(),
                               done
                       ));
                       Log.d(TAG, "HTMLParse: ID:" + list.get(i).getID());
                       Log.d(TAG, "HTMLParse: CONTENT:" + list.get(i).getCONTENT());
                       Log.d(TAG, "HTMLParse: TIME: " + list.get(i).getTIME());

               }catch (Exception e){
                       Log.e(TAG, "HTMLParse: "+e.getMessage() );
                   }
           }
           i++;
        }
    }
}
