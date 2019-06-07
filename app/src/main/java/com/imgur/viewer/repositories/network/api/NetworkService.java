package com.imgur.viewer.repositories.network.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imgur.viewer.BuildConfig;
import com.imgur.viewer.repositories.network.models.FeedResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService sInstance;

    public static NetworkService getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkService();
        }
        return sInstance;
    }

    private NetworkInterface networkInterface;

    private NetworkService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FeedResponse.class, new CustomJsonDeserializer())
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/") //TODO: move to const
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build());

        networkInterface = builder.build().create(NetworkInterface.class);
    }

    public NetworkInterface getClient() {
        return networkInterface;
    }
}
