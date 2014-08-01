package tw.fatminmin.xposed.networkspeedindicator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private static final String TAG = SettingsActivity.class.getSimpleName();
	private SharedPreferences mPrefs;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
		addPreferencesFromResource(R.xml.settings);
		mPrefs = getPreferenceScreen().getSharedPreferences();
		
	}

	@Override
	public void onResume() {
		super.onResume();
		
		@SuppressWarnings("deprecation")
		PreferenceGroup settings = (PreferenceGroup) findPreference("settings");
		for(int i = 0; i < settings.getPreferenceCount(); i++) {
		    setSummary(settings.getPreference(i));
		}
//		colorEnable();
		
		mPrefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		mPrefs.unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

	
    boolean setSummary(Preference preference) {
	    if (preference instanceof ListPreference) {
            ListPreference listPref = (ListPreference) preference;
            preference.setSummary(listPref.getEntry());
            return true;
        }
        else if(preference instanceof EditTextPreference) {
            EditTextPreference editPref = (EditTextPreference) preference;
            editPref.setSummary(editPref.getText());
            return true;
        }
        else {
            return false;
        }
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		Intent intent = new Intent();
		Log.i(TAG, "onSharedPreferenceChanged "+key);
		
		setSummary(findPreference(key));
		
		if(key.equals(Common.KEY_SHOW_UPLOAD_SPEED)) {
		    
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
		    intent.putExtra(Common.KEY_SHOW_UPLOAD_SPEED, 
		            prefs.getBoolean(Common.KEY_SHOW_UPLOAD_SPEED, Common.DEF_SHOW_UPLOAD_SPEED));
		    
		}
		else if(key.equals(Common.KEY_SHOW_DOWNLOAD_SPEED)) {
		    
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
		    intent.putExtra(Common.KEY_SHOW_DOWNLOAD_SPEED, 
                    prefs.getBoolean(Common.KEY_SHOW_DOWNLOAD_SPEED, Common.DEF_SHOW_DOWNLOAD_SPEED));
		    
		}
		else if(key.equals(Common.KEY_SHOW_DOWNLOAD_SPEED)) {
		    
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
		}
		else if (key.equals(Common.KEY_FORCE_UNIT)) {
		    
			intent.setAction(Common.ACTION_SETTINGS_CHANGED);
			intent.putExtra(Common.KEY_FORCE_UNIT,
					Common.getPrefInt(prefs, Common.KEY_FORCE_UNIT, Common.DEF_FORCE_UNIT));
		}
		else if (key.equals(Common.KEY_HIDE_UNIT)) {
		    
			intent.setAction(Common.ACTION_SETTINGS_CHANGED);
			intent.putExtra(Common.KEY_HIDE_UNIT, prefs.getBoolean(Common.KEY_HIDE_UNIT, Common.DEF_HIDE_UNIT));
			
		}
		else if (key.equals(Common.KEY_HIDE_INACTIVE)) {
			
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
			intent.putExtra(Common.KEY_HIDE_INACTIVE,
					prefs.getBoolean(Common.KEY_HIDE_INACTIVE, Common.DEF_HIDE_INACTIVE));
			
		}
		else if(key.equals(Common.KEY_FONT_SIZE)) {
		    
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
		    intent.putExtra(Common.KEY_FONT_SIZE,
		            Common.getPrefInt(prefs, Common.KEY_FONT_SIZE, Common.DEF_FONT_SIZE));
		    
		    findPreference(Common.KEY_FONT_SIZE).setSummary(prefs.getString(Common.KEY_FONT_SIZE, "10"));
		    
		}
		else if(key.equals(Common.KEY_POSITION)) {
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
		    intent.putExtra(Common.KEY_POSITION, 
		            Common.getPrefInt(prefs, Common.KEY_POSITION, Common.DEF_POSITION));
		}
		else if(key.equals(Common.KEY_SUFFIX)) {
            intent.setAction(Common.ACTION_SETTINGS_CHANGED);
            intent.putExtra(Common.KEY_SUFFIX, 
                    Common.getPrefInt(prefs, Common.KEY_SUFFIX, Common.DEF_SUFFIX));
        }
		else if(key.equals(Common.KEY_NETWORK_TYPE)) {
		    intent.setAction(Common.ACTION_SETTINGS_CHANGED);
		    intent.putExtra(Common.KEY_NETWORK_TYPE, prefs.getString(Common.KEY_NETWORK_TYPE, Common.DEF_NETWORK_TYPE));
		}
		else if(key.equals(Common.KEY_DISPLAY)) {
            intent.setAction(Common.ACTION_SETTINGS_CHANGED);
            intent.putExtra(Common.KEY_DISPLAY, 
                    Common.getPrefInt(prefs, Common.KEY_DISPLAY, Common.DEF_DISPLAY));
        }
		else if(key.equals(Common.KEY_UPDATE_INTERVAL))
		{
            intent.setAction(Common.ACTION_SETTINGS_CHANGED);
            intent.putExtra(Common.KEY_UPDATE_INTERVAL, 
                    Common.getPrefInt(prefs, Common.KEY_UPDATE_INTERVAL, Common.DEF_UPDATE_INTERVALE));
      }
//		else if(key.equals(Common.KEY_COLOR_MODE))
//		{
//			intent.setAction(Common.ACTION_SETTINGS_CHANGED);
//			intent.putExtra(Common.KEY_COLOR_MODE, Common.getPrefInt(prefs, Common.KEY_COLOR_MODE, Common.DEF_COLOR_MODE));
//			colorEnable();
//		}
		else if(key.equals(Common.KEY_COLOR))
		{
			intent.setAction(Common.ACTION_SETTINGS_CHANGED);
			intent.putExtra(Common.KEY_COLOR, prefs.getInt(Common.KEY_COLOR, Common.DEF_COLOR));
		}
			
		if (intent.getAction() != null) {
			sendBroadcast(intent);
			Log.i(TAG, "sendBroadcast");
		}
	}
	
//	@SuppressWarnings("deprecation")
//	void colorEnable() {
//		if(Common.getPrefInt(mPrefs, Common.KEY_COLOR_MODE, Common.DEF_COLOR_MODE) == 1) {
//			findPreference(Common.KEY_COLOR).setEnabled(true);
//		}
//		else {
//			findPreference(Common.KEY_COLOR).setEnabled(false);
//		}
//	}
	
}
