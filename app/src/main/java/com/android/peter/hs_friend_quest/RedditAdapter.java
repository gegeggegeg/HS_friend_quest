package com.android.peter.hs_friend_quest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RedditAdapter extends RecyclerView.Adapter<RedditHolder> {

    private ArrayList<AquiredData> list;
    private Context context;

    public RedditAdapter(ArrayList<AquiredData> list, Context context) {
        super();
        this.list = list;
        this.context =context;
    }

    @NonNull
    @Override
    public RedditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new RedditHolder(inflater.inflate(R.layout.reddit_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RedditHolder holder, int position) {
        holder.setTextID(list.get(position).getID());
        holder.setTextContent(list.get(position).getCONTENT().substring(2));
        holder.setTextTime(list.get(position).getTIME());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
