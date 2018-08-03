package com.Peter.chen.hs_friend_quest.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
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

import com.Peter.chen.hs_friend_quest.DataBase.RedditEntity;
import com.Peter.chen.hs_friend_quest.DataBase.RedditRepository;
import com.Peter.chen.hs_friend_quest.R;
import com.Peter.chen.hs_friend_quest.network.redditService;
import com.Peter.chen.hs_friend_quest.ui.PttAdapter;
import com.Peter.chen.hs_friend_quest.ui.RedditAdapter;
import com.Peter.chen.hs_friend_quest.viewModel.PttViewModel;
import com.Peter.chen.hs_friend_quest.viewModel.RedditViewModel;

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
    private View result;
    private RecyclerView recyclerView;
    private ProgressBar progressBar2;
    private RedditViewModel mRedditViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRedditViewModel = ViewModelProviders.of(this).get(RedditViewModel.class);
        result = inflater.inflate(R.layout.reddit_layout,container,false);
        recyclerView = result.findViewById(R.id.RecyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar2 = result.findViewById(R.id.progressbar2);
        final RedditAdapter adapter = new RedditAdapter(getContext());
        recyclerView.setAdapter(adapter);
        mRedditViewModel.getPagedlist().observe(this, new Observer<PagedList<RedditEntity>>() {
            @Override
            public void onChanged(@Nullable PagedList<RedditEntity> redditEntities) {
                adapter.submitList(redditEntities);
                adapter.notifyDataSetChanged();
            }
        });
        mRedditViewModel.isloading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)
                    progressBar2.setVisibility(View.VISIBLE);
                else
                    progressBar2.setVisibility(View.INVISIBLE);
            }
        });
        return result;
    }
}
