package com.android.peter.hs_friend_quest;

import android.content.Context;
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

public class PTTFragment extends Fragment {

    private static final String TAG = "PTTFragment";
    private ArrayList<AquiredData> stack = new ArrayList<>();
    private static View result;
    private Call<ResponseBody> call;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.ptt_layout,container,false);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null)
            RequestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(call != null)
            call.cancel();
    }

    private void RequestData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestJobService.BASE_URL)
                .build();
        CallService service = retrofit.create(CallService.class);
        call = service.url();
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
        RecyclerView recyclerView1 = result.findViewById(R.id.RecyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(new DataAdapter(stack,getContext()));
    }
}
