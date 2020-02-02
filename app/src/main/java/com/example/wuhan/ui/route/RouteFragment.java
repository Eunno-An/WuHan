package com.example.wuhan.ui.route;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuhan.R;
import com.example.wuhan.ui.alert.AlertViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class RouteFragment extends Fragment
        implements OnMapReadyCallback {
    private RouteViewModel routeViewModel;

    private MapView mapView = null;

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
