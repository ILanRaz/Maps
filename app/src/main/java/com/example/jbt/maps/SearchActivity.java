package com.example.jbt.maps;

import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    EditText loc;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MapFragment mapFragment = new MapFragment();
        getFragmentManager().beginTransaction().replace(R.id.bottomCont, mapFragment).commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                LatLng latLng = new LatLng(34.1525200, 72.12548556);
                float zoom = 15;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("");
                        googleMap.addMarker(marker);
                        location = latLng;
                    }
                });

            }


        });
        loc = (EditText) findViewById(R.id.location_name);
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loc.getText().toString().isEmpty()) {
                    Toast.makeText(SearchActivity.this, "Enter location name", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<MyPlace> places = (ArrayList<MyPlace>) MyPlace.listAll(MyPlace.class);
                     /*Run only once just to create dummy places*/
                    MyPlace place1 = places.get(0);
                    MyPlace place2 = places.get(1);
                    MyPlace place3 = places.get(2);

                    MyPlace nplace1 = new MyPlace(loc.getText().toString(), location.latitude, location.longitude);

                    place1.delete();
                    place2.delete();
                    place3.delete();
                    nplace1.save();
                    (new MyPlace(place1.name, place1.lat, place1.lon)).save();
                    (new MyPlace(place2.name, place2.lat, place2.lon)).save();

                    finish();
                }
            }
        });
    }

    private String getAddressFromLocation(double latitude, double longitude) {
        Toast.makeText(this, "  " + latitude, Toast.LENGTH_SHORT).show();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
                }

                loc.setText("Address " + strAddress.toString());
                return strAddress.toString();

            } else {
                loc.setText("Searching Current Address");
                return "cant find";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Something went wrong";
    }
}
