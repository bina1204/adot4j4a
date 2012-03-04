
package com.gsbina.android.adot4j4a;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gsbina.android.adot4j4a.adapter.TimelineAdapter;
import com.gsbina.android.adot4j4a.loader.AsyncTimelineLoader;

public class Timeline {

    public static final String ACTION_TIMELINE = DetailsActivity.ACTION_PREFIX + "TIMELINE";
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

    public static final int PUBLIC_LINE = 0;
    public static final int HOME_LINE = PUBLIC_LINE + 1;
    public static final int USER_LINE = HOME_LINE + 1;
    public static final int MENTIONS_LINE = USER_LINE + 1;
    public static final int RETWEET_BY_ME_LINE = MENTIONS_LINE + 1;
    public static final int RETWEET_TO_ME_LINE = RETWEET_BY_ME_LINE + 1;
    public static final int RETWEET_OF_ME_LINE = RETWEET_TO_ME_LINE + 1;
    public static final int RETWEET_TO_USER_LINE = RETWEET_OF_ME_LINE + 1;
    public static final int RETWEET_BY_USER_LINE = RETWEET_TO_USER_LINE + 1;

    public static Fragment getDetailsFragmentByAction(String action) {
        if (Timeline.ACTION_TIMELINE_PUBLIC.equals(action)) {
            return new Timeline.Public();
        } else if (Timeline.ACTION_TIMELINE_HOME.equals(action)) {
            return new Timeline.Home();
        } else if (Timeline.ACTION_TIMELINE_USER.equals(action)) {
            return new Timeline.User();
        } else if (Timeline.ACTION_TIMELINE_MENTIONS.equals(action)) {
            return new Timeline.Mentions();
        } else if (Timeline.ACTION_TIMELINE_RETWEET_BY_ME.equals(action)) {
            return new Timeline.RetweetByMe();
        } else if (Timeline.ACTION_TIMELINE_RETWEET_OF_ME.equals(action)) {
            return new Timeline.RetweetOfMe();
        } else if (Timeline.ACTION_TIMELINE_RETWEET_TO_ME.equals(action)) {
            return new Timeline.RetweetToMe();
        } else if (Timeline.ACTION_TIMELINE_RETWEET_TO_USER.equals(action)) {
            return new Timeline.RetweetToUser();
        } else if (Timeline.ACTION_TIMELINE_RETWEET_BY_USER.equals(action)) {
            return new Timeline.RetweetByUser();
        } else {
            return new Timeline.SelectMenu();
        }

    }

    public static class SelectMenu extends DetailsList {

        private boolean mHasAccount = false;

        @Override
        public void onResume() {
            ADOT4J4A adot4j4a = (ADOT4J4A) getActivity().getApplicationContext();
            mHasAccount = adot4j4a.hasToken();
            super.onResume();
        }

        @Override
        protected String[] getTitles() {
            return getResources().getStringArray(R.array.titles_timeline);
        }

        @Override
        protected void replaceFragment(Fragment details) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.details, details);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }

        @Override
        protected Fragment getDetailsFragment(int index) {
            switch (index) {
                case PUBLIC_LINE:
                    return new Timeline.Public();
                case HOME_LINE:
                    return new Timeline.Home();
                case USER_LINE:
                    return new Timeline.User();
                case MENTIONS_LINE:
                    return new Timeline.Mentions();
                case RETWEET_BY_ME_LINE:
                    return new Timeline.RetweetByMe();
                case RETWEET_TO_ME_LINE:
                    return new Timeline.RetweetToMe();
                case RETWEET_OF_ME_LINE:
                    return new Timeline.RetweetOfMe();
                case RETWEET_TO_USER_LINE:
                    return new Timeline.RetweetToUser();
                case RETWEET_BY_USER_LINE:
                    return new Timeline.RetweetByUser();
            }
            return null;
        }

        @Override
        protected void startDetailsIntent(int index) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            switch (index) {
                case PUBLIC_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_PUBLIC);
                    break;
                case HOME_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_HOME);
                    break;
                case USER_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_USER);
                    break;
                case MENTIONS_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_MENTIONS);
                    break;
                case RETWEET_BY_ME_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_RETWEET_BY_ME);
                    break;
                case RETWEET_TO_ME_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_RETWEET_TO_ME);
                    break;
                case RETWEET_OF_ME_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_RETWEET_OF_ME);
                    break;
                case RETWEET_TO_USER_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_RETWEET_TO_USER);
                    break;
                case RETWEET_BY_USER_LINE:
                    intent.setAction(Timeline.ACTION_TIMELINE_RETWEET_BY_USER);
                    break;
                default:
                    return;
            }
            startActivity(intent);
        }
    }

    public static class Public extends Timeline.Base {
        public Public() {
            super(PUBLIC_LINE);
        }
    }

    public static class Home extends Timeline.Base {
        public Home() {
            super(HOME_LINE);
        }
    }

    public static class User extends Timeline.Base {
        public User() {
            super(USER_LINE);
        }
    }

    public static class Mentions extends Timeline.Base {
        public Mentions() {
            super(MENTIONS_LINE);
        }
    }

    public static class RetweetByMe extends Timeline.Base {
        public RetweetByMe() {
            super(RETWEET_BY_ME_LINE);
        }
    }

    public static class RetweetToMe extends Timeline.Base {
        public RetweetToMe() {
            super(RETWEET_TO_ME_LINE);
        }
    }

    public static class RetweetOfMe extends Timeline.Base {
        public RetweetOfMe() {
            super(RETWEET_OF_ME_LINE);
        }
    }

    public static class RetweetToUser extends Timeline.Base {
        public RetweetToUser() {
            super(RETWEET_TO_USER_LINE);
        }
    }

    public static class RetweetByUser extends Timeline.Base {
        public RetweetByUser() {
            super(RETWEET_BY_USER_LINE);
        }
    }

    public static class Base extends ListFragment implements LoaderCallbacks<List<TwitterStatus>>,
            OnItemClickListener {

        protected final int mMode;
        protected final long mStatusId;

        Twitter mTwitter;
        Handler mHandler = new Handler();

        public Base(int mode) {
            this(mode, -1);
        }

        public Base(int mode, long statusId) {
            mMode = mode;
            mStatusId = statusId;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FragmentActivity activity = getActivity();
            activity.getSupportLoaderManager().initLoader(0, null,
                    this);

            ADOT4J4A adot4j4a = (ADOT4J4A) getActivity().getApplicationContext();

            ConfigurationBuilder confbuilder = new ConfigurationBuilder();
            confbuilder.setOAuthConsumerKey(ADOT4J4A.CONSUMER_KEY);
            confbuilder.setOAuthConsumerSecret(ADOT4J4A.CONSUMER_SECRET);
            confbuilder.setOAuthAccessToken(adot4j4a.getToken());
            confbuilder.setOAuthAccessTokenSecret(adot4j4a.getTokenSecret());
            mTwitter = new TwitterFactory(confbuilder.build()).getInstance();
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getListView().setOnItemClickListener(this);
        }

        @Override
        public Loader<List<TwitterStatus>> onCreateLoader(int id, Bundle data) {
            return new AsyncTimelineLoader(getActivity().getApplicationContext(), mMode);
        }

        @Override
        public void onLoadFinished(Loader<List<TwitterStatus>> loader, List<TwitterStatus> data) {
            setListAdapter(new TimelineAdapter(getActivity(), R.layout.tweet_row, data));
        }

        @Override
        public void onLoaderReset(Loader<List<TwitterStatus>> data) {
        }

        @Override
        public void onPause() {
            FragmentActivity activity = getActivity();
            activity.getSupportLoaderManager().destroyLoader(0);
            super.onPause();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    }
}
