package com.inha_univ.wuhan.ui.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.inha_univ.wuhan.MainActivity;
import com.inha_univ.wuhan.R;
import com.inha_univ.wuhan.db.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AlertFragment extends Fragment {
    private AlertViewModel alertViewModel;

    //데이터베이스에서 읽어올 정보들!
    private Patient patient;
    private LottieAnimationView levelAnimationView;
    private TextView alertTextView;

    private TextView deathTextView; //사망자 수
    private TextView ingTextView; // 검사중 수
    private TextView outTextView;  // 격리해제(결과음성) 수
    private TextView confirmTextView; // 확진자 수
    private TextView outDiagTextView;   //확진자 격리해제 수

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState){
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_alert, contatiner,false);
        super.onCreate(savedInstanceState);


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //확진자 수
        DatabaseReference diagnosisRef = database.getReference("main");
        diagnosisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                patient = dataSnapshot.getValue(Patient.class);
                String level = patient.getLevel();
                levelAnimationView = (LottieAnimationView) root.findViewById(R.id.animation_view);
                alertTextView = (TextView)root.findViewById(R.id.main_text);
                if(level.equals("관심")){
                    levelAnimationView.setAnimation(R.raw.alert_blue);
                    alertTextView.setText("관심");
                    alertTextView.setTextColor(getResources().getColor(R.color.colorBlue));
                }else if(level.equals("주의")){
                    levelAnimationView.setAnimation(R.raw.alert_yellow);
                    alertTextView.setText("주의");
                    alertTextView.setTextColor(getResources().getColor(R.color.colorYellow));
                }
                else if(level.equals("경계")){
                    levelAnimationView.setAnimation(R.raw.alert_orange);
                    alertTextView.setText("경계");
                    alertTextView.setTextColor(getResources().getColor(R.color.colorOrange));
                }
                else if(level.equals("심각")){
                    levelAnimationView.setAnimation(R.raw.alert_red);
                    alertTextView.setText("심각");
                    alertTextView.setTextColor(getResources().getColor(R.color.colorRed));
                }

                deathTextView = (TextView)root.findViewById(R.id.death_num);
                ingTextView = (TextView)root.findViewById(R.id.ing_num);
                confirmTextView = (TextView)root.findViewById(R.id.confirm_num);
                outTextView = (TextView)root.findViewById(R.id.out_num);

                deathTextView.setText(Integer.toString(patient.getFatality()));//getFatality는 int
                ingTextView.setText(Integer.toString(patient.getIng()));
                outTextView.setText(Integer.toString(patient.getOut()));
                confirmTextView.setText(Integer.toString(patient.getDiagnosis()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("firebase - 확진자수 읽기 에러", "Failed to read value.", error.toException());
            }
        });

        //확진자 격리해제 수
        DatabaseReference outDiagRef = database.getReference("outDiag");
        outDiagRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int outDiagNum = ((Long) dataSnapshot.getValue()).intValue();
                outDiagTextView = (TextView)root.findViewById(R.id.outDiag_num);
                outDiagTextView.setText(outDiagNum+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("firebase - 확진자수 읽기 에러", "Failed to read value.", error.toException());
            }
        });
        return root;
    }




}
