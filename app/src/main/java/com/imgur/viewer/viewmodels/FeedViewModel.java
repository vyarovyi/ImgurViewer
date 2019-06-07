package com.imgur.viewer.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.imgur.viewer.repositories.database.FeedsDBRepository;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;

import io.reactivex.annotations.NonNull;

public class FeedViewModel extends AndroidViewModel {
    private FeedsDBRepository repository;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        repository = FeedsDBRepository.getInstance();
    }

    public LiveData<PagedList<FeedItem>> getItems() {
        return repository.getItems();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}
