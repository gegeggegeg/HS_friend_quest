package com.Peter.chen.hs_friend_quest.DataBase;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;
import com.Peter.chen.hs_friend_quest.network.PwnService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PwnRepository {

    private static final String BASE_URL = "https://www.hearthpwn.com/";
    private static final String TAG = "PwnRepository";
    private static ArrayList<PwnEntity> list;
    private pwnDao mPwnDao;
    private MutableLiveData<Boolean> isloading;

    public PwnRepository(Application application) {
        PwnRoomDataBase dataBase = Room.databaseBuilder(application,PwnRoomDataBase.class, "HearthPwn").build();
        mPwnDao = dataBase.pwndao();
        list = new ArrayList<>();
        isloading = new MutableLiveData<>();
        isloading.postValue(true);
    }

    public void fetchData(){
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
                    insertFetchedData();
                    isloading.setValue(false);
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
                list.add(new PwnEntity(Top.get(i).text(),checkRegion(Top.get(i).text())));
            }
        }
    }

    private String checkRegion(String content) {
        if(content.toLowerCase().contains("asia"))
            return "Asia";
        else if (content.toLowerCase().contains("na")||content.toLowerCase().contains("us"))
            return "NA";
        else if (content.toLowerCase().contains("eu"))
            return "EU";
        else
            return "Unknown";
    }

    private class insertTask extends AsyncTask<ArrayList<PwnEntity>, Void, Void>{
        @Override
        protected Void doInBackground(ArrayList<PwnEntity>... arrayLists) {
            mPwnDao.deleteAllData();
            for(PwnEntity enity: arrayLists[0]){
                long id = mPwnDao.insertpwnData(enity);
                enity.set_id(id);
            }
            return null;
        }
    }

    public void insertFetchedData(){
        insertTask task = new insertTask();
        task.execute(list);
    }

    public DataSource.Factory<Integer,PwnEntity> getData(){
        return mPwnDao.getPagerAll();
    }

    public MutableLiveData<Boolean> getIsloading() {
        return isloading;
    }
}
