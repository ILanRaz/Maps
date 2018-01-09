package com.example.jbt.maps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddManual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manual);

        final EditText name, lat, longi;
        name = (EditText) findViewById(R.id.name);
        lat = (EditText) findViewById(R.id.lat);
        longi = (EditText) findViewById(R.id.longi);

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty())
                    Toast.makeText(AddManual.this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                else if (name.getText().toString().isEmpty())
                    Toast.makeText(AddManual.this, "Enter a valid latitude", Toast.LENGTH_SHORT).show();
                else if (name.getText().toString().isEmpty())
                    Toast.makeText(AddManual.this, "Enter a valid longitude", Toast.LENGTH_SHORT).show();
                else {
                    String namee = name.getText().toString();
                    String latitude = lat.getText().toString();
                    String longitude = longi.getText().toString();

                    MyPlace m = new MyPlace(namee, Double.parseDouble(latitude), Double.parseDouble(longitude));
                    m.save();

                    ArrayList<MyPlace> places = (ArrayList<MyPlace>) MyPlace.listAll(MyPlace.class);

                    MyPlace place1 = places.get(0);
                    MyPlace place2 = places.get(1);
                    MyPlace place3 = places.get(2);

                    place1.delete();
                    place2.delete();
                    place3.delete();
                    m.save();
                    (new MyPlace(place1.name, place1.lat, place1.lon)).save();
                    (new MyPlace(place2.name, place2.lat, place2.lon)).save();

                    finish();
                }
            }
        });
    }
}
