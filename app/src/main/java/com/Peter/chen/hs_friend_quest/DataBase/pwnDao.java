package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface pwnDao {

    @Insert
    public long insertpwnData(PwnEntity pwnEntity);

    @Update
    public int updatepwnData(PwnEntity pwnEntity);

    @Query("SELECT * FROM pwnTable")
    public LiveData<List<PwnEntity>> pullPttDataAll();

    @Query("SELECT * FROM pwnTable")
    public android.arch.paging.DataSource.Factory<Integer,PwnEntity> getPagerAll();

    @Query("DELETE FROM pwnTable")
    public void deleteAllData();

}
