package com.Peter.chen.hs_friend_quest.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.Peter.chen.hs_friend_quest.DataBase.PwnEntity;
import com.Peter.chen.hs_friend_quest.DataBase.RedditEntity;
import com.Peter.chen.hs_friend_quest.R;
import com.Peter.chen.hs_friend_quest.ui.PwnHolder;

import java.util.ArrayList;

public class PwnAdapter extends PagedListAdapter<PwnEntity,PwnHolder> {

    public PwnAdapter() {
        super(diffCallBack);
    }

    @NonNull
    @Override
    public PwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new PwnHolder(inflater.inflate(R.layout.pwn_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PwnHolder holder, int position) {
        holder.setPwnText(getItem(position).getContent());
        holder.setTextRegion(getItem(position).getRegion());
    }

    private static DiffUtil.ItemCallback<PwnEntity> diffCallBack = new DiffUtil.ItemCallback<PwnEntity>() {
        @Override
        public boolean areItemsTheSame(PwnEntity oldItem, PwnEntity newItem) {
            return oldItem.get_id() == newItem.get_id();
        }

        @Override
        public boolean areContentsTheSame(PwnEntity oldItem, PwnEntity newItem) {
            return oldItem.getContent().equals(newItem.getContent());
        }
    };
}
