package com.mikehuff.takehomeapplication.persistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {

    private final SharedPreferences prefs;

    private String LAST_UPDATE_TIMESTAMP_KEY = "last_time_updated";

    public SharedPrefs(Context context) {
      this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setRecentUpdateTime(long updateTime) {
      SharedPreferences.Editor editor = prefs.edit();
      editor.putLong(LAST_UPDATE_TIMESTAMP_KEY, updateTime);
      editor.apply();
    }

    public long getRecentSyncTime() {
      return prefs.getLong(LAST_UPDATE_TIMESTAMP_KEY, 0);
    }

}
