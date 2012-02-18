
package com.gsbina.android.adot4j4a;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.gsbina.android.adot4j4a.adapter.TimelineAdapter;
import com.gsbina.android.adot4j4a.loader.AsyncTimelineLoader;

public class Timeline {

    public static final int PUBLIC_LINE = 0;
    public static final int HOME_LINE = PUBLIC_LINE + 1;
    public static final int USER_LINE = HOME_LINE + 1;
    public static final int MENTIONS_LINE = USER_LINE + 1;
    public static final int RETWEET_BY_ME_LINE = MENTIONS_LINE + 1;
    public static final int RETWEET_TO_ME_LINE = RETWEET_BY_ME_LINE + 1;
    public static final int RETWEET_OF_ME_LINE = RETWEET_TO_ME_LINE + 1;
    public static final int RETWEET_TO_USER_LINE = RETWEET_OF_ME_LINE + 1;
    public static final int RETWEET_BY_USER_LINE = RETWEET_TO_USER_LINE + 1;

    public static class SelectMenu extends DetailsList {
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
            // TODO デュアルペインのタイムライン
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
            // TODO シングルペインのタイムライン
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            switch (index) {
                case PUBLIC_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_PUBLIC);
                    break;
                case HOME_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_HOME);
                    break;
                case USER_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_USER);
                    break;
                case MENTIONS_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_MENTIONS);
                    break;
                case RETWEET_BY_ME_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_RETWEET_BY_ME);
                    break;
                case RETWEET_TO_ME_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_RETWEET_TO_ME);
                    break;
                case RETWEET_OF_ME_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_RETWEET_OF_ME);
                    break;
                case RETWEET_TO_USER_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_RETWEET_TO_USER);
                    break;
                case RETWEET_BY_USER_LINE:
                    intent.setAction(DetailsActivity.ACTION_TIMELINE_RETWEET_BY_USER);
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

    public static class Base extends ListFragment implements LoaderCallbacks<List<TwitterStatus>> {

        private final int mMode;

        public Base(int mode) {
            mMode = mode;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FragmentActivity activity = getActivity();
            activity.getSupportLoaderManager().initLoader(0, null,
                    this);
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
    }
}
