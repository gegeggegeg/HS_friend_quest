package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "redditTable")
public class RedditEntity {

    public RedditEntity(String name, String content, String time, String region) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.region = region;
    }

    @PrimaryKey (autoGenerate = true)
    private long _id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "region")
    private String region;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
