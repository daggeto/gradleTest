package com.adc.criminalintent.module;

import android.content.Context;
import android.widget.ListAdapter;

import com.adc.criminalintent.CrimeFragment;
import com.adc.criminalintent.CrimeLab;
import com.adc.criminalintent.CrimeListFragment;
import com.adc.criminalintent.CrimePagerActivity;
import com.adc.criminalintent.adapter.CrimeListAdapter;
import com.adc.criminalintent.listener.CrimeMultiChoiceModeListener;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daggreto on 2014.05.03.
 */
@Module(
        injects = {CrimeListFragment.class, CrimeFragment.class, CrimePagerActivity.class, CrimeMultiChoiceModeListener.class, CrimeListAdapter.class})
public class CriminalIntentModule {

    private Context context;

    public CriminalIntentModule(Context context){
        this.context = context;
    }

    @Provides @Singleton CrimeLab provideCrimeLab(){
        return new CrimeLab(context);
    }
}
