package com.smartbrightness.eventbus;

/**
 * Created by MMM on 2017/5/14.
 */

public class NetWorkEvent {

    private boolean isConnected;

    public NetWorkEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
