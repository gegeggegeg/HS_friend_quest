package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PttEntity.class}, version = 1)
public abstract class PttRoomDatabase extends RoomDatabase {
    public abstract pttDAO pttdao();
}
