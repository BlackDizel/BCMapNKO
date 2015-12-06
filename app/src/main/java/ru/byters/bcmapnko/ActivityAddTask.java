package ru.byters.bcmapnko;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import ru.byters.bcmapnko.model.Task;
import ru.byters.bcmapnko.utils.LocalData;

public class ActivityAddTask extends AppCompatActivity
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static int REQUEST_LOCATION = 0;
    TextView tvLocation, tvTitle, tvDescription, tvContacts, tvDate;
    private LatLng location;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        tvLocation = (TextView) findViewById(R.id.btSelectLocation);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvContacts = (TextView) findViewById(R.id.tvContacts);
        tvDate = (TextView) findViewById(R.id.btSelectDate);

        tvLocation.setOnClickListener(this);
        tvDate.setOnClickListener(this);
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
            if (location != null
                    && tvTitle.getText() != null
                    && tvDescription.getText() != null
                    && tvContacts.getText() != null
                    && selectedDate != null) {
                LocalData.addData(
                        this,
                        new Task(
                                tvTitle.getText().toString()
                                , tvDescription.getText().toString()
                                , location.latitude
                                , location.longitude
                                , selectedDate
                                , tvContacts.getText().toString()));
                finish();
            } else
                Toast.makeText(this, getString(R.string.no_enough_data), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalData.saveContacts(this, tvContacts.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String contacts = LocalData.getContacts(this);
        if (contacts != null && !contacts.isEmpty())
            tvContacts.setText(contacts);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_LOCATION) {
            if (data.getExtras() != null && data.getExtras().containsKey(ActivitySelectLocation.INTENT_COORDS))
                this.location = data.getParcelableExtra(ActivitySelectLocation.INTENT_COORDS);
            if (data.getExtras() != null && data.getExtras().containsKey(ActivitySelectLocation.INTENT_TITLE))
                tvLocation.setText(data.getStringExtra(ActivitySelectLocation.INTENT_TITLE));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSelectLocation:
                startActivityForResult(new Intent(this, ActivitySelectLocation.class), REQUEST_LOCATION);
                break;
            case R.id.btSelectDate:
                Calendar cal = Calendar.getInstance();
                new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        selectedDate = Calendar.getInstance();
        selectedDate.set(year, monthOfYear, dayOfMonth);
        tvDate.setText(Task.getDisplayedDate(selectedDate));
    }
}
