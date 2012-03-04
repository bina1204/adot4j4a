
package com.gsbina.android.adot4j4a;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

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
            if (TextUtils.isEmpty(action)) {
                finish();
                return;
            }

            Fragment details = null;
            if (action.startsWith(Login.ACTION_LOGIN)) {
                details = Login.getDetailsFragmentByAction(action);
            } else if (action.startsWith(Timeline.ACTION_TIMELINE)) {
                details = Timeline.getDetailsFragmentByAction(action);
            } else if (action.startsWith(Tweet.ACTION_TWEET)) {
                details = Tweet.getDetailsFragmentByAction(action);
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
