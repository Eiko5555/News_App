package com.lesson_three.eiko.news_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eiko on 12/6/2016.
 */
public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
    }

    public static class NewsPreferenceFragment extends
            PreferenceFragment implements
            Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settingmain);

//            Preference section = findPreference(getString(
//                    R.string.setting_type_key ));
//            bindPreferenceSummerytoValue(section);

            Preference orderby = findPreference
                    (getString(R.string.setting_orderby_key));
            bindPreferenceSummerytoValue(orderby);
        }

        private void bindPreferenceSummerytoValue(Preference sort) {
       sort.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPref = PreferenceManager.
                    getDefaultSharedPreferences(sort.getContext());
            String string = sharedPref.getString(
                    sort.getKey(), "");
            onPreferenceChange(sort,string);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringvalue = value.toString();
            if (preference instanceof ListPreference){
                ListPreference listPrefs = (ListPreference)preference;
                int prefIndex = listPrefs.findIndexOfValue(stringvalue);
                if (prefIndex >= 0){
                    CharSequence[] label = listPrefs.getEntries();
                    preference.setSummary(label[prefIndex]);
                }
            }else {
                preference.setSummary(stringvalue);
            }
            return true;
        }
    }
}
