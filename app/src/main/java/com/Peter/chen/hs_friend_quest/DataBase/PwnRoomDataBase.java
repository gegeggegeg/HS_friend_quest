package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PwnEntity.class}, version = 1)
public abstract class PwnRoomDataBase extends RoomDatabase {
    public abstract pwnDao pwndao();
}
