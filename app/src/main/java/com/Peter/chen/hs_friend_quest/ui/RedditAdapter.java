package com.Peter.chen.hs_friend_quest.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.Peter.chen.hs_friend_quest.DataBase.RedditEntity;
import com.Peter.chen.hs_friend_quest.R;

public class RedditAdapter extends PagedListAdapter<RedditEntity,DataHolder> {

    private Context context;

    public RedditAdapter(Context context) {
        super(diffCallBack);
        this.context =context;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new DataHolder(inflater.inflate(R.layout.holder_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        holder.setTextID(getItem(position).getName());
        holder.setTextContent(getItem(position).getContent());
        holder.setTextTime(getItem(position).getTime());
        holder.setTextRegion(getItem(position).getRegion());
    }

    private static DiffUtil.ItemCallback<RedditEntity> diffCallBack = new DiffUtil.ItemCallback<RedditEntity>() {
        @Override
        public boolean areItemsTheSame(RedditEntity oldItem, RedditEntity newItem) {
            return oldItem.get_id() == newItem.get_id();
        }

        @Override
        public boolean areContentsTheSame(RedditEntity oldItem, RedditEntity newItem) {
            return oldItem.getContent().equals(newItem.getContent());
        }
    };
}
