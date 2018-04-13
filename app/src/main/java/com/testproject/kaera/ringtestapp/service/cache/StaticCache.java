package com.testproject.kaera.ringtestapp.service.cache;

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;

import java.util.ArrayList;
import java.util.List;

public class StaticCache {

    private List<APIRedditItem> cachedData;

    public StaticCache() {
        this.cachedData = new ArrayList<>();
    }

    public void writeData(List<APIRedditItem> data) {
        this.cachedData.addAll(data);
    }

    public List<APIRedditItem> readData() {
        return cachedData;
    }
}