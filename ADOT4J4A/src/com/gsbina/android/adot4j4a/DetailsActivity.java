
package com.gsbina.android.adot4j4a;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity {

    private static final String ACTION_PREFIX = "com.gsbina.android.adot4j4a.action.";
    public static final String ACTION_LOGIN = ACTION_PREFIX + "LOGIN";
    public static final String ACTION_LOGIN_FROM_WEBVIEW = ACTION_LOGIN + "_FROM_WEBVIEW";
    public static final String ACTION_TIMELINE = ACTION_PREFIX + "TIMELINE";
    public static final String ACTION_TIMELINE_PUBLIC = ACTION_TIMELINE + "_PUBLIC";
    public static final String ACTION_TIMELINE_HOME = ACTION_TIMELINE + "_HOME";
    public static final String ACTION_TIMELINE_USER = ACTION_TIMELINE + "_USER";
    public static final String ACTION_TIMELINE_MENTIONS = ACTION_TIMELINE + "_MENTIONS";
    public static final String ACTION_TIMELINE_RETWEET_BY_ME = ACTION_TIMELINE + "_RETWEET_BY_ME";
    public static final String ACTION_TIMELINE_RETWEET_OF_ME = ACTION_TIMELINE + "_RETWEET_OF_ME";
    public static final String ACTION_TIMELINE_RETWEET_TO_ME = ACTION_TIMELINE + "_RETWEET_TO_ME";
    public static final String ACTION_TIMELINE_RETWEET_TO_USER = ACTION_TIMELINE
            + "_RETWEET_TO_USER";
    public static final String ACTION_TIMELINE_RETWEET_BY_USER = ACTION_TIMELINE
            + "_RETWEET_BY_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            String action = getIntent().getAction();
            Fragment details = null;
            if (ACTION_LOGIN.equals(action)) {
                details = new Login.SelectMenu();
            } else if (ACTION_LOGIN_FROM_WEBVIEW.equals(action)) {
                details = new Login.FromWebView();
            } else if (ACTION_TIMELINE.equals(action)) {
                details = new Timeline.SelectMenu();
            } else if (ACTION_TIMELINE_PUBLIC.equals(action)) {
                details = new Timeline.Public();
            } else if (ACTION_TIMELINE_HOME.equals(action)) {
                details = new Timeline.Home();
            } else if (ACTION_TIMELINE_USER.equals(action)) {
                details = new Timeline.User();
            } else if (ACTION_TIMELINE_MENTIONS.equals(action)) {
                details = new Timeline.Mentions();
            } else if (ACTION_TIMELINE_RETWEET_BY_ME.equals(action)) {
                details = new Timeline.RetweetByMe();
            } else if (ACTION_TIMELINE_RETWEET_OF_ME.equals(action)) {
                details = new Timeline.RetweetOfMe();
            } else if (ACTION_TIMELINE_RETWEET_TO_ME.equals(action)) {
                details = new Timeline.RetweetToMe();
            } else if (ACTION_TIMELINE_RETWEET_TO_USER.equals(action)) {
                details = new Timeline.RetweetToUser();
            } else if (ACTION_TIMELINE_RETWEET_BY_USER.equals(action)) {
                details = new Timeline.RetweetByUser();
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
