package com.adc.criminalintent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class CriminalIntentJSONSerializer {

	private static final String APP_DIR_NAME = "CriminalIntent";
    private static final String TAG = "CrimeLab";
	
	private Context mContext;
	private String mFileName;
	
	public CriminalIntentJSONSerializer(Context c, String f) {
		this.mContext = c;
		this.mFileName = f;
	}

    private File getFolder(){
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + APP_DIR_NAME);
    }

	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,
			IOException {
		JSONArray array = new JSONArray();

		for (Crime c : crimes) {
			array.put(c.toJSON());
		}

		Writer writer = null;
		try {
			OutputStream out = resolveOutpuStream();
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

    private  boolean isStorageMounted(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

	private OutputStream resolveOutpuStream() throws FileNotFoundException{
		if(isStorageMounted()){
			return new FileOutputStream(createExternalFile());
		}
		
		return mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
	}

    private InputStream resolveInputStream() throws FileNotFoundException{

        if(isStorageMounted()){
            return new FileInputStream(new File(getFolder() + "/" + mFileName));
        }

        return mContext.openFileInput(mFileName);
    }
	
	private File createExternalFile(){
		File dir = getFolder();
		dir.mkdirs();
		return new File (dir, mFileName);
	}
	
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException{
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		
		try{

			InputStream in = resolveInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine()) != null){
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			
			for(int i = 0; i <array.length(); i++){
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e){
			Log.e(TAG, e.toString());
		} finally {
			if(reader != null){
				reader.close();
			}
		}
		
		return crimes;
	}
	
	
}
