package com.example.wuhan.ui.area;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuhan.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class AreaFragment extends Fragment {
    private ArrayList<ItemObject2> list = new ArrayList();
    private AreaViewModel mapViewModel;
    private TextView gyeong_In;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(AreaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_area, container, false);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_content, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(root.getContext());
        dialog.setContentView(dialogView);
        //리사이클러 뷰 객체 참조
        final RecyclerView recyclerView = (RecyclerView)dialog.findViewById(R.id.recycler2);
        //텍스트 뷰 객체 참조
        gyeong_In = (TextView)root.findViewById(R.id.Gyeng_In);


        RecyclerView recyclerView2 = dialog.findViewById(R.id.recycler2);
        TextView dialogText = dialog.findViewById(R.id.dialogtext1);

        //다이얼로그 제목 바꾸는 코드
        dialogText.setText("1");

        //리사이클러뷰 아이템 추가 코드
        list.add(new ItemObject2("날짜", "장소", "내용"));
        list.add(new ItemObject2("1/29", "서울","우미관에서 식사"));
        //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

        MyAdapter2 myAdapter2 = new MyAdapter2(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter2);


        //다이얼로그 띄우는 코드
        dialog.show();
        return root;
    }
}