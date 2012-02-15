
package com.gsbina.android.adot4j4a.loader;

import java.util.ArrayList;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.gsbina.android.adot4j4a.ADOT4J4A;
import com.gsbina.android.adot4j4a.Timeline;
import com.gsbina.android.adot4j4a.TwitterStatus;
import com.gsbina.android.utils.ImageUtil;

public class AsyncTimelineLoader extends AsyncTaskLoader<List<TwitterStatus>> {

    Twitter mTwitter;
    List<TwitterStatus> mResult;

    int mMode = -1;

    private AsyncTimelineLoader(Context context) {
        super(context);

        ADOT4J4A adot4j4a = (ADOT4J4A) context.getApplicationContext();

        ConfigurationBuilder confbuilder = new ConfigurationBuilder();
        confbuilder.setOAuthConsumerKey(ADOT4J4A.CONSUMER_KEY);
        confbuilder.setOAuthConsumerSecret(ADOT4J4A.CONSUMER_SECRET);
        confbuilder.setOAuthAccessToken(adot4j4a.getToken());
        confbuilder.setOAuthAccessTokenSecret(adot4j4a.getTokenSecret());
        mTwitter = new TwitterFactory(confbuilder.build()).getInstance();
    }

    public AsyncTimelineLoader(Context context, int mode) {
        this(context);
        mMode = mode;
    }

    @Override
    public List<TwitterStatus> loadInBackground() {
        List<TwitterStatus> newStatus = new ArrayList<TwitterStatus>();
        try {
            ResponseList<Status> timeline = null;
            switch (mMode) {
                case Timeline.PUBLIC_MODE:
                    timeline = mTwitter.getPublicTimeline();
                    break;
                case Timeline.HOME_MODE:
                    timeline = mTwitter.getHomeTimeline();
                    break;
                default:
                    return null;
            }
            for (Status status : timeline) {
                Log.d("TIMELINE", status.getText());
                byte[] image = ImageUtil.download(status.getUser().getProfileImageURL());
                TwitterStatus twitterStatus = new TwitterStatus(status, image);
                newStatus.add(twitterStatus);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return newStatus;
    }

    @Override
    public void deliverResult(List<TwitterStatus> data) {
        if (isReset()) {
            if (this.mResult != null) {
                this.mResult = null;
            }
            return;
        }

        this.mResult = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (this.mResult != null) {
            deliverResult(this.mResult);
        }
        if (takeContentChanged() || this.mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }

}
