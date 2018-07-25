package com.Peter.chen.hs_friend_quest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataHolder> {
    private static final String TAG = "DataAdapter";

    private ArrayList<AquiredData> list;
    private Context context;

    public DataAdapter(ArrayList<AquiredData> list, Context context) {
        this.list =list;
        this.context = context;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new DataHolder(inflater.inflate(R.layout.holder_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        holder.setTextID(list.get(position).getID());
        holder.setTextContent(list.get(position).getCONTENT().substring(2));
        holder.setTextTime(list.get(position).getTIME());
        holder.setTextRegion(list.get(position).getREGION());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
