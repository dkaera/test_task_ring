package com.testproject.kaera.ringtestapp.service.api;

import io.techery.janet.http.annotations.RequestHeader;

public class BaseAction {

    @RequestHeader("User-Agent") String userAgent = "Ring Test Android App";
}
