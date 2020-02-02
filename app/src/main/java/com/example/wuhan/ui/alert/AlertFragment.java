package com.example.wuhan.ui.alert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuhan.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class AlertFragment extends Fragment {
    private AlertViewModel alertViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState){
        alertViewModel =
                ViewModelProviders.of(this).get(AlertViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alert, contatiner,false);


        return root;
    }
}
