
package com.gsbina.android.adot4j4a;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class Tweet {

    public static final int SHOW = 0;
    public static final int UPDATE = SHOW + 1;
    public static final int USER_LINE = UPDATE + 1;
    public static final int MENTIONS_LINE = USER_LINE + 1;
    public static final int RETWEET_BY_ME_LINE = MENTIONS_LINE + 1;
    public static final int RETWEET_TO_ME_LINE = RETWEET_BY_ME_LINE + 1;
    public static final int RETWEET_OF_ME_LINE = RETWEET_TO_ME_LINE + 1;
    public static final int RETWEET_TO_USER_LINE = RETWEET_OF_ME_LINE + 1;
    public static final int RETWEET_BY_USER_LINE = RETWEET_TO_USER_LINE + 1;

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
                case USER_LINE:
                case MENTIONS_LINE:
                case RETWEET_BY_ME_LINE:
                case RETWEET_TO_ME_LINE:
                case RETWEET_OF_ME_LINE:
                case RETWEET_TO_USER_LINE:
                case RETWEET_BY_USER_LINE:
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
                    intent.setAction(DetailsActivity.ACTION_TWEET_UPDATE);
                    break;
                case USER_LINE:
                case MENTIONS_LINE:
                case RETWEET_BY_ME_LINE:
                case RETWEET_TO_ME_LINE:
                case RETWEET_OF_ME_LINE:
                case RETWEET_TO_USER_LINE:
                case RETWEET_BY_USER_LINE:
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
                        }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
