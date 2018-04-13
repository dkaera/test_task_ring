package com.testproject.kaera.ringtestapp.service.api;

import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Query;

@HttpAction(method = HttpAction.Method.GET, value = "r/all/top")
public class GetTopSubredditAction extends BaseAction {

    @Query("count") int count = 0;
    @Query("limit") int limit = 10;


}
