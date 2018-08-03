package com.Peter.chen.hs_friend_quest.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RedditEntity.class}, version = 1)
public abstract class RedditRoomDataBase extends RoomDatabase {
    public abstract redditDao redditdao();
}
