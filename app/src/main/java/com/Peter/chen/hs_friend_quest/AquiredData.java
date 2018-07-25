package com.Peter.chen.hs_friend_quest;

public class AquiredData {
    String ID;
    String CONTENT;
    String TIME;
    String REGION;

    public AquiredData(String ID, String CONTENT, String TIME, String REGION) {
        this.ID = ID;
        this.CONTENT = CONTENT;
        this.TIME = TIME;
        this.REGION = REGION;
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

    public String getREGION() {
        return REGION;
    }

    public void setREGION(String REGION) {
        this.REGION = REGION;
    }
}
