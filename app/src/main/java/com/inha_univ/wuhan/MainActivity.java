package com.inha_univ.wuhan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.inha_univ.wuhan.ui.route.RouteFragment;
import com.inha_univ.wuhan.ui.setting.SettingFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private AdView mAdView;
    private Activity mActivity;
    //solchan commit
    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imageView;
    private long backKeyPressedTime = 0;
    private Toast toast;

    private boolean check1, check2, check3, first;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first = CheckAppFirstExecute();
        if(first) {
            new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setMessage("\"확진자 수 변화에 따른 푸시 알람에 동의하십니까?\"")
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check1 = false;
                            dialog.dismiss();
                        }
                    }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "확진자 수 변화 푸시 알림에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                    changePrefValue1();
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
                    check1 = true;
                }
            }).show();
            new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setMessage("\"확진자 경로 알림에 따른 푸시 알람에 동의하십니까?\"")
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check2 = false;
                            dialog.dismiss();
                        }
                    }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "확진자 경로 추가 푸시 알림에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                    changePrefValue2();
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
                    check2 = true;
                }
            }).show();
            new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setMessage("\"사망자 수 변화에 따른 푸시 알람에 동의하십니까?\"")
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check3 = false;
                            dialog.dismiss();
                        }
                    }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "사망자 수 변화 푸시 알림에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                    changePrefValue3();

                    FirebaseMessaging.getInstance().subscribeToTopic("fatality").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //String msg = getString(R.string.msg_subscribed);
                            if (!task.isSuccessful()) {
                                //msg = getString(R.string.msg_subscribe_failed);
                                Log.d("push topic subscribe", "사망자 알림 구독 실패");
                            }
                            Log.d("push topic subscribe", "사망자 알림 구독");
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                    check3 = true;
                }
            }).show();
            Log.e("test First", "true");
        }
        else{
            Log.e("test First", "false");
        }


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //nav_route nav_notificationnav_areanav_alert
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_alert, R.id.nav_area, R.id.nav_notification, R.id.nav_route, R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        //↓↓↓↓↓↓↓↓↓↓↓↓DB에서 경보 단계 불러오기 시작!↓↓↓↓↓↓↓↓↓↓↓↓
        //1. DB에서 경보 단계를 불러온다
        //2. 경보 단계에 따라 다중 조건문으로 ImageView 객체를 수정한다.

        //↑↑↑↑↑↑↑↑↑↑↑↑DB에서 경보 단계 불러오기 끝!↑↑↑↑↑↑↑↑↑↑↑↑
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed(){
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            //아얘 앱이 종료되게 함 그래서 fragment lifecycle 또한 모두 죽임.
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }



    public boolean CheckAppFirstExecute(){
        SharedPreferences pref = getSharedPreferences("IsFirst" , Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.apply();
        }
        return !isFirst;
    }
    public void changePrefValue1() {
        //SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref_key_diagnose), MODE_PRIVATE);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putBoolean(getString(R.string.pref_key_diagnose),true);
        edit.commit();
        Log.e("changePrefValue","change to true");
    }
    public void changePrefValue2(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putBoolean(getString(R.string.pref_key_route),true);
        edit.commit();
        Log.e("changePrefValue","change to true");
    }
    public void changePrefValue3(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putBoolean(getString(R.string.pref_key_dead),true);
        edit.commit();
        Log.e("changePrefValue","change to true");
    }
}
