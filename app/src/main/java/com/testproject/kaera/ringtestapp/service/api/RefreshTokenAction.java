package com.testproject.kaera.ringtestapp.service.api;

import android.util.Base64;

import com.testproject.kaera.ringtestapp.enteties.APIToken;
import com.testproject.kaera.ringtestapp.util.Constants;

import io.techery.janet.http.annotations.Field;
import io.techery.janet.http.annotations.HttpAction;
import io.techery.janet.http.annotations.RequestHeader;
import io.techery.janet.http.annotations.Response;

import static com.testproject.kaera.ringtestapp.util.Constants.CLIENT_ID;

@HttpAction(method = HttpAction.Method.POST, value = "api/v1/access_token", type = HttpAction.Type.FORM_URL_ENCODED)
public class RefreshTokenAction extends BaseAction {

    // TODO: Implement refresh token mechanism

    @Field("redirect_uri") String redirectUri = Constants.REDIRECT_URI;
    @Field("grant_type") String grantType = "refresh_token"; // Use "refresh_token" value for renewing an access token
    @Field("code") String authCode;

    @RequestHeader("Authorization") String authHeader;

    @Response APIToken response;

    public RefreshTokenAction(String refreshToken) {
        String authString = CLIENT_ID + ":";
        this.authCode = refreshToken;
        this.authHeader = "Basic " + Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);
    }

    public APIToken getResponse() {
        return response;
    }
}
