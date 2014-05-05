package com.adc.criminalintent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {

	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	
	public Crime(){
		mId = UUID.randomUUID();
		mDate = new Date();	
	}

	public Crime(JSONObject json) throws JSONException{
		mId = UUID.fromString(json.getString(JSON_ID));
        if(!json.isNull(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
		mSolved = json.getBoolean(JSON_SOLVED);
        try {
            mDate = new SimpleDateFormat("EEEE, MMM d, yyyy").parse(json.getString(JSON_DATE));
        } catch (ParseException e) {
            mDate = new Date();
        }

    }
	
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_DATE, android.text.format.DateFormat.format("EEEE, MMM d, yyyy", mDate).toString());
		
		return json;
		
	}
	
	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public UUID getId() {
		return mId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date mDate) {
		this.mDate = mDate;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean mSolved) {
		this.mSolved = mSolved;
	}
	
	@Override
	public String toString() {
		return mTitle;
	}
	
}
