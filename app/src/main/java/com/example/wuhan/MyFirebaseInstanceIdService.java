package com.example.wuhan;
//디바이스에서 앱이 최초 실행되어 디바이스
// 토큰이 생성되거나 재생성 될 시 에 동작을 작성할 클래스
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
/*
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("IDService","Refreshed token : "+refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token){
        //디바이스 토큰이 생성되거나 재생성 될 시 동작할 코드 작성
    }
}*/


public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    String TAG="MyFirebaseInstanceIdService";

    public void onNewToken(String s){
        super.onNewToken(s);
        Log.d("New_Token",s);
    }
}