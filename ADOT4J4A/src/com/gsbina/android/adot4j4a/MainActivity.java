
package com.gsbina.android.adot4j4a;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gsbina.android.adot4j4a.Login.FromWebView;

public class MainActivity extends FragmentActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.main);
    }

    public static class DetailsActivity extends FragmentActivity {

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
                FromWebView details = new FromWebView();
                details.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(
                        android.R.id.content, details).commit();
            }
        }
    }

    private static final int REQUEST_OAUTH = 0;

    public static class TitlesFragment extends ListFragment {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Populate list with our static array of titles.
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_list_item_checkable_1,
                    android.R.id.text1, Twitter4JApis.TITELS));

            // Check to see if we have a frame in which to embed the details
            // fragment directly in the containing UI.
            View detailsFrame = getActivity().findViewById(R.id.details);
            mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }

            if (mDualPane) {
                // In dual-pane mode, the list view highlights the selected
                // item.
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showDetails(mCurCheckPosition);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showDetails(position);
        }

        /**
         * Helper function to show the details of a selected item, either by
         * displaying a fragment in-place in the current UI, or starting a whole
         * new activity in which it is displayed.
         */
        void showDetails(int index) {
            mCurCheckPosition = index;

            if (mDualPane) {
                // We can display everything in-place with fragments, so update
                // the list to highlight the selected item and show the data.
                getListView().setItemChecked(index, true);

                switch (index) {
                    case 0:

                        // Check what fragment is currently shown, replace if
                        // needed.
                        FromWebView details = (FromWebView) getFragmentManager().findFragmentById(
                                R.id.details);
                        if (details == null) {
                            // Make new fragment to show this selection.
                            details = new FromWebView();

                            Bundle extras = new Bundle();
                            extras.putString(Login.CALLBACK, ADOT4J4A.OAUTH_CALLBACK_URL);
                            extras.putString(Login.CONSUMER_KEY, ADOT4J4A.CONSUMER_KEY);
                            extras.putString(Login.CONSUMER_SECRET, ADOT4J4A.CONSUMER_SECRET);

                            details.setArguments(extras);

                            // Execute a transaction, replacing any existing
                            // fragment
                            // with this one inside the frame.
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.details, details);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.commit();
                        }

                        break;

                    default:
                        break;
                }

            } else {
                Intent intent = new Intent()
                        .setClass(getActivity(), DetailsActivity.class)
                        .putExtra(Login.CALLBACK, ADOT4J4A.OAUTH_CALLBACK_URL)
                        .putExtra(Login.CONSUMER_KEY, ADOT4J4A.CONSUMER_KEY)
                        .putExtra(Login.CONSUMER_SECRET, ADOT4J4A.CONSUMER_SECRET);
                startActivityForResult(intent, REQUEST_OAUTH);
                startActivity(intent);
            }
        }
    }
}
