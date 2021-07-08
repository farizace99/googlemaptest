package com.myapplicationdev.android.googlemaptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3;
    private GoogleMap map;
    Spinner spn;
    LatLng poi_East,poi_Central, poi_North;
    ArrayList<String>diffAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        spn = findViewById(R.id.spinner);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                poi_North = new LatLng(1.461708, 103.813500);
                Marker cp1 = map.addMarker(new
                        MarkerOptions()
                        .position(poi_North)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm Tel:65433456")
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on)));

                poi_Central = new LatLng(1.300542, 103.841226);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,
                        15));
                Marker cp2 = map.addMarker(new
                        MarkerOptions()
                        .position(poi_Central)
                        .title("HQ - Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542 Operating hours: 11am-8pm Tel:67788652")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                poi_East = new LatLng(1.350057, 103.934452);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,
                        15));
                Marker cp3 = map.addMarker(new
                        MarkerOptions()
                        .position(poi_East)
                        .title("HQ - East")
                        .snippet("Block 555, Tampines Ave 3, 287788 Operating hours: 9am-5pm Tel:66776677")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.getTitle().equals("HQ - North")) {
                            Toast.makeText(MainActivity.this, "HQ - North", Toast.LENGTH_SHORT).show();
                        } else if (marker.getTitle().equals("HQ - Central")) {
                            Toast.makeText(MainActivity.this, "HQ - Central", Toast.LENGTH_SHORT).show();
                        } else if (marker.getTitle().equals("HQ - East")) {
                            Toast.makeText(MainActivity.this, "HQ - East", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        UiSettings ui = map.getUiSettings();
                        ui.setCompassEnabled(true);
                        ui.setZoomControlsEnabled(true);

                        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION);

                        if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                            map.setMyLocationEnabled(true);
                        } else {
                            Log.e("GMap - Permission", "GPS access has not been granted");
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                        }

                        return false;
                    }
                });
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,15));
                        break;
                    case 1:
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,15));
                        break;
                    case 2:
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,15));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}