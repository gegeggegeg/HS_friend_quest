package com.Peter.chen.hs_friend_quest.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.Peter.chen.hs_friend_quest.DataBase.RedditEntity;
import com.Peter.chen.hs_friend_quest.DataBase.RedditRepository;

public class RedditViewModel extends AndroidViewModel {
    private RedditRepository mRedditRepository;
    private LiveData<PagedList<RedditEntity>> pagedlist;


    public RedditViewModel(@NonNull Application application) {
        super(application);
        mRedditRepository = new RedditRepository(application);
        mRedditRepository.fetchData();
        pagedlist = new LivePagedListBuilder<>(mRedditRepository.getData(),10).build();
    }

    public LiveData<PagedList<RedditEntity>> getPagedlist(){
        return pagedlist;
    }

    public MutableLiveData<Boolean> isloading(){
        return mRedditRepository.getIsloading();
    }

}
