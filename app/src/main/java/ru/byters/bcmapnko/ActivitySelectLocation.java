package ru.byters.bcmapnko;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ActivitySelectLocation extends FragmentActivity
        implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    public static final String INTENT_COORDS = "coords";
    public static final String INTENT_TITLE = "title";
    LatLng coordinates;
    String shortLocalityName;
    Button pickButton;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pickButton = (Button) findViewById(R.id.queryButton);
        pickButton.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
/*
        LatLng pos = new LatLng(55.7522, 37.6155);
        mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Moscow"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));*/
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_COORDS, coordinates);
        intent.putExtra(INTENT_TITLE, shortLocalityName);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        pickButton.setVisibility(View.GONE);
        mMap.addMarker(new MarkerOptions().position(latLng).title(""));

        Geocoder geocoder = new Geocoder(ActivitySelectLocation.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            coordinates = latLng;

            String address = "";
            for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); ++i)
                address += addresses.get(0).getAddressLine(i) + " ";

            shortLocalityName = address;
            pickButton.setVisibility(View.VISIBLE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
