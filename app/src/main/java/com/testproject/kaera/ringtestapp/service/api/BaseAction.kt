package com.testproject.kaera.ringtestapp.service.api

import io.techery.janet.http.annotations.RequestHeader

/**
 * Created by Dmitriy Puzak on 5/10/18.
 */
open class BaseAction {

    @RequestHeader("User-Agent")
    internal var userAgent = "Ring Test Android App"
}