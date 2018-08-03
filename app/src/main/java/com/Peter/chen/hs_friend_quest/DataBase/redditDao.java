package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface redditDao {

    @Insert
    public long insertPttData(RedditEntity redditEntity);

    @Update
    public int updataePttData(RedditEntity redditEntity);

    @Delete
    public void deletePttData(RedditEntity redditEntity);

    @Query("SELECT * FROM redditTable")
    public LiveData<List<PttEntity>> pullPttDataAll();

    @Query("DELETE FROM redditTable WHERE name = :input")
    public void deleteNameData(String input);

    @Query("SELECT * FROM redditTable")
    public android.arch.paging.DataSource.Factory<Integer,RedditEntity> getPagerAll();

    @Query("DELETE FROM redditTable")
    public void deleteAllData();
}