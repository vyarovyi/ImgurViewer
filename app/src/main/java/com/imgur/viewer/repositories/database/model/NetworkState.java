package com.imgur.viewer.repositories.database.model;


public class NetworkState {
    public enum Status{
        INITIALIZING,
        RUNNING,
        SUCCESS,
        EMPTY,
        FAILED
    }
    private final Status status;
    private final String msg;

    public static final NetworkState LOADED;
    public static final NetworkState LOADING;
    public static final NetworkState INITIALIZING;
    public static final NetworkState EMPTY;

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    static {
        INITIALIZING = new NetworkState(Status.INITIALIZING, "Initializing");
        LOADED = new NetworkState(Status.SUCCESS,"Success");
        LOADING = new NetworkState(Status.RUNNING,"Running");
        EMPTY = new NetworkState(Status.EMPTY, "No data");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }



}
