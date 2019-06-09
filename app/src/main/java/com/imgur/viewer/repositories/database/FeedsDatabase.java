package com.imgur.viewer.repositories.database;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;
import com.imgur.viewer.repositories.database.paging.DBDataSourceFactory;
import com.imgur.viewer.repositories.database.paging.DBPageKeyedDataSource;
import com.imgur.viewer.repositories.network.paging.NetworkPageKeyedDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {FeedItem.class}, version = 1)
public abstract class FeedsDatabase extends RoomDatabase {

    private static FeedsDatabase sInstance;

    public abstract FeedDao feedDao();

    private static final Object sLock = new Object();
    private LiveData<PagedList<FeedItem>> feedLiveData;
    private LiveData<NetworkState> networkState;

    public static FeedsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FeedsDatabase.class, "ImgurFeeds.db")
                        .build();
                sInstance.init();

            }
            return sInstance;
        }
    }

    private void init() {
        DBDataSourceFactory dataSourceFactory = new DBDataSourceFactory(feedDao());
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkState(),
                (Function<DBPageKeyedDataSource, LiveData<NetworkState>>)
                        DBPageKeyedDataSource::getNetworkState);

        //TODO: add pagination from DB
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(2);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        feedLiveData = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<FeedItem>> getItems() {
        return feedLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
