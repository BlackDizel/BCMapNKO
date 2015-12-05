package ru.byters.bcmapnko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import ru.byters.bcmapnko.model.Task;
import ru.byters.bcmapnko.utils.LocalData;

public class ActivityAddTask extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_LOCATION = 0;
    TextView tvLocation, tvTitle, tvDescription;
    private LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        tvLocation = (TextView) findViewById(R.id.bSelectLocation);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        tvLocation.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_task) {
            if (location != null && tvTitle.getText() != null && tvDescription.getText() != null) {
                LocalData.addData(new Task(
                        tvTitle.getText().toString()
                        , tvDescription.getText().toString()
                        , location.latitude
                        , location.longitude));
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK && requestCode == REQUEST_LOCATION) {
            if (data.getExtras() != null && data.getExtras().containsKey(ActivitySelectLocation.INTENT_COORDS))
                this.location = data.getParcelableExtra(ActivitySelectLocation.INTENT_COORDS);
            if (data.getExtras() != null && data.getExtras().containsKey(ActivitySelectLocation.INTENT_TITLE))
                tvLocation.setText(data.getStringExtra(ActivitySelectLocation.INTENT_TITLE));
        }
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this, ActivitySelectLocation.class), REQUEST_LOCATION);
    }
}
