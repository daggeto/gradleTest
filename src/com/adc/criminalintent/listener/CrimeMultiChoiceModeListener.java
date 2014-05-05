package com.adc.criminalintent.listener;

import android.annotation.TargetApi;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.adc.criminalintent.CrimeLab;
import com.adc.criminalintent.CrimeListFragment;
import com.adc.criminalintent.R;
import com.adc.criminalintent.app.CriminalIntentApp;

import javax.inject.Inject;

/**
 * Created by daggreto on 2014.05.01.
 */
@TargetApi(19)
public class CrimeMultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {
    ListFragment fragment;

    @Inject CrimeLab crimeLab;

    public CrimeMultiChoiceModeListener(ListFragment fragment){
        this.fragment = fragment;
        ((CriminalIntentApp) fragment.getActivity().getApplication()).inject(this);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.crime_list_item_context, menu);

        actionMode.setTitle(R.string.context_name);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_item_delete_crime:
                CrimeListFragment.CrimeAdapter adapter = (CrimeListFragment.CrimeAdapter) fragment.getListAdapter();
                for (int i = adapter.getCount() - 1; i >= 0; i--) {
                    if(fragment.getListView().isItemChecked(i)){
                        crimeLab.deleteCrime(adapter.getItem(i));
                    }
                }
                actionMode.finish();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
