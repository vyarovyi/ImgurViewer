package com.imgur.viewer.repositories.network;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.imgur.viewer.repositories.network.paging.NetworkDataSourceFactory;
import com.imgur.viewer.repositories.network.paging.NetworkPageKeyedDataSource;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeedsNetworkRepository {

    private static final int PAGE_SIZE = 40;

    final private LiveData<PagedList<FeedItem>> feedLiveData;
    final private LiveData<NetworkState> networkState;

    public FeedsNetworkRepository(NetworkDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<FeedItem> boundaryCallback) {

        networkState = Transformations.switchMap(dataSourceFactory.getNetworkState(),
                (Function<NetworkPageKeyedDataSource, LiveData<NetworkState>>)
                        NetworkPageKeyedDataSource::getNetworkState);

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PAGE_SIZE * 2).setPageSize(PAGE_SIZE).build();
        Executor executor = Executors.newFixedThreadPool(2);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        feedLiveData = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }

    public LiveData<PagedList<FeedItem>> getItems() {
        return feedLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
