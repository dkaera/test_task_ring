package com.testproject.kaera.ringtestapp.service.api;

import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.Response;

@HttpAction(method = HttpAction.Method.GET, value = "top.json")
public class AuthenticateAction {

    @Response
    String response;

    public String getResponse() {
        return response;
    }
}
