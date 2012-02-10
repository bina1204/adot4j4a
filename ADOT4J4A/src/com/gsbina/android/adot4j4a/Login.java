
package com.gsbina.android.adot4j4a;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Login {

    // Activity開始時のインテントパラメータ
    public static final String CALLBACK = "callback";
    public static final String CONSUMER_KEY = "consumer_key";
    public static final String CONSUMER_SECRET = "consumer_secret";

    // Activity終了時のインテントパラメータ
    public static final String USER_ID = "user_id";
    public static final String SCREEN_NAME = "screen_name";
    public static final String TOKEN = "token";
    public static final String TOKEN_SECRET = "token_secret";

    private static final String OAUTH_VERIFIER = "oauth_verifier";

    /**
     * WebView でログイン
     */
    public static class FromWebView extends Fragment {

        private WebView mWebView;
        private String mCallback;
        private Twitter mTwitter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            FragmentActivity activity = getActivity();
            activity.setResult(Activity.RESULT_CANCELED);

            mWebView = new WebView(activity);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setSavePassword(false);
            mWebView.setWebChromeClient(mWebChromeClient);
            mWebView.setWebViewClient(mWebViewClient);
            return mWebView;
        }

        @Override
        public void onStart() {

            // TwitterのOAuth認証画面で毎回ユーザ名、アカウントを入力させるために必要
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(false);

            FragmentActivity activity = getActivity();

            Bundle extras = getArguments();
            mCallback = extras.getString(CALLBACK);
            String consumerKey = extras.getString(CONSUMER_KEY);
            String consumerSecret = extras.getString(CONSUMER_SECRET);
            if ((mCallback == null) || (consumerKey == null)
                    || (consumerSecret == null)) {
                activity.finish();
            }

            mTwitter = new TwitterFactory().getInstance();
            mTwitter.setOAuthConsumer(consumerKey, consumerSecret);

            PreTask preTask = new PreTask();
            preTask.execute();

            super.onStart();
        }

        // 前処理
        public class PreTask extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            protected String doInBackground(Void... params) {
                String authorizationUrl = null;
                try {
                    // 非同期処理が必要なメソッドの呼び出し
                    RequestToken requestToken = mTwitter.getOAuthRequestToken();
                    if (requestToken != null) {
                        authorizationUrl = requestToken.getAuthorizationURL();
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return authorizationUrl;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                FragmentActivity activity = getActivity();
                activity.setProgressBarIndeterminateVisibility(false);
                if (result != null) {
                    mWebView.loadUrl(result);
                } else {
                    activity.finish();
                }
            }

        }

        private WebChromeClient mWebChromeClient = new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getActivity().setProgress(newProgress * 100);
            }

        };

        private WebViewClient mWebViewClient = new WebViewClient() {

            // 特定のページをフック
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean result = true;
                if ((url != null) && (url.startsWith(mCallback))) {
                    Uri uri = Uri.parse(url);
                    String oAuthVerifier = uri.getQueryParameter(OAUTH_VERIFIER);
                    PostTask postTask = new PostTask();
                    postTask.execute(oAuthVerifier);
                } else {
                    result = super.shouldOverrideUrlLoading(view, url);
                }
                return result;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                shouldOverrideUrlLoading(view, url);
            }

        };

        // 後処理
        public class PostTask extends AsyncTask<String, Void, AccessToken> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            protected AccessToken doInBackground(String... params) {
                AccessToken accessToken = null;
                if (params != null) {
                    try {
                        // 非同期処理が必要なメソッドの呼び出し
                        accessToken = mTwitter.getOAuthAccessToken(params[0]);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
                return accessToken;
            }

            @Override
            protected void onPostExecute(AccessToken result) {
                super.onPostExecute(result);
                getActivity().setProgressBarIndeterminateVisibility(false);
                if (result != null) {

                    ADOT4J4A adot4j4a = (ADOT4J4A) getActivity().getApplication();
                    adot4j4a.writeToken(result);

                    Log.d("LoginFromWebView", result.getScreenName());

                    getActivity().setResult(Activity.RESULT_OK);
                }
                getActivity().finish();
            }
        }
    }

    /**
     * ブラウザの callback でログイン
     */
    public static class FromBrowser extends Fragment {

    }

    /**
     * キーをコピペしてログイン
     */
    public static class FromKeyCode extends Fragment {

    }
}
