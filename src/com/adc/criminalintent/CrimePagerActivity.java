package com.adc.criminalintent;
import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.adc.criminalintent.app.CriminalIntentApp;

import javax.inject.Inject;


public class CrimePagerActivity extends FragmentActivity {

    @Inject CrimeLab crimeLab;

	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	
	@Override
	protected void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);

        ((CriminalIntentApp) getApplication()).inject(this);

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = crimeLab.getCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mCrimes.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getId());
			}
		});
		
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		for(int i = 0; i < mCrimes.size(); i++){
			if(mCrimes.get(i).getId().equals(crimeId)){
				mViewPager.setCurrentItem(i);
				setTitle(mCrimes.get(i).getTitle());
				break;
			}
		}
		
		
	}
}
