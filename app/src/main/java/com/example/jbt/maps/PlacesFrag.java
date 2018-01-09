package com.example.jbt.maps;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFrag extends Fragment {


    private static boolean allowRefresh;

    public PlacesFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_places, container, false);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.mainCont);

        ArrayList<MyPlace> places = (ArrayList<MyPlace>) MyPlace.listAll(MyPlace.class);

        //for (single item: from array)
        for (final MyPlace place : places) {
            Button placeButton = new Button(getActivity());
            placeButton.setText(place.name);
            placeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Toast.makeText(getActivity(), ""+place.lat+"  , "+ place.lon, Toast.LENGTH_SHORT).show();

                    MapChanger mapChanger = (MapChanger) getActivity();
                    mapChanger.changeMaps(place.lat, place.lon);


                }
            });
            linearLayout.addView(placeButton);
        }


        return v;
    }

    public void refresh() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
