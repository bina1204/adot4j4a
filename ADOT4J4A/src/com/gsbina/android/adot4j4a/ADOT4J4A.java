
package com.gsbina.android.adot4j4a;

import twitter4j.auth.AccessToken;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class ADOT4J4A extends Application {

    public static final String CONSUMER_KEY = "eF8u83Xg3EBs1rS9HNjWFQ";
    public static final String CONSUMER_SECRET = "B8BYkgLCb6VBByHpibYHq25dd7WF2kSCZau19eIxA";

    public static final String OAUTH_CALLBACK_SCHEME = "http";
    public static final String OAUTH_CALLBACK_HOST = "gsbina.com/adot4j4a/callback";
    public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://"
            + OAUTH_CALLBACK_HOST;
    public static final String VERIFIER = "oauth_verifier";

    private static final String PREFERENCE = "adot4j4a";
    private static final String TOKEN = "oauth_token";
    private static final String TOKEN_SECRET = "oauth_token_secret";

    public void writeToken(AccessToken accessToken) {
        SharedPreferences preferences = getSharedPreferences(PREFERENCE,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(TOKEN, accessToken.getToken());
        editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
        editor.commit();
    }

    public String getToken() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCE,
                Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }

    public String getTokenSecret() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCE,
                Context.MODE_PRIVATE);
        return preferences.getString(TOKEN_SECRET, "");
    }

    public boolean hasToken() {
        return !TextUtils.isEmpty(getToken()) && !TextUtils.isEmpty(getTokenSecret());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
