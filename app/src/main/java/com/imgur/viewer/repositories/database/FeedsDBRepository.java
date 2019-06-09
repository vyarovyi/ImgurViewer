package com.imgur.viewer.repositories.database;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.imgur.viewer.ImgurApplication;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;
import com.imgur.viewer.repositories.network.FeedsNetworkRepository;
import com.imgur.viewer.repositories.network.paging.NetworkDataSourceFactory;
import com.imgur.viewer.repositories.network.paging.NetworkPageKeyedDataSource;

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

    final private MediatorLiveData itemsMerger;
    final private MediatorLiveData networkMerger;

    private FeedsDBRepository() {

        NetworkDataSourceFactory dataSourceFactory = new NetworkDataSourceFactory();

        network = new FeedsNetworkRepository(dataSourceFactory, boundaryCallback);
        database = FeedsDatabase.getInstance(ImgurApplication.getInstance());

        itemsMerger = new MediatorLiveData<>();
        itemsMerger.addSource(network.getItems(), itemsMerger::setValue);

        networkMerger = new MediatorLiveData<>();
        networkMerger.addSource(network.getNetworkState(), networkMerger::setValue);
        networkMerger.addSource(database.getNetworkState(), networkMerger::setValue);


        dataSourceFactory.getItems().
                observeOn(Schedulers.io()).
                subscribe(item -> database.feedDao().insertItem(item));

    }

    private PagedList.BoundaryCallback<FeedItem> boundaryCallback = new PagedList.BoundaryCallback<FeedItem>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            itemsMerger.addSource(database.getItems(), value -> {
                itemsMerger.setValue(value);
                itemsMerger.removeSource(database.getItems());
            });
        }
    };

    public LiveData<PagedList<FeedItem>> getItems() {
        return itemsMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkMerger;
    }
}
