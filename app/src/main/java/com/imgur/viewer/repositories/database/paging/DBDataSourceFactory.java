package com.imgur.viewer.repositories.database.paging;

import androidx.paging.DataSource;

import com.imgur.viewer.repositories.database.FeedDao;

public class DBDataSourceFactory extends DataSource.Factory {

    private DBPageKeyedDataSource pageKeyedDataSource;
    public DBDataSourceFactory(FeedDao dao) {
        pageKeyedDataSource = new DBPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return pageKeyedDataSource;
    }

}
