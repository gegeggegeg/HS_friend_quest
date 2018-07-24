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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PwnFragment extends Fragment {

    private static final String TAG = "PwnFragment";
    private static final String BASE_URL = "https://www.hearthpwn.com/";
    private RecyclerView recyclerView3;
    private ProgressBar progressBar3;
    private static View result;
    private static ArrayList<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        list = new ArrayList<>();
        RequestData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.pwn_layout,container,false);
        recyclerView3 = result.findViewById(R.id.RecyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3.setAdapter(new PwnAdapter(list,getContext()));
        progressBar3 =result.findViewById(R.id.progressbar3);
        if(list.isEmpty())
            progressBar3.setVisibility(View.VISIBLE);
        else
            progressBar3.setVisibility(View.INVISIBLE);

        return result;
    }

    private void RequestData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        PwnService service = retrofit.create(PwnService.class);
        Call<ResponseBody> call = service.url();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    HTMLParse(response.body().string());
                    recyclerView3.setAdapter(new PwnAdapter(list,getContext()));
                    progressBar3.setVisibility(View.INVISIBLE);
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
        Elements Top = data.select("div.p-comment-content").select("div.j-comment-body");
        for(int i =0; i<Top.size(); i++){
           if(!Top.get(i).text().toLowerCase().contains("done")){
               list.add(Top.get(i).text());
           }
        }
    }
}
