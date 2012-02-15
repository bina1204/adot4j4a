
package com.gsbina.android.adot4j4a;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.gsbina.android.adot4j4a.adapter.TimelineAdapter;
import com.gsbina.android.adot4j4a.loader.AsyncTimelineLoader;

public class Timeline {

    public static final int PUBLIC_MODE = 0;
    public static final int HOME_MODE = 1;

    public static class Public extends ListFragment implements LoaderCallbacks<List<TwitterStatus>> {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FragmentActivity activity = getActivity();
            activity.getSupportLoaderManager().initLoader(0, null,
                    this);
        }

        @Override
        public Loader<List<TwitterStatus>> onCreateLoader(int id, Bundle data) {
            return new AsyncTimelineLoader(getActivity().getApplicationContext(),
                    PUBLIC_MODE);
        }

        @Override
        public void onLoadFinished(Loader<List<TwitterStatus>> loader, List<TwitterStatus> data) {
            setListAdapter(new TimelineAdapter(getActivity(), R.layout.tweet_row, data));
        }

        @Override
        public void onLoaderReset(Loader<List<TwitterStatus>> data) {
        }
    }

    public static class Home extends ListFragment implements LoaderCallbacks<List<TwitterStatus>> {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FragmentActivity activity = getActivity();
            activity.getSupportLoaderManager().initLoader(0, null,
                    this);
        }

        @Override
        public Loader<List<TwitterStatus>> onCreateLoader(int id, Bundle data) {
            return new AsyncTimelineLoader(getActivity().getApplicationContext(),
                    HOME_MODE);
        }

        @Override
        public void onLoadFinished(Loader<List<TwitterStatus>> loader, List<TwitterStatus> data) {
            setListAdapter(new TimelineAdapter(getActivity(), R.layout.tweet_row, data));
        }

        @Override
        public void onLoaderReset(Loader<List<TwitterStatus>> data) {
        }
    }
}
