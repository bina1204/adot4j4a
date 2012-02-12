
package com.gsbina.android.adot4j4a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gsbina.android.adot4j4a.Login.LoginList;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    public static class TitlesFragment extends Fragment implements OnItemClickListener {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        private ListView mTitleList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View layout = inflater.inflate(R.layout.title_list, container, false);
            mTitleList = (ListView) layout.findViewById(R.id.title_list);
            mTitleList.setOnItemClickListener(this);

            return layout;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Populate list with our static array of titles.
            mTitleList.setAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_list_item_checkable_1,
                    android.R.id.text1, getResources().getStringArray(R.array.titles)));

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
                mTitleList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showDetails(mCurCheckPosition);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
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
                mTitleList.setItemChecked(index, true);

                Fragment details = getFragmentManager().findFragmentById(
                        R.id.details);
                if (details == null) {

                    switch (index) {
                        case Twitter4JApis.LOGIN:
                            details = new LoginList();
                            break;
                        case Twitter4JApis.TIMELINE:
                        default:
                            return;
                    }

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.add(R.id.details, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }

            } else {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                switch (index) {
                    case Twitter4JApis.LOGIN:
                        intent.setAction(DetailsActivity.ACTION_LOGIN);
                        break;
                    case Twitter4JApis.TIMELINE:
                    default:
                        return;
                }

                startActivity(intent);
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showDetails(position);
        }
    }
}
