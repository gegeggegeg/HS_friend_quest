package com.android.peter.hs_friend_quest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class DataHolder extends RecyclerView.ViewHolder {

    private TextView textID;
    private TextView textContent;
    private TextView textTime;
    private TextView textDone;
    public DataHolder(View itemView) {
        super(itemView);
        textID = itemView.findViewById(R.id.textID);
        textContent = itemView.findViewById(R.id.textContent);
        textTime = itemView.findViewById(R.id.textTime);
        textDone = itemView.findViewById(R.id.textDone);
    }

    public void setTextID(String ID) {
        textID.setText(ID);
    }

    public void setTextContent(String Content) {
        textContent.setText(Content);
    }

    public void setTextTime(String Time) {
        textTime.setText(Time);
    }

    public void setTextDone(String Done) {
        textDone.setText(Done);
    }

}
