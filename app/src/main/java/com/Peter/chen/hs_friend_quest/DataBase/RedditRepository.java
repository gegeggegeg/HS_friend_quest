package com.Peter.chen.hs_friend_quest.DataBase;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;

import com.Peter.chen.hs_friend_quest.network.redditService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RedditRepository {

    private static final String TAG = "RedditRepository";
    private static final String BASE_URL = "https://www.reddit.com/";
    private static ArrayList<RedditEntity> redditlist;
    private  redditDao mRedditDao;
    private MutableLiveData<Boolean> isloading;


    public RedditRepository(Application application) {
        RedditRoomDataBase dataBase = Room.databaseBuilder(application, RedditRoomDataBase.class,"Reddit").build();
        mRedditDao = dataBase.redditdao();
        redditlist = new ArrayList<>();
        isloading = new MutableLiveData<>();
        isloading.postValue(true);
    }

    public DataSource.Factory<Integer,RedditEntity> getData(){
        return mRedditDao.getPagerAll();
    }

    public void fetchData(){
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
        Elements Top = data.select("div.s136il31-0");
        int i = 0;
        while (redditlist.size()<30){
            if (!Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("done") &&
                    !Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("finish")&&
                    !Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("complete")&&
                    !Top.get(i).select("a.s1461iz-1").text().isEmpty()&&
                    Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("#") &&
                    (Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("trade")||
                            Top.get(i).select("p.s570a4-10").text().toLowerCase().contains("quest"))) {
                try {
                    String region = checkRegion(Top.get(i).select("p.s570a4-10").text());
                    redditlist.add(new RedditEntity(
                            Top.get(i).select("a.s1461iz-1").text(),
                            Top.get(i).select("p.s570a4-10").text(),
                            Top.get(i).select("a.s1xnagc2-13").select("span").text(),
                            region
                    ));
                    Log.d(TAG, "HTMLParse: ID:" + redditlist.get(i).getName());
                    Log.d(TAG, "HTMLParse: CONTENT:" + redditlist.get(i).getContent());
                    Log.d(TAG, "HTMLParse: TIME: " + redditlist.get(i).getTime());

                }catch (Exception e){
                    Log.e(TAG, "HTMLParse: "+e.getMessage() );
                }
            }
            i++;
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

    public void insertFetchedData(){
        insertTask task = new insertTask();
        task.execute(redditlist);
    }

    private  class insertTask extends AsyncTask<ArrayList<RedditEntity>,Void, Void> {
        @Override
        protected Void doInBackground(ArrayList<RedditEntity>... arrayLists) {
            mRedditDao.deleteAllData();
            for(RedditEntity enity: arrayLists[0]){
                Log.d(TAG, "insertFetchedData: insert data: "+enity.getName());
                long id = mRedditDao.insertPttData(enity);
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
