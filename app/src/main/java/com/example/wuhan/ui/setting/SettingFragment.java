package com.example.wuhan.ui.setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wuhan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("diag").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                //msg = getString(R.string.msg_subscribe_failed);
                                Log.d("push topic unsubscribe", "확진자 알림 해제 실패");
                            }
                            Log.d("push topic unsubscribe", "확진자 알림 해제");
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {  // 확진자 수 알림이 켜지는 것 감지
                    Toast.makeText(mActivity,"확진자 수 알림이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                    diagnose_onoff.setChecked(true);

                    FirebaseMessaging.getInstance().subscribeToTopic("diag").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                //msg = getString(R.string.msg_subscribe_failed);
                                Log.d("push topic subscribe", "확진자 알림 구독 실패");
                            }
                            Log.d("push topic subscribe", "확진자 알림 구독");
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                return false;
            }
        });

        route_onoff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {   // 경로 관련 알림 변화 리스너
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(route_onoff.isChecked()) {  // 경로 알림이 꺼지는 것 감지
                    Toast.makeText(mActivity,"경로 추가 알림이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                    route_onoff.setChecked(false);

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("map").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                //msg = getString(R.string.msg_subscribe_failed);
                                Log.d("push topic unsubscribe", "경로 알림 해제 실패");
                            }
                            Log.d("push topic unsubscribe", "경로 알림 해제");
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {  // 경로 알림이 켜지는 것 감지
                    Toast.makeText(mActivity,"경로 추가 알림이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                    route_onoff.setChecked(true);

                    FirebaseMessaging.getInstance().subscribeToTopic("map").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                //msg = getString(R.string.msg_subscribe_failed);
                                Log.d("push topic subscribe", "경로 알림 구독 실패");
                            }
                            Log.d("push topic subscribe", "경로 알림 구독");
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                return false;
            }
        });
    }

}
