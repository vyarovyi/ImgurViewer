package com.imgur.viewer.repositories.network.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.imgur.viewer.repositories.database.model.FeedItem;

import io.reactivex.subjects.ReplaySubject;

public class NetworkDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<NetworkPageKeyedDataSource> networkStatus;
    private NetworkPageKeyedDataSource networkDataSource;
    public NetworkDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        networkDataSource = new NetworkPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(networkDataSource);
        return networkDataSource;
    }

    public MutableLiveData<NetworkPageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<FeedItem> getItems() {
        return networkDataSource.getItems();
    }

}
