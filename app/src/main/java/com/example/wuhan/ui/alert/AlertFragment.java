package com.example.wuhan.ui.alert;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuhan.R;
import com.example.wuhan.db.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class AlertFragment extends Fragment {
    private AlertViewModel alertViewModel;

    //데이터베이스에서 읽어올 정보들!
    private Patient patient;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState){
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alert, contatiner,false);


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //확진자 수
        DatabaseReference diagnosisRef = database.getReference("main");
        diagnosisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                patient = dataSnapshot.getValue(Patient.class);
                Log.d("firebase - 확진자수 읽기", patient.getDiagnosis() + "");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("firebase - 확진자수 읽기 에러", "Failed to read value.", error.toException());
            }
        });
    }
}
