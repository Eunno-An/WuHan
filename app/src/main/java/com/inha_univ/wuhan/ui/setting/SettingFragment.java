package com.inha_univ.wuhan.ui.setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inha_univ.wuhan.MainActivity;
import com.inha_univ.wuhan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

public class SettingFragment extends PreferenceFragmentCompat {

    private Activity mActivity;
    private Boolean check1, check2, first;
    private SwitchPreferenceCompat diagnose_onoff, route_onoff, dead_onoff;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting, rootKey);

        mActivity = this.getActivity();
        diagnose_onoff = (SwitchPreferenceCompat) findPreference(getString(R.string.pref_key_diagnose));
        route_onoff = (SwitchPreferenceCompat) findPreference(getString(R.string.pref_key_route));
        dead_onoff = (SwitchPreferenceCompat) findPreference(getString(R.string.pref_key_dead));




        //main에서 보내준 bundle 값을 통해서
        //switchPreference 값 설정하기


        dead_onoff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {   // 확진자 수 관련 알림 변화 리스너
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(dead_onoff.isChecked()) {  // 사망자 수 알림이 꺼지는 것 감지
                    Toast.makeText(mActivity,"사망자 수 알림이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                    dead_onoff.setChecked(false);



                }else {  // 사망자 수 알림이 켜지는 것 감지
                    Toast.makeText(mActivity,"사망자 수 알림이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                    dead_onoff.setChecked(true);


                }
                return false;
            }
        });


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
