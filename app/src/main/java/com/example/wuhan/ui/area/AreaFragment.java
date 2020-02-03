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
    private ArrayList<ItemObject2> list = new ArrayList(); //경인
    private ArrayList<ItemObject2> list2 = new ArrayList(); //서울
    private ArrayList<ItemObject2> list3 = new ArrayList(); //강원
    private ArrayList<ItemObject2> list4 = new ArrayList(); //충남
    private ArrayList<ItemObject2> list5 = new ArrayList(); //충북
    private ArrayList<ItemObject2> list6 = new ArrayList(); //경북
    private ArrayList<ItemObject2> list7 = new ArrayList(); //경남
    private ArrayList<ItemObject2> list8 = new ArrayList(); //전남
    private ArrayList<ItemObject2> list9 = new ArrayList(); //전북
    private ArrayList<ItemObject2> list10 = new ArrayList(); //제주
    private ArrayList<ItemObject2> list11 = new ArrayList(); //부산, 울산
    private AreaViewModel mapViewModel;
    private TextView gyeong_In,  seoul, kangwon, chung_nam,chung_buk, gyeong_buk, gyeong_nam, jun_nam, jun_buk, jeju, busan_ulsan;
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


        RecyclerView recyclerView2 = dialog.findViewById(R.id.recycler2);
        TextView dialogText = dialog.findViewById(R.id.dialogtext1);

        //다이얼로그 제목 바꾸는 코드
        dialogText.setText("1");

        //리사이클러뷰 아이템 추가 코드
        list2.add(new ItemObject2("날짜", "장소", "내용"));
        list2.add(new ItemObject2("1/29", "서울","우미관에서 식사"));

        list.add(new ItemObject2("날짜", "장소", "내용"));
        list.add(new ItemObject2("1/29", "인천","인천에서 산책"));
        //경인 버튼 추가
        gyeong_In = (TextView)root.findViewById(R.id.Gyeng_In);
        gyeong_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("경인");

                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        seoul = (TextView)root.findViewById(R.id.Seoul);
        seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list2);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("서울");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        kangwon = (TextView)root.findViewById(R.id.Gangwon);
        kangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list3);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("강원");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        chung_nam = (TextView)root.findViewById(R.id.Chung_Nam);
        chung_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list4);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("충남");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        chung_buk = (TextView)root.findViewById(R.id.Chung_Buck);
        chung_buk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list5);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("충북");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        gyeong_buk = (TextView)root.findViewById(R.id.Gyeong_buck);
        gyeong_buk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list6);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("경북");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        gyeong_nam = (TextView)root.findViewById(R.id.Gyeong_Nam);
        gyeong_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list7);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("경남");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        jun_buk = (TextView)root.findViewById(R.id.Jeon_Buck);
        jun_buk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list9);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("전북");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        jun_nam = (TextView)root.findViewById(R.id.Jeon_Nam);
        jun_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list8);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("전남");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        jeju = (TextView)root.findViewById(R.id.Jeju);
        jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list10);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("제주");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });

        busan_ulsan = (TextView)root.findViewById(R.id.Busan_Ulsan);
        busan_ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                MyAdapter2 myAdapter2 = new MyAdapter2(list11);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter2);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);

                //다이얼로그 제목 바꾸는 코드
                dialogText.setText("부산, 울산");
                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });
        return root;
    }
}