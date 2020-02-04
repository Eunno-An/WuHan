package com.example.wuhan.ui.route;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wuhan.R;
import com.example.wuhan.db.ColorData;
import com.example.wuhan.db.LatLngWithIdx;
import com.example.wuhan.db.MapData;
import com.example.wuhan.ui.alert.AlertViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //색 지정
    float hue;
    float saturation = 13;
    float brightness = 14;
    float[] HSV = new float[3];
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
                Log.d("ming", "ming");
                Log.d("firebase totalNum", totalNum + "");
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

        /*↓↓↓↓↓↓↓↓↓↓디비에서 위치 정보 갖고 오는 부분↓↓↓↓↓↓↓↓↓↓↓↓*/
        //함수를 통해서 얻어온다.
//        LatLng INCHEON_INHAUNIV_HIGHTECH = new LatLng(37.450686, 126.657126);
//        MarkerOptions markerOptions0 = new MarkerOptions();
//        markerOptions0.position(INCHEON_INHAUNIV_HIGHTECH);
//        markerOptions0.title("인하대학교 하이테크센터");
//        markerOptions0.snippet("밑에 정보");
//        markerOptions0.icon(BitmapDescriptorFactory.defaultMarker(0));
//        googleMap.addMarker(markerOptions0);

        for(int i=1; i<=totalNum; i++){
            MarkerOptions markerOptions = new MarkerOptions();
            PolylineOptions polylineOptions;
            List<LatLng> arrayPoints = new ArrayList<>();
            //color 불러오기
            int color = colorMap[i];
            HSV[0] = color;
            HSV[1] = saturation;
            HSV[2] = brightness;
            int rgb = Color.HSVToColor(HSV);
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;
            for(int j=0; j<gpsData[i].size(); j++){
                //gpsData 배열로부터 위도, 경도 정보 불러오기
                LatLng position = gpsData[i].get(j).latlng;
                //경로 마다 comment 불러오기
                String comment = pathList[i].get(j).getExplain();
                //i번째 확진자에 대한 title 지정하기
                markerOptions.title(i + "번째 확진자" + (j+1));
                //i번째 확진자에 대한 snippet 지정하기
                markerOptions.snippet(comment);
                //i번째 확진자에 대한 position 지정하기
                markerOptions.position(position);
                //i번째 확진자에 대한 색 지정하기
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));
                googleMap.addMarker(markerOptions);

                polylineOptions = new PolylineOptions();
                polylineOptions.width(7);
                arrayPoints.add(position);
                polylineOptions.color(rgb);
                polylineOptions.addAll(arrayPoints);
                googleMap.addPolyline(polylineOptions);


            }
        }
        //메시지 띄우기
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getContext(), marker.getSnippet(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
        /*↑↑↑↑↑↑↑↑↑↑디비에서 위치 정보 갖고 오는 부분 끝↑↑↑↑↑↑↑↑↑↑↑*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(CENTER));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7));
    }
}
