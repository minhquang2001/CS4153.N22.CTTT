package com.minhquang.alarm;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.khuongviettai.alarm.R;


public class Preferences extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);
    }
}

