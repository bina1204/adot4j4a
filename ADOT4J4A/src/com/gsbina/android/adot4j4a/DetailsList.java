
package com.gsbina.android.adot4j4a;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class DetailsList extends ListFragment {
    boolean mDualPane;

    protected abstract String[] getTitles();

    protected abstract void startDetailsIntent(int index);

    protected abstract Fragment getDetailsFragment(int index);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getTitles()));

        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int index) {

        if (mDualPane) {
            Fragment details = getDetailsFragment(index);

            if (details != null) {
                replaceFragment(details);
            }

        } else {
            startDetailsIntent(index);
        }
    }

    protected void replaceFragment(Fragment details) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.details, details);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
