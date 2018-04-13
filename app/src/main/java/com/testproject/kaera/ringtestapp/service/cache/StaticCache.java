package com.testproject.kaera.ringtestapp.service.cache;

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class StaticCache {

    private List<APIRedditItem> cachedData;

    public StaticCache() {
        this.cachedData = new ArrayList<>();
    }

    public Observable<List<APIRedditItem>> writeData(List<APIRedditItem> data) {
        return Observable.from(cachedData).mergeWith(Observable.from(data))
                .distinct()
                .toList()
                .map(items -> {
                    cachedData.clear();
                    cachedData.addAll(items);
                    return cachedData;
                });
    }

    public List<APIRedditItem> readData() {
        return cachedData;
    }
}