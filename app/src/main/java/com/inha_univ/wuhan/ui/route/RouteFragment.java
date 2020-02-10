package com.inha_univ.wuhan.ui.route;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.inha_univ.wuhan.R;
import com.inha_univ.wuhan.db.ColorData;
import com.inha_univ.wuhan.db.LatLngWithIdx;
import com.inha_univ.wuhan.db.MapData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RouteFragment extends Fragment
        implements OnMapReadyCallback {
    private RouteViewModel routeViewModel;
    private GoogleMap map;
    private MapView mapView = null;
    public MarkerOptions[][] markerlist = new MarkerOptions[100][30];
    private int totalNum;   //총 확진자 수
    private final int diagnosisCapacity = 100;

    //color 저장
    int colorMap[] = new int[diagnosisCapacity]; //몇번째 확진자인지를 index로

    //경로 저장
    private ArrayList<MapData>[] pathList = new ArrayList[diagnosisCapacity];       //확진자별 정보
    private ArrayList<LatLngWithIdx>[] gpsData = new ArrayList[diagnosisCapacity];     //확진자 별 경로, gpsData[1]은 1번 확진자의 경로
    private MyAdapter3 myAdapter3;
    //색 지정
    float hue;
    float saturation = 13;
    float brightness = 14;
    float[] HSV = new float[3];
    public int numberofp = 0;
    public ArrayList<String> s = new ArrayList<>();
    public RouteFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup contatiner, @Nullable Bundle savedInstanceState){
        routeViewModel =
                ViewModelProviders.of(this).get(RouteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_route, contatiner,false);


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

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_popup, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(root.getContext());
        dialog.setContentView(dialogView);
        //리사이클러 뷰 객체 참조
        final RecyclerView recyclerView = (RecyclerView)dialog.findViewById(R.id.recycler2);
        Button b = (Button)root.findViewById(R.id.button);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                map.clear();
                for(int i=1; i<=totalNum; i++){
                    if(s.get(i-1) == "b"){
                        continue;
                    }
                    Log.e("onMapReady", "color");
                    Log.e("totalNum Size", totalNum+"");
                    MarkerOptions markerOptions = new MarkerOptions();
                    PolylineOptions polylineOptions;
                    List<LatLng> arrayPoints = new ArrayList<>();
                    //color 불러오기
                    int color = colorMap[i];
                    HSV[0] = color;
                    HSV[1] = saturation;
                    HSV[2] = brightness;
                    int rgb = Color.HSVToColor(HSV);
                    Log.e("gpsData Size", gpsData[i].size()+"");
                    for(int j=0; j<gpsData[i].size(); j++){
                        //MarkerOptions markerOptions = markerlist[i][j];
                        Log.e("gpsData", gpsData[i].size()+"");
                        //gpsData 배열로부터 위도, 경도 정보 불러오기
                        LatLng position = gpsData[i].get(j).latlng;
                        //경로 마다 comment 불러오기
                        String comment = pathList[i].get(j).getExplain();
                        //i번째 확진자에 대한 title 지정하기
                        markerOptions.title(i + "번째 확진자" + (j+1));
                        if(numberofp < i){
                            numberofp = i;
                            s.add("a");
                        }
                        //i번째 확진자에 대한 snippet 지정하기
                        markerOptions.snippet(comment);
                        //i번째 확진자에 대한 position 지정하기
                        markerOptions.position(position);
                        //i번째 확진자에 대한 색 지정하기
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));
                        map.addMarker(markerOptions);


                        polylineOptions = new PolylineOptions();
                        polylineOptions.width(7);
                        arrayPoints.add(position);
                        polylineOptions.color(rgb);
                        polylineOptions.addAll(arrayPoints);
                        map.addPolyline(polylineOptions);

                    }
                }
                //메시지 띄우기
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(getContext(), marker.getSnippet(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                /*↑↑↑↑↑↑↑↑↑↑디비에서 위치 정보 갖고 오는 부분 끝↑↑↑↑↑↑↑↑↑↑↑*/

                map.animateCamera(CameraUpdateFactory.zoomTo(7));
            }
        });
        for(int i = 0; i < numberofp; i++){
            s.add("a");
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //다이얼로그를 띄우기 전, 리스트에 있는 내용을 다이얼로그 안의 리사이클려뷰로 갱신해주는 코드

                myAdapter3 = new MyAdapter3(s);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter3);

                TextView dialogText = dialog.findViewById(R.id.dialogtext1);
                dialogText.setText(s.get(5));

                //다이얼로그를 띄우는 코드
                dialog.show();
            }
        });
        mapView = (MapView)root.findViewById(R.id.map);
        mapView.getMapAsync(this);
        return root;
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
        mapView.onDestroy();
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
        Log.e("onMapReady", "onon");
        LatLng CENTER = new LatLng(36.675801, 127.990564);
        map = googleMap;
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
            Log.e("onMapReady", "color");
            Log.e("totalNum Size", totalNum+"");
            MarkerOptions markerOptions = new MarkerOptions();
            PolylineOptions polylineOptions;
            List<LatLng> arrayPoints = new ArrayList<>();
            //color 불러오기
            int color = colorMap[i];
            HSV[0] = color;
            HSV[1] = saturation;
            HSV[2] = brightness;
            int rgb = Color.HSVToColor(HSV);
            Log.e("gpsData Size", gpsData[i].size()+"");
            for(int j=0; j<gpsData[i].size(); j++){
                //MarkerOptions markerOptions = markerlist[i][j];
                Log.e("gpsData", gpsData[i].size()+"");
                //gpsData 배열로부터 위도, 경도 정보 불러오기
                LatLng position = gpsData[i].get(j).latlng;
                //경로 마다 comment 불러오기
                String comment = pathList[i].get(j).getExplain();
                //i번째 확진자에 대한 title 지정하기
                markerOptions.title(i + "번째 확진자" + (j+1));
                if(numberofp < i){
                    numberofp = i;
                    s.add("a");
                }
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
