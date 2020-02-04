package com.example.wuhan.ui.setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wuhan.R;

import java.util.prefs.Preferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

public class SettingFragment extends PreferenceFragmentCompat {
    private Context mContext;
    private Activity mActivity;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting, rootKey);

        mContext = this.getActivity();
        mActivity = this.getActivity();
        final SwitchPreferenceCompat diagnose_onoff = (SwitchPreferenceCompat) findPreference(getString(R.string.pref_key_diagnose));
        final SwitchPreferenceCompat route_onoff = (SwitchPreferenceCompat) findPreference(getString(R.string.pref_key_route));


        diagnose_onoff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {   // 확진자 수 관련 알림 변화 리스너
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(diagnose_onoff.isChecked()) {  // 확진자 수 알림이 꺼지는 것 감지
                    Toast.makeText(mActivity,"확진자 수 알림이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                    diagnose_onoff.setChecked(false);
                }else {  // 확진자 수 알림이 켜지는 것 감지
                    Toast.makeText(mActivity,"확진자 수 알림이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                    diagnose_onoff.setChecked(true);
                }
                return false;
            }
        });

        route_onoff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {   // 확진자 수 관련 알림 변화 리스너
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(route_onoff.isChecked()) {  // 확진자 수 알림이 꺼지는 것 감지
                    Toast.makeText(mActivity,"경로 추가 알림이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                    route_onoff.setChecked(false);
                }else {  // 확진자 수 알림이 켜지는 것 감지
                    Toast.makeText(mActivity,"경로 추가 알림이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                    route_onoff.setChecked(true);
                }
                return false;
            }
        });
    }

}
