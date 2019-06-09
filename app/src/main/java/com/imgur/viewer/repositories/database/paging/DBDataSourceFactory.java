package com.imgur.viewer.repositories.database.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.imgur.viewer.repositories.database.FeedDao;
import com.imgur.viewer.repositories.network.paging.NetworkPageKeyedDataSource;

public class DBDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<DBPageKeyedDataSource> networkState;
    private DBPageKeyedDataSource pageKeyedDataSource;

    public DBDataSourceFactory(FeedDao dao) {
        networkState = new MutableLiveData<>();
        pageKeyedDataSource = new DBPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        networkState.postValue(pageKeyedDataSource);
        return pageKeyedDataSource;
    }

    public MutableLiveData<DBPageKeyedDataSource> getNetworkState() {
        return networkState;
    }
}
