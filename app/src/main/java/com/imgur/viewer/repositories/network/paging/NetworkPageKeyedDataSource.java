package com.imgur.viewer.repositories.network.paging;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.imgur.viewer.ImgurApplication;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.NetworkState;
import com.imgur.viewer.repositories.network.api.NetworkInterface;
import com.imgur.viewer.repositories.network.api.NetworkService;
import com.imgur.viewer.repositories.network.models.FeedResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.ReplaySubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO: require refactoring: remove duplicates in code
public class NetworkPageKeyedDataSource extends PageKeyedDataSource<Integer, FeedItem> {

    private final NetworkInterface networkClient;
    private final MutableLiveData networkState;
    private final ReplaySubject<FeedItem> dataObservable;

    NetworkPageKeyedDataSource() {
        networkClient = NetworkService.getInstance().getClient();
        networkState = new MutableLiveData();
        dataObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<FeedItem> getItems() {
        return dataObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, FeedItem> callback) {
        networkState.postValue(NetworkState.LOADING);
        Call<FeedResponse> callBack = networkClient.getFeed(0);
        callBack.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccessful()) {
                    List<FeedItem> items = response.body().getItems();
                    callback.onResult(items, 1, 2);
                    networkState.postValue(NetworkState.LOADED);
                    for (FeedItem item : items) {

                        Glide.with(ImgurApplication.getInstance())
                                .load(item.getLink())
                                .preload(item.getWidth() / 4, item.getHeight() / 4);

                        dataObservable.onNext(item);
                    }
                } else {
                    Log.e("ERROR", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, getError(t)));
                callback.onResult(new ArrayList<>(), 1, 2);
            }
        });
    }


    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, final @NonNull LoadCallback<Integer, FeedItem> callback) {
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(params.key);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Call<FeedResponse> callBack = networkClient.getFeed(page.get());
        callBack.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccessful()) {
                    List<FeedItem> items = response.body().getItems();
                    callback.onResult(items, page.get() + 1);
                    networkState.postValue(NetworkState.LOADED);
                    for (FeedItem item : items) {

                        //TODO: rewrite with RequestManager->download
                        Glide.with(ImgurApplication.getInstance())
                                .load(item.getLink())
                                .preload(item.getWidth() / 4, item.getHeight() / 4);

                        dataObservable.onNext(item);
                    }
                } else {
                    Log.e("ERROR", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, getError(t)));
                callback.onResult(new ArrayList<>(), page.get());
            }
        });
    }

    private String getError(Throwable t) {
        if (t.getMessage() == null) {
            return t.toString();
        } else {
            return t.getMessage();
        }
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FeedItem> callback) {

    }
}
