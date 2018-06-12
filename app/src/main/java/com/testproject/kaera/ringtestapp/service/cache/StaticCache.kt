package com.testproject.kaera.ringtestapp.service.cache

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import rx.Observable
import java.util.*

/**
 * Created by Dmitriy Puzak on 6/12/18.
 */
class StaticCache {

    private val cachedData: MutableList<APIRedditItem>

    constructor() {
        this.cachedData = ArrayList()
    }

    fun writeData(data: List<APIRedditItem>?): Observable<List<APIRedditItem>> {
        return Observable.from(cachedData).mergeWith(Observable.from(data))
                .distinct()
                .toList()
                .map { items ->
                    cachedData.clear()
                    cachedData.addAll(items)
                    cachedData
                }
    }

    fun readData(): List<APIRedditItem> {
        return cachedData
    }
}