package com.cab404.libph.data;

/**
 * @author cab404
 */
public class LivestreetKey {


    public String key;

    @Deprecated
    public LivestreetKey(String address, String key) {
        this.key = key;
    }

    public LivestreetKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

}
