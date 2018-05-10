package com.testproject.kaera.ringtestapp.service.api

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import io.techery.janet.http.annotations.HttpAction
import io.techery.janet.http.annotations.Query
import io.techery.janet.http.annotations.Response

/**
 * Created by Dmitriy Puzak on 5/10/18.
 */
@HttpAction(method = HttpAction.Method.GET, value = "r/all/top.json")
open class GetTopSubredditAction : BaseAction {

    @Query("count")
    var count: Int
    @Query("after")
    var after: String?
    //    @Query("limit") String limit; // TODO: This value is declared in documentation but has no effect. Future improvement

    constructor (count: Int, after: String? = null) {
        this.count = count
        this.after = after
    }

    @Response
    var result: List<APIRedditItem>? = null
}