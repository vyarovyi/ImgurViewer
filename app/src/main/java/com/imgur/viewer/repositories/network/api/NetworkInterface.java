package com.imgur.viewer.repositories.network.api;

import com.imgur.viewer.repositories.network.models.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkInterface {

    @GET("gallery/hot/time/{page}.json")
    Call<FeedResponse> getFeed(@Path("page") int page);
}
