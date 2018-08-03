package com.Peter.chen.hs_friend_quest.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.Peter.chen.hs_friend_quest.DataBase.PttEntity;
import com.Peter.chen.hs_friend_quest.DataBase.PttRepository;

public class PttViewModel extends AndroidViewModel {
    private static final String TAG = "PttViewModel";
    private PttRepository mPttRepository;
    private LiveData<PagedList<PttEntity>> pagedlist;

    public PttViewModel(@NonNull Application application) {
        super(application);
        mPttRepository = new PttRepository(application);
        mPttRepository.fetchData();
        pagedlist = new LivePagedListBuilder<>(mPttRepository.getData(),50).build();
    }

    public LiveData<PagedList<PttEntity>> getPagedlist(){
        Log.d(TAG, "getPagedlist: "+(pagedlist == null));
        return pagedlist;
    }

    public MutableLiveData<Boolean> isloading(){
        return mPttRepository.getIsloading();
    }
}
