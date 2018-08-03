package com.Peter.chen.hs_friend_quest.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.Peter.chen.hs_friend_quest.DataBase.PwnEntity;
import com.Peter.chen.hs_friend_quest.R;
import com.Peter.chen.hs_friend_quest.viewModel.PwnViewModel;

public class PwnFragment extends Fragment {

    private RecyclerView recyclerView3;
    private ProgressBar progressBar3;
    private static View result;
    private PwnViewModel mPwnViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.pwn_layout,container,false);
        mPwnViewModel = ViewModelProviders.of(this).get(PwnViewModel.class);
        recyclerView3 = result.findViewById(R.id.RecyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        final PwnAdapter adapter = new PwnAdapter();
        recyclerView3.setAdapter(adapter);
        progressBar3 =result.findViewById(R.id.progressbar3);
        mPwnViewModel.getPagedlist().observe(this, new Observer<PagedList<PwnEntity>>() {
            @Override
            public void onChanged(@Nullable PagedList<PwnEntity> pwnEntities) {
                adapter.submitList(pwnEntities);
                adapter.notifyDataSetChanged();
            }
        });
        mPwnViewModel.isloading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean)
                    progressBar3.setVisibility(View.VISIBLE);
                else
                    progressBar3.setVisibility(View.INVISIBLE);
            }
        });
        return result;
    }

}
