package com.Peter.chen.hs_friend_quest.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.Peter.chen.hs_friend_quest.DataBase.PwnEntity;
import com.Peter.chen.hs_friend_quest.DataBase.PwnRepository;


public class PwnViewModel extends AndroidViewModel {

    private PwnRepository mPwnRepository;
    private LiveData<PagedList<PwnEntity>> pagedlist;

    public PwnViewModel(@NonNull Application application) {
        super(application);
        mPwnRepository = new PwnRepository(application);
        mPwnRepository.fetchData();
        pagedlist = new LivePagedListBuilder<Integer,PwnEntity>(mPwnRepository.getData(),10).build();
    }

    public LiveData<PagedList<PwnEntity>> getPagedlist(){
        return pagedlist;
    }

    public MutableLiveData<Boolean> isloading(){
        return mPwnRepository.getIsloading();
    }
}
