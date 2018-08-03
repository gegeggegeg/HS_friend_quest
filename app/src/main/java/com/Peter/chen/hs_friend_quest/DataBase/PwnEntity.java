package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "pwnTable")
public class PwnEntity {

    public PwnEntity(String content, String region) {
        this.content = content;
        this.region = region;
    }

    @PrimaryKey(autoGenerate = true)
    private long _id;

    @ColumnInfo(name = "cotent")
    private String content;

    @ColumnInfo(name = "region")
    private String region;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}