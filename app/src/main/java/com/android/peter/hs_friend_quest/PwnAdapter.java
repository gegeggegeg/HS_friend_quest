package com.android.peter.hs_friend_quest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PwnAdapter extends RecyclerView.Adapter<PwnHolder> {

    private ArrayList<String> list;
    private Context context;

    public PwnAdapter(ArrayList<String> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public PwnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new PwnHolder(inflater.inflate(R.layout.pwn_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PwnHolder holder, int position) {
        holder.setPwnText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
