package com.adc.criminalintent.app;

import android.app.Application;

import com.adc.criminalintent.CrimeListFragment;
import com.adc.criminalintent.module.CriminalIntentModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by daggreto on 2014.05.03.
 */
public class CriminalIntentApp extends Application{
    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(getModules().toArray());
    }

    public void inject(Object object) {
        graph.inject(object);
    }

    public List<Object> getModules(){
        return Arrays.<Object>asList(new CriminalIntentModule(this.getApplicationContext()));
    }
}
