package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface pttDAO {

    @Insert
    public long insertPttData(PttEntity pttEntity);

    @Update
    public int updataePttData(PttEntity pttEntity);

    @Delete
    public void deletePttData(PttEntity pttEntity);

    @Query("SELECT * FROM pttTable")
    public LiveData<List<PttEntity>> pullPttDataAll();

    @Query("DELETE FROM pttTable WHERE name = :input")
    public void deleteNameData(String input);

    @Query("SELECT * FROM pttTable")
    public android.arch.paging.DataSource.Factory<Integer,PttEntity> getPagerAll();

    @Query("DELETE FROM pttTable")
    public void deleteAllData();
}
