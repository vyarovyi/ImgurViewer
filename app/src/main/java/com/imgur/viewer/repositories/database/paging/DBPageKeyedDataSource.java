package com.imgur.viewer.repositories.database.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.imgur.viewer.repositories.database.FeedDao;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class DBPageKeyedDataSource extends PageKeyedDataSource<Integer, FeedItem> {

    private final MutableLiveData networkState;
    private final FeedDao feedDao;
    public DBPageKeyedDataSource(FeedDao dao) {
        feedDao = dao;
        networkState = new MutableLiveData();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, FeedItem> callback) {
        networkState.postValue(NetworkState.INITIALIZING);
        List<FeedItem> items = feedDao.getItems();
        if(items.size() != 0) {
            callback.onResult(items, 0, 1);
        } else {
            networkState.postValue(NetworkState.EMPTY);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, final @NonNull LoadCallback<Integer, FeedItem> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FeedItem> callback) {
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }
}
