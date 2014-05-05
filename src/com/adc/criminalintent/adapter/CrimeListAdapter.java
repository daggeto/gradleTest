package com.adc.criminalintent.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.adc.criminalintent.Crime;
import com.adc.criminalintent.CrimeLab;
import com.adc.criminalintent.R;
import com.adc.criminalintent.app.CriminalIntentApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by daggreto on 2014.05.04.
 */
@TargetApi(11)
public class CrimeListAdapter extends ArrayAdapter<Crime> {

    @Inject CrimeLab crimeLab;

    private Context context;

    public CrimeListAdapter(Context context){
        super(context, 0);
        this.context = context;
        ((CriminalIntentApp) context.getApplicationContext()).inject(this);
        this.addAll(crimeLab.getCrimes());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = ((FragmentActivity)context).getLayoutInflater().inflate(R.layout.list_item_crime, null);
        }

        Crime c = getItem(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
        titleTextView.setText(c.getTitle());

        TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
        dateTextView.setText(formatDate(c.getDate()));

        CheckBox solvedCheckBox = (CheckBox)  convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
        solvedCheckBox.setChecked(c.isSolved());

        return convertView;
    }

    @Override
    public int getCount() {
        return crimeLab.getCrimes().size();
    }

    private String formatDate(Date date){
        return DateFormat.format("EEEE, MMM d, yyyy", date).toString();
    }

}
