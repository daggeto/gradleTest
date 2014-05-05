package com.adc.criminalintent.adapter;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by daggreto on 2014.05.04.
 */
public class CrimeListAdapter extends ArrayAdapter<Crime> {

    @Inject CrimeLab crimeLab;

    private FragmentActivity context;

    public CrimeListAdapter(FragmentActivity context){
        super(context, 0);
        this.addAll(crimeLab.getCrimes().toArray());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = context.getLayoutInflater().inflate(R.layout.list_item_crime, null);
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
