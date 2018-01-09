package com.example.jbt.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.orm.SugarContext;

public class MainActivity extends AppCompatActivity implements MapChanger {

    PlacesFrag placesFrag;
    private boolean pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SugarContext.init(this);

        /*Run only once just to create dummy places*/
        MyPlace place1 = new MyPlace("New York", 40.6974034, 74.1197632);
        MyPlace place2 = new MyPlace("Arizona", 40.586021, 73.6913553);
        MyPlace place3 = new MyPlace("Los Angeles", 34.0201613, 118.6919126);

        if (MyPlace.listAll(MyPlace.class).size() == 0) {
            place1.save();
            place2.save();
            place3.save();
        }

        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        placesFrag = new PlacesFrag();
        getFragmentManager().beginTransaction().add(R.id.topCont, placesFrag).commit();
    }


    @Override
    public void changeMaps(final double lat, final double lon) {
        MapFragment mapFragment = new MapFragment();
        getFragmentManager().beginTransaction().replace(R.id.bottomCont, mapFragment).commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                LatLng latLng = new LatLng(lat, lon);
                float zoom = 15;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pause)
            if (placesFrag != null)
                placesFrag.refresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exit) {
            finish();
        } else {
            startActivity(new Intent(this, AddManual.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
