package com.Peter.chen.hs_friend_quest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RedditHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = "RedditHolder";

    private TextView textID;
    private TextView textContent;
    private TextView textTime;
    private TextView textRegion;

    public RedditHolder(View itemView) {
        super(itemView);
        textID = itemView.findViewById(R.id.textIDReddit);
        textContent = itemView.findViewById(R.id.textContentReddit);
        textTime = itemView.findViewById(R.id.textTimeReddit);
        textRegion = itemView.findViewById(R.id.textRegionReddit);
        itemView.setOnClickListener(this);
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

    public void setTextRegion(String Region) {
        textRegion.setText(Region);
    }

    private String BattletagIdentifier (String input){
        String output = " ";
        try {
            int postion = input.indexOf("#");
            int front_spcae = postion;
            while (input.charAt(front_spcae) != ' '&& input.charAt(front_spcae) != ',' && front_spcae >0)
                front_spcae--;
            Log.e(TAG, "BattletagIdentifier: front_spacce: "+front_spcae );
            output = input.substring(front_spcae,postion+5);
            Log.e(TAG, "BattletagIdentifier: "+output);
        }catch (Exception e){
            Log.e(TAG, "BattletagIdentifier: "+e.getMessage());
        }
        return output;
    }

    @Override
    public void onClick(View view) {
        String battletag = BattletagIdentifier(textContent.getText().toString());
        Log.e(TAG, "onClick: "+battletag );
        if(!battletag.isEmpty()){
            ClipboardManager clipboard = (ClipboardManager)view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("battletag",battletag);
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(view.getContext(),battletag+" has been copied to clipboard",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(view.getContext(),"Can not find Battletag",Toast.LENGTH_SHORT).show();
        }
    }
}
