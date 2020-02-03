package com.example.wuhan.ui.route;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuhan.R;
import com.example.wuhan.db.ColorData;
import com.example.wuhan.db.LatLngWithIdx;
import com.example.wuhan.db.MapData;
import com.example.wuhan.ui.alert.AlertViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RouteFragment extends Fragment
        implements OnMapReadyCallback {
    private RouteViewModel routeViewModel;

    private MapView mapView = null;

    private int totalNum;   //총 확진자 수
    private final int diagnosisCapacity = 100;

    //color 저장
    int colorMap[] = new int[diagnosisCapacity]; //몇번째 확진자인지를 index로

    //경로 저장
    private ArrayList<MapData>[] pathList = new ArrayList[diagnosisCapacity];       //확진자별 정보
    private ArrayList<LatLngWithIdx>[] gpsData = new ArrayList[diagnosisCapacity];     //확진자 별 경로, gpsData[1]은 1번 확진자의 경로


    public RouteFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup contatiner, @Nullable Bundle savedInstanceState){
        routeViewModel =
                ViewModelProviders.of(this).get(RouteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_route, contatiner,false);
        mapView = (MapView)root.findViewById(R.id.map);
        mapView.getMapAsync(this);
        return root;
    }
    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //여기부터 디비 추가!!!!
        for(int i = 0; i < diagnosisCapacity; i++){
            gpsData[i] = new ArrayList<>();
            pathList[i] = new ArrayList<>();
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //확진자 수
        DatabaseReference diagnosisRef = database.getReference("main/diagnosis");
        diagnosisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                totalNum = ((Long) dataSnapshot.getValue()).intValue();
                Log.d("firebase - 확진자수 읽기", totalNum + "");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("firebase - 확진자수 읽기 에러", "Failed to read value.", error.toException());
            }
        });

        //색 정보 받아오기
        DatabaseReference colorRef = database.getReference("color");

        colorRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ColorData colorData = dataSnapshot.getValue(ColorData.class);
                colorMap[Integer.valueOf(colorData.getIdx())] = colorData.getColor();
                Log.d("firebase-color added", colorData.getColor() + "");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ColorData colorData = dataSnapshot.getValue(ColorData.class);
                colorMap[Integer.valueOf(colorData.getIdx())] = colorData.getColor();
                Log.d("firebase-color changed", colorData.getColor() + "");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });



        //경로 받아오기
        DatabaseReference pathRef = database.getReference("map");
        pathRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MapData mapData = dataSnapshot.getValue(MapData.class);
                pathList[mapData.getDiagNum()].add(mapData);
                gpsData[mapData.getDiagNum()].add(new LatLngWithIdx(mapData.getIdx(), mapData.getLatitude(), mapData.getLongitude()));
                Log.d("firebase-path added", mapData.getIdx() + "");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MapData mapData = dataSnapshot.getValue(MapData.class);
                pathList[mapData.getDiagNum()].add(mapData);
                gpsData[mapData.getDiagNum()].add(new LatLngWithIdx(mapData.getIdx(), mapData.getLatitude(), mapData.getLongitude()));
                Log.d("firebase-path changed", mapData.getIdx() + "");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng CENTER = new LatLng(36.675801, 127.990564);
        /*마커 옵션 수정하는 부분
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("서울");
//        markerOptions.snippet("수도");
//        googleMap.addMarker(markerOptions);
         */

        /*↓↓↓↓↓↓↓↓↓↓디비에서 위치 정보 갖고 오는 부분↓↓↓↓↓↓↓↓↓↓↓↓*/
        //함수를 통해서 얻어온다.
        /*↑↑↑↑↑↑↑↑↑↑디비에서 위치 정보 갖고 오는 부분 끝↑↑↑↑↑↑↑↑↑↑↑*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(CENTER));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7));
    }
}
