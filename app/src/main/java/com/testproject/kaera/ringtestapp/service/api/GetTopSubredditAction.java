package com.testproject.kaera.ringtestapp.service.api;

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;

import java.util.List;

import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Query;
import io.techery.janet.http.annotations.Response;

@HttpAction(method = HttpAction.Method.GET, value = "r/all/top.json")
public class GetTopSubredditAction extends BaseAction {

    @Query("count") int count;
    @Query("after") String after;
//    @Query("limit") String limit; // TODO: This value is declared in documentation but has no effect. Future improvement

    @Response List<APIRedditItem> result;

    public GetTopSubredditAction(int count, String after) {
        this.count = count;
        this.after = after;
    }

    public List<APIRedditItem> getResult() {
        return result;
    }
}
