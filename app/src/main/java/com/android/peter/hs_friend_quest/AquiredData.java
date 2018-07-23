package com.android.peter.hs_friend_quest;

public class AquiredData {
    String ID;
    String CONTENT;
    String TIME;
    String Done;

    public AquiredData(String ID, String CONTENT, String TIME, String Done) {
        this.ID = ID;
        this.CONTENT = CONTENT;
        this.TIME = TIME;
        this.Done = Done;
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

    public String getDone() {
        return Done;
    }

    public void setDone(String done) {
        Done = done;
    }
}
