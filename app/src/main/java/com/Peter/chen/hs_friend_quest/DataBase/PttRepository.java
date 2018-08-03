package com.Peter.chen.hs_friend_quest.DataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;

import com.Peter.chen.hs_friend_quest.network.CallService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PttRepository {

    public static final String BASE_URL = "https://www.ptt.cc/bbs/";
    private Call<ResponseBody> call;

    private static final String TAG = "PttRepository";
    private  pttDAO mpttDA0;
    private ArrayList<PttEntity> pttlist;
    private MutableLiveData<Boolean> isloading;


    public PttRepository(Application application) {
        PttRoomDatabase database = Room.databaseBuilder(application, PttRoomDatabase.class,"Ptt").build();
        mpttDA0 = database.pttdao();
        pttlist = new ArrayList<>();
        isloading = new MutableLiveData<>();
        isloading.postValue(true);
    }


    public void insertFetchedData(){
        insertTask task = new insertTask();
        task.execute(pttlist);
    }

    public LiveData<List<PttEntity>> repoPullAllData(){
        return  mpttDA0.pullPttDataAll();
    }

    public void repoDeleteData(String name){
        mpttDA0.deleteNameData(name);
    }

    public void updateData(PttEntity enity){
        mpttDA0.updataePttData(enity);
    }

    public DataSource.Factory<Integer,PttEntity> getData(){
        return mpttDA0.getPagerAll();
    }


    public void fetchData(){
        Log.d(TAG, "fetchData: fetch data");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).build();
        CallService service = retrofit.create(CallService.class);
        call =service.url();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: response success");
                    HTMLParse(response.body().string());
                    insertFetchedData();
                    isloading.setValue(false);
                }catch (IOException e){
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
            if(pushes.get(limit).select("span").get(2).text().toLowerCase().contains("done")){
                donelist.add(pushes.get(limit).select("span").get(1).text());
            }
            if(pushes.get(limit).select("span").get(2).text().contains("友誼")||
                    pushes.get(limit).select("span").get(2).text().contains("互解")) {
                String region = checkRegion(pushes.get(limit).select("span").get(2).text());
                pttlist.add(new PttEntity(
                        pushes.get(limit).select("span").get(1).text(),
                        pushes.get(limit).select("span").get(2).text(),
                        pushes.get(limit).select("span").get(3).text(),
                        region
                ));
                i++;
            }
            limit--;
        }
        for(String done: donelist){
            for(int index = 0; index< pttlist.size(); index++){
                if(pttlist.get(index).getName().equals(done)) {
                    pttlist.remove(index);
                }
            }
        }
        Log.d(TAG, "HTMLParse: data transformed: " +pttlist.size());
    }

    private String checkRegion(String content) {
        if(content.contains("亞"))
            return "Asia";
        else if (content.contains("美"))
            return "NA";
        else if (content.contains("歐"))
            return "EU";
        else
            return "Unknown";
    }

    private  class insertTask extends AsyncTask<ArrayList<PttEntity>,Void, Void>{

        @Override
        protected Void doInBackground(ArrayList<PttEntity>... arrayLists) {
            mpttDA0.deleteAllData();
            for(PttEntity enity: arrayLists[0]){
                Log.d(TAG, "insertFetchedData: insert data: "+enity.getName());
                long id = mpttDA0.insertPttData(enity);
                enity.set_id(id);
            }
            Log.d(TAG, "doInBackground: "+ (getData()== null));
            return null;
        }
    }
    public MutableLiveData<Boolean> getIsloading() {
        return isloading;
    }
}
