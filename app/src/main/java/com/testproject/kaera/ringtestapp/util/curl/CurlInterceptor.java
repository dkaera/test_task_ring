package com.testproject.kaera.ringtestapp.util.curl;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CurlInterceptor implements Interceptor {

    private static final String TAG = "Curl command :: ";

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();

        final Request copy = request.newBuilder().build();
        final String curl = new CurlBuilder(copy).build();

        Log.d(TAG, curl);

        return chain.proceed(request);
    }
}
