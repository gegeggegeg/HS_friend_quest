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

import com.Peter.chen.hs_friend_quest.DataBase.PttEntity;
import com.Peter.chen.hs_friend_quest.R;
import com.Peter.chen.hs_friend_quest.viewModel.PttViewModel;

public class PttFragment extends Fragment {


    private static final String TAG = "PttFragment";
    private PttViewModel mPttViewModel;
    private MutableLiveData<Boolean> isloading;
    private ProgressBar progressBar;
    RecyclerView recyclerView1;
    PttAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        adapter = new PttAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.ptt_layout,container,false);
        mPttViewModel = ViewModelProviders.of(this).get(PttViewModel.class);
        isloading = mPttViewModel.isloading();
        recyclerView1 = result.findViewById(R.id.RecyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = result.findViewById(R.id.progressbar1);
        progressBar.setVisibility(View.INVISIBLE);
        adapter.submitList(mPttViewModel.getPagedlist().getValue());
        recyclerView1.setAdapter(adapter);
        mPttViewModel.getPagedlist().observe(this, new Observer<PagedList<PttEntity>>() {
            @Override
            public void onChanged(@Nullable PagedList<PttEntity> pttEnities) {
                Log.d(TAG, "onChanged: database changed");
                adapter.submitList(pttEnities);
                recyclerView1.setAdapter(adapter);
            }
        });
        isloading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return result;
    }
}
