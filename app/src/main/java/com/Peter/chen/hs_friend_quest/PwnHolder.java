package com.Peter.chen.hs_friend_quest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PwnHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "PwnHolder";
    private TextView pwnText;
    private TextView pwnRegion;

    public PwnHolder(View itemView) {
        super(itemView);
        pwnText = itemView.findViewById(R.id.pwnText);
        pwnRegion = itemView.findViewById(R.id.pwnRegion);
        itemView.setOnClickListener(this);
    }

    public void setPwnText(String text){
        pwnText.setText(text);
    }

    public void setTextRegion(String Region) {
        pwnRegion.setText(Region);
    }

    private String BattletagIdentifier (String input){
        String output = " ";
        try {
            int postion = input.indexOf("#");
            int front_space = postion;
            while (input.charAt(front_space) != ' ')
                front_space--;
            output = input.substring(front_space,postion+5);
        }catch (Exception e){
            Log.e(TAG, "BattletagIdentifier: "+e.getMessage());
        }
        return output;
    }

    @Override
    public void onClick(View view) {
        String battletag = BattletagIdentifier(pwnText.getText().toString());
        if(!battletag.isEmpty()){
            ClipboardManager clipboard = (ClipboardManager)view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("battletage",battletag);
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(view.getContext(),battletag+" has been copied to clipboard",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(view.getContext(),"Can not find Battletag",Toast.LENGTH_SHORT).show();
        }
    }
}
