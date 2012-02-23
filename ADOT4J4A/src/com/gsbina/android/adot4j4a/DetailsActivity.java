
package com.gsbina.android.adot4j4a;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity {

    static final String ACTION_PREFIX = "com.gsbina.android.adot4j4a.action.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            String action = getIntent().getAction();
            Fragment details = null;
            if (Login.ACTION_LOGIN.equals(action)) {
                details = new Login.SelectMenu();
            } else if (Timeline.ACTION_TIMELINE.equals(action)) {
                details = new Timeline.SelectMenu();
            } else if (Tweet.ACTION_TWEET.equals(action)) {
                details = new Tweet.SelectMenu();
            } else if (Login.ACTION_LOGIN_FROM_WEBVIEW.equals(action)) {
                details = new Login.FromWebView();
            } else if (Timeline.ACTION_TIMELINE_PUBLIC.equals(action)) {
                details = new Timeline.Public();
            } else if (Timeline.ACTION_TIMELINE_HOME.equals(action)) {
                details = new Timeline.Home();
            } else if (Timeline.ACTION_TIMELINE_USER.equals(action)) {
                details = new Timeline.User();
            } else if (Timeline.ACTION_TIMELINE_MENTIONS.equals(action)) {
                details = new Timeline.Mentions();
            } else if (Timeline.ACTION_TIMELINE_RETWEET_BY_ME.equals(action)) {
                details = new Timeline.RetweetByMe();
            } else if (Timeline.ACTION_TIMELINE_RETWEET_OF_ME.equals(action)) {
                details = new Timeline.RetweetOfMe();
            } else if (Timeline.ACTION_TIMELINE_RETWEET_TO_ME.equals(action)) {
                details = new Timeline.RetweetToMe();
            } else if (Timeline.ACTION_TIMELINE_RETWEET_TO_USER.equals(action)) {
                details = new Timeline.RetweetToUser();
            } else if (Timeline.ACTION_TIMELINE_RETWEET_BY_USER.equals(action)) {
                details = new Timeline.RetweetByUser();
            } else if (Tweet.ACTION_TWEET_UPDATE.equals(action)) {
                details = new Tweet.UpdateStatus();
            } else if (Tweet.ACTION_TWEET_DESTROY.equals(action)) {
                details = new Tweet.DestroyStatus();
            } else if (Tweet.ACTION_TWEET_RETWEET.equals(action)) {
                details = new Tweet.RetweetStatus();
            } else if (Tweet.ACTION_TWEET_GET_RETWEETS.equals(action)) {
                details = new Tweet.GetRetweetStatus();
            } else if (Tweet.ACTION_TWEET_FAVORITES.equals(action)) {
                details = new Tweet.Favorites();
            } else {
                finish();
                return;
            }
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(
                    android.R.id.content, details).commit();
        }
    }
}
