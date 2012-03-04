
package com.gsbina.android.adot4j4a;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.gsbina.android.adot4j4a.loader.AsyncTimelineLoader;

public class Tweet {

    public static final String ACTION_TWEET = DetailsActivity.ACTION_PREFIX + "TWEET";
    public static final String ACTION_TWEET_SHOW = ACTION_TWEET + "_SHOW";
    public static final String ACTION_TWEET_UPDATE = ACTION_TWEET + "_UPDATE";
    public static final String ACTION_TWEET_DESTROY = ACTION_TWEET + "_DESTROY";
    public static final String ACTION_TWEET_RETWEET = ACTION_TWEET + "_RETWEET";
    public static final String ACTION_TWEET_GET_RETWEETS = ACTION_TWEET + "_GET_RETWEETS";
    public static final String ACTION_TWEET_FAVORITES = ACTION_TWEET + "_FAVORITES";

    public static final int SHOW = 0;
    public static final int UPDATE = SHOW + 1;
    public static final int DESTROY = UPDATE + 1;
    public static final int RETWEET = DESTROY + 1;
    public static final int GET_RETWEETS = RETWEET + 1;
    public static final int GET_RETWEETED_BY = GET_RETWEETS + 1;
    public static final int GET_RETWEETED_BY_IDS = GET_RETWEETED_BY + 1;
    public static final int FAVORITES = GET_RETWEETED_BY_IDS + 1;
    public static final int CREATE_FAVORITE = FAVORITES + 1;
    public static final int DESTROY_FAVORITE = CREATE_FAVORITE + 1;

    public static Fragment getDetailsFragmentByAction(String action) {
        if (Tweet.ACTION_TWEET_UPDATE.equals(action)) {
            return new Tweet.UpdateStatus();
        } else if (Tweet.ACTION_TWEET_DESTROY.equals(action)) {
            return new Tweet.DestroyStatus();
        } else if (Tweet.ACTION_TWEET_RETWEET.equals(action)) {
            return new Tweet.RetweetStatus();
        } else if (Tweet.ACTION_TWEET_GET_RETWEETS.equals(action)) {
            return new Tweet.GetRetweetStatus();
        } else if (Tweet.ACTION_TWEET_FAVORITES.equals(action)) {
            return new Tweet.Favorites();
        } else {
            return new Tweet.SelectMenu();
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
            return getResources().getStringArray(R.array.titles_tweet);
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
                case SHOW:
                    return null;
                case UPDATE:
                    return new Tweet.UpdateStatus();
                case DESTROY:
                    return new Tweet.DestroyStatus();
                case RETWEET:
                    return new Tweet.RetweetStatus();
                case GET_RETWEETS:
                    return new Tweet.GetRetweetStatus();
                case GET_RETWEETED_BY:
                    return null;
                case GET_RETWEETED_BY_IDS:
                    return null;
                case FAVORITES:
                    return new Tweet.Favorites();
                case CREATE_FAVORITE:
                case DESTROY_FAVORITE:
            }
            return null;
        }

        @Override
        protected void startDetailsIntent(int index) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            switch (index) {
                case SHOW:
                    return;
                case UPDATE:
                    intent.setAction(Tweet.ACTION_TWEET_UPDATE);
                    break;
                case DESTROY:
                    intent.setAction(Tweet.ACTION_TWEET_DESTROY);
                    break;
                case RETWEET:
                    intent.setAction(Tweet.ACTION_TWEET_RETWEET);
                    break;
                case GET_RETWEETS:
                    intent.setAction(Tweet.ACTION_TWEET_GET_RETWEETS);
                    break;
                case GET_RETWEETED_BY:
                    break;
                case GET_RETWEETED_BY_IDS:
                    break;
                case FAVORITES:
                    intent.setAction(Tweet.ACTION_TWEET_FAVORITES);
                    break;
                case CREATE_FAVORITE:
                    break;
                default:
                    return;
            }
            startActivity(intent);
        }
    }

    public static class UpdateStatus extends Fragment implements OnClickListener {

        private EditText mTweetText;
        private Twitter mTwitter;

        public UpdateStatus() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ADOT4J4A adot4j4a = (ADOT4J4A) getActivity().getApplicationContext();

            ConfigurationBuilder confbuilder = new ConfigurationBuilder();
            confbuilder.setOAuthConsumerKey(ADOT4J4A.CONSUMER_KEY);
            confbuilder.setOAuthConsumerSecret(ADOT4J4A.CONSUMER_SECRET);
            confbuilder.setOAuthAccessToken(adot4j4a.getToken());
            confbuilder.setOAuthAccessTokenSecret(adot4j4a.getTokenSecret());
            mTwitter = new TwitterFactory(confbuilder.build()).getInstance();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.update_status, container, false);
            mTweetText = (EditText) view.findViewById(R.id.tweet_text);
            view.findViewById(R.id.tweet_button).setOnClickListener(this);
            return view;
        }

        Handler mHandler = new Handler();

        @Override
        public void onClick(View v) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        String tweetText = mTweetText.getText().toString();
                        if (!TextUtils.isEmpty(tweetText)) {
                            mTwitter.updateStatus(tweetText);
                            mTweetText.setText("");
                            Toast.makeText(getActivity(), tweetText, Toast.LENGTH_SHORT).show();
                        }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static class DestroyStatus extends Timeline.User {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
            final TwitterStatus twitterStatus = (TwitterStatus) parent.getAdapter().getItem(
                    position);
            mHandler.post(new Runnable() {
                public void run() {
                    try {
                        Status status = twitterStatus.getStatus();
                        mTwitter.destroyStatus(status.getId());
                        Toast.makeText(getActivity(), "destroy : " + status.getText(),
                                Toast.LENGTH_SHORT).show();
                        @SuppressWarnings("unchecked")
                        ArrayAdapter<TwitterStatus> adapter = (ArrayAdapter<TwitterStatus>) parent
                                .getAdapter();
                        adapter.remove(twitterStatus);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static class RetweetStatus extends Timeline.Home {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
            final TwitterStatus twitterStatus = (TwitterStatus) parent.getAdapter().getItem(
                    position);
            mHandler.post(new Runnable() {
                public void run() {
                    try {
                        Status status = twitterStatus.getStatus();
                        mTwitter.retweetStatus(status.getId());
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static class GetRetweetStatus extends Timeline.RetweetOfMe {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final TwitterStatus twitterStatus = (TwitterStatus) parent.getAdapter().getItem(
                    position);
            long statusId = twitterStatus.getStatus().getId();
            replaceFragment(statusId);
        }

        private void replaceFragment(long statusId) {
            Retweets details = new Retweets(statusId);

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, details);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    public static class Retweets extends Timeline.Base {

        public Retweets(long statusId) {
            super(Tweet.GET_RETWEETS + Twitter4JApis.TWEET_MODE, statusId);
        }

        @Override
        public Loader<List<TwitterStatus>> onCreateLoader(int id, Bundle data) {
            return new AsyncTimelineLoader(getActivity().getApplicationContext(), mMode, mStatusId);
        }
    }

    public static class Favorites extends Timeline.Base {

        public Favorites() {
            super(Tweet.FAVORITES + Twitter4JApis.TWEET_MODE);
        }

    }
}
