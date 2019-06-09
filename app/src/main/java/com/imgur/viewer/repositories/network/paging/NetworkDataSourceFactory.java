package com.imgur.viewer.repositories.network.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.imgur.viewer.repositories.database.model.FeedItem;

import io.reactivex.subjects.ReplaySubject;

public class NetworkDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<NetworkPageKeyedDataSource> networkState;
    private NetworkPageKeyedDataSource networkDataSource;
    public NetworkDataSourceFactory() {
        this.networkState = new MutableLiveData<>();
        networkDataSource = new NetworkPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkState.postValue(networkDataSource);
        return networkDataSource;
    }

    public MutableLiveData<NetworkPageKeyedDataSource> getNetworkState() {
        return networkState;
    }

    public ReplaySubject<FeedItem> getItems() {
        return networkDataSource.getItems();
    }

}
