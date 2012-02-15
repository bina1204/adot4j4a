
package com.gsbina.android.adot4j4a;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.gsbina.android.adot4j4a.Login.FromWebView;
import com.gsbina.android.adot4j4a.Login.LoginList;
import com.gsbina.android.adot4j4a.Timeline.Home;
import com.gsbina.android.adot4j4a.Timeline.Public;

public class DetailsActivity extends FragmentActivity {

    private static final String ACTION_PREFIX = "com.gsbina.android.adot4j4a.action.";
    public static final String ACTION_LOGIN = ACTION_PREFIX + "LOGIN";
    public static final String ACTION_LOGIN_FROM_WEBVIEW = ACTION_LOGIN + "_FROM_WEBVIEW";
    public static final String ACTION_TIMELINE = ACTION_PREFIX + "TIMELINE";
    public static final String ACTION_TIMELINE_PUBLIC = ACTION_TIMELINE + "_PUBLIC";
    public static final String ACTION_TIMELINE_HOME = ACTION_TIMELINE + "_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            String action = getIntent().getAction();
            Fragment details = null;
            if (ACTION_LOGIN.equals(action)) {
                details = new LoginList();
            } else if (ACTION_LOGIN_FROM_WEBVIEW.equals(action)) {
                details = new FromWebView();
            } else if (ACTION_TIMELINE.equals(action)) {
                // TODO タイムライン選択
                return;
            } else if (ACTION_TIMELINE_PUBLIC.equals(action)) {
                details = new Public();
            } else if (ACTION_TIMELINE_HOME.equals(action)) {
                details = new Home();
            }
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(
                    android.R.id.content, details).commit();
        }
    }
}
