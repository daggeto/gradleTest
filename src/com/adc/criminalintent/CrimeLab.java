package com.adc.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";

	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;
	
	private CrimeLab sCrimeLab;
	private Context mAppContext;
	
	public CrimeLab(Context appContext){
		mAppContext	= appContext;
		mSerializer = new CriminalIntentJSONSerializer(appContext, FILENAME);
		
		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			mCrimes = new ArrayList<Crime>();
			Log.e(TAG, "Error loading crimes: " + e);
		}
	}

    public void deleteCrime(Crime c){
        mCrimes.remove(c);
    }

	public void addCrime(Crime c){
		mCrimes.add(c);
	}
	
	public boolean saveCrimes(){
		try{
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e){
			Log.d(TAG, "Error saving crimes: " + e);
			return false;
		}
	}
	
	public ArrayList<Crime> getCrimes(){
		return mCrimes;
	}
	
	public Crime getCrime(UUID id){
		for(Crime c : mCrimes){
			if(c.getId().equals(id)){
				return c;
			}
		}
		return null;
	}
}
