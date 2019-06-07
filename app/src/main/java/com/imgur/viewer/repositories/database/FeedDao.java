package com.imgur.viewer.repositories.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.imgur.viewer.repositories.database.model.FeedItem;

import java.util.List;

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feeds")
    List<FeedItem> getItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(FeedItem item);
}
