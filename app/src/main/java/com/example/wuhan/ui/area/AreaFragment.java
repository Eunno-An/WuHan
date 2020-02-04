package com.example.wuhan.ui.area;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuhan.R;
import com.example.wuhan.db.MapData;
import com.example.wuhan.ui.route.RouteFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Button gyeong_In,  seoul, kangwon, chung_nam,chung_buk, gyeong_buk, gyeong_nam, jun_nam, jun_buk, jeju, busan_ulsan;
    private RouteFragment routeFragment;

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

        routeFragment = new RouteFragment();
        RecyclerView recyclerView2 = dialog.findViewById(R.id.recycler2);
        TextView dialogText = dialog.findViewById(R.id.dialogtext1);

        //다이얼로그 제목 바꾸는 코드
        dialogText.setText("1");

        //리사이클러뷰 아이템 추가 코드

        //db연동
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mapRef = database.getReference("map");
        mapRef.orderByChild("date");
        mapRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("firebase", "onChildAdded:" + dataSnapshot.getKey());

                MapData tmp = dataSnapshot.getValue(MapData.class);
                String date = Integer.valueOf(tmp.getDate()).toString().substring(4,6) + "/" + Integer.valueOf(tmp.getDate()).toString().substring(6);
                ItemObject2 item = new ItemObject2(date, tmp.getCity(), tmp.getExplain());
                if(tmp.getCity().equals("경기/인천")){
                    list.add(item);
                }
                else if(tmp.getCity().equals("서울")){
                    list2.add(item);
                }
                else if(tmp.getCity().equals("강원")){
                    list3.add(item);
                }
                else if(tmp.getCity().equals("충남")){
                    list4.add(item);
                }
                else if(tmp.getCity().equals("충북")){
                    list5.add(item);
                }
                else if(tmp.getCity().equals("경북")){
                    list6.add(item);
                }
                else if(tmp.getCity().equals("경남")){
                    list7.add(item);
                }
                else if(tmp.getCity().equals("전남")){
                    list8.add(item);
                }
                else if(tmp.getCity().equals("전북")){
                    list9.add(item);
                }
                else if(tmp.getCity().equals("제주")){
                    list10.add(item);
                }
                else if(tmp.getCity().equals("부산/울산")){
                    list11.add(item);
                }
                gyeong_In.setText(Integer.toString(list.size()));
                seoul.setText(Integer.toString(list2.size()));
                kangwon.setText(Integer.toString(list3.size()));
                chung_nam.setText(Integer.toString(list4.size()));
                chung_buk.setText(Integer.toString(list5.size()));
                gyeong_buk.setText(Integer.toString(list6.size()));
                gyeong_nam.setText(Integer.toString(list7.size()));
                jun_nam.setText(Integer.toString(list8.size()));
                jun_buk.setText(Integer.toString(list9.size()));
                jeju.setText(Integer.toString(list10.size()));
                busan_ulsan.setText(Integer.toString(list11.size()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("firebase", "error to load", databaseError.toException());

            }
        });

        //경인 버튼 추가
        gyeong_In = (Button)root.findViewById(R.id.gyeongInBtn);
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

        seoul = (Button)root.findViewById(R.id.seoulBtn);
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

        kangwon = (Button)root.findViewById(R.id.gangwon_Btn);
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

        chung_nam = (Button)root.findViewById(R.id.chung_nam_Btn);
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

        chung_buk = (Button)root.findViewById(R.id.chung_buck_Btn);
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

        gyeong_buk = (Button)root.findViewById(R.id.gyeong_buck_Btn);
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

        gyeong_nam = (Button)root.findViewById(R.id.gyeong_nam_Btn);
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

        jun_buk = (Button)root.findViewById(R.id.jeon_buk_Btn);
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

        jun_nam = (Button)root.findViewById(R.id.jeon_nam_Btn);
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

        jeju = (Button)root.findViewById(R.id.jeju_Btn);
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

        busan_ulsan = (Button)root.findViewById(R.id.busan_ulsan_Btn);
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