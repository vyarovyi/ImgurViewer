package com.imgur.viewer.repositories.network.models;

import com.imgur.viewer.repositories.database.model.FeedItem;

import java.util.List;

public class FeedResponse {
    boolean success;
    int status;
    List<FeedItem> items;

    public FeedResponse(boolean success, int status, List<FeedItem> items) {
        this.success = success;
        this.status = status;
        this.items = items;
    }

    public boolean isSuccessful() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public List<FeedItem> getItems() {
        return items;
    }
}
