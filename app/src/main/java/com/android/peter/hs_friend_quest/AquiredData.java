package com.android.peter.hs_friend_quest;

public class AquiredData {
    String ID;
    String CONTENT;
    String TIME;

    public AquiredData(String ID, String CONTENT, String TIME) {
        this.ID = ID;
        this.CONTENT = CONTENT;
        this.TIME = TIME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
