package com.adc.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.adc.criminalintent.adapter.CrimeListAdapter;
import com.adc.criminalintent.app.CriminalIntentApp;
import com.adc.criminalintent.listener.CrimeMultiChoiceModeListener;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

public class CrimeListFragment extends ListFragment {

    @Inject CrimeLab crimeLab;
    @Inject ListAdapter mAdapter;

	private ArrayList<Crime> mCrimes;
	private boolean mSubtitleVisible;
	private static final String TAG = "CrimeListFragment";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((CriminalIntentApp) getActivity().getApplication()).inject(this);
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.crime_title);

		mCrimes = crimeLab.getCrimes();
		
		setRetainInstance(true);
		mSubtitleVisible = false;

	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if(mSubtitleVisible){
				getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
			}
		}

        ListView listView = (ListView) view.findViewById(android.R.id.list);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new CrimeMultiChoiceModeListener(this));
        }


		return view;
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if(mSubtitleVisible && showSubtitle != null){
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeListAdapter adapter = (CrimeListAdapter) getListAdapter();
        Crime crime = adapter.getItem(position);

        switch (item.getItemId()){
            case R.id.menu_item_delete_crime:
                crimeLab.deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.menu_item_new_crime :
				Crime crime = new Crime();
				crimeLab.addCrime(crime);
				
				Intent i = new Intent(getActivity(), CrimePagerActivity.class);
				i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
				startActivityForResult(i, 0);
				return true;
			case R.id.menu_item_show_subtitle:
				if(getActivity().getActionBar().getSubtitle() == null){
					getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
					mSubtitleVisible = true;
					item.setTitle(R.string.hide_subtitle);
				}else{
					getActivity().getActionBar().setSubtitle(null);
					mSubtitleVisible = false;
					item.setTitle(R.string.show_subtitle);
				}
				
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((CrimeListAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeListAdapter) getListAdapter()).getItem(position);
		
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
		
	}
	
}
