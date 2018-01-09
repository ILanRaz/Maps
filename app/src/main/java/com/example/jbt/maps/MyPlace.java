package com.example.jbt.maps;

import com.orm.SugarRecord;

/**
 * Created by jbt on 02/01/2018.
 */

public class MyPlace extends SugarRecord {
    String name;
    double lat;
    double lon;


    public MyPlace(){
    }


    public MyPlace(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
