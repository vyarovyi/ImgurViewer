package com.imgur.viewer.repositories.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.imgur.viewer.ImgurApplication;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;
import com.imgur.viewer.repositories.network.FeedsNetworkRepository;
import com.imgur.viewer.repositories.network.paging.NetworkDataSourceFactory;

import io.reactivex.schedulers.Schedulers;

public class FeedsDBRepository {

    private static FeedsDBRepository sInstance;

    public static FeedsDBRepository getInstance() {
        if (sInstance == null) {
            sInstance = new FeedsDBRepository();
        }
        return sInstance;
    }

    final private FeedsNetworkRepository network;
    final private FeedsDatabase database;
    final private MediatorLiveData liveDataMerger;

    private FeedsDBRepository() {

        NetworkDataSourceFactory dataSourceFactory = new NetworkDataSourceFactory();

        network = new FeedsNetworkRepository(dataSourceFactory, boundaryCallback);
        database = FeedsDatabase.getInstance(ImgurApplication.getInstance());
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getItems(), liveDataMerger::setValue);

        dataSourceFactory.getItems().
                observeOn(Schedulers.io()).
                subscribe(item -> database.feedDao().insertItem(item));

    }

    private PagedList.BoundaryCallback<FeedItem> boundaryCallback = new PagedList.BoundaryCallback<FeedItem>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getItems(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(database.getItems());
            });
        }
    };

    public LiveData<PagedList<FeedItem>> getItems() {
        return liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}
