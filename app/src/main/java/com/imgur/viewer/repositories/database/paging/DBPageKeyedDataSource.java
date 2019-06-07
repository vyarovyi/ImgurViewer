package com.imgur.viewer.repositories.database.paging;

import androidx.paging.PageKeyedDataSource;

import com.imgur.viewer.repositories.database.FeedDao;
import com.imgur.viewer.repositories.database.model.FeedItem;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class DBPageKeyedDataSource extends PageKeyedDataSource<String, FeedItem> {

    private final FeedDao feedDao;
    public DBPageKeyedDataSource(FeedDao dao) {
        feedDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, FeedItem> callback) {
        List<FeedItem> items = feedDao.getItems();
        if(items.size() != 0) {
            callback.onResult(items, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, FeedItem> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, FeedItem> callback) {
    }
}
