package ru.byters.bcmapnko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.List;

import ru.byters.bcmapnko.model.Task;
import ru.byters.bcmapnko.utils.LocalData;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private List<Task> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    void setData() {
        if (mMap == null) return;

        mMap.clear();

        data = LocalData.readData(this);

        if (data == null) return;

        Calendar currentDate = Calendar.getInstance();
        for (Task item : data) {

            if (item.getCalendar().before(currentDate))
                continue;

            LatLng sydney = new LatLng(item.getLatitude(), item.getLongitude());

            //todo Marker m used in clicklistener to check selected marker
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title(item.getTitle())
                    .snippet(item.getDescription()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_task) {
            startActivity(new Intent(this, ActivityAddTask.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //todo add navigate to details activity
        return false;
    }
}
