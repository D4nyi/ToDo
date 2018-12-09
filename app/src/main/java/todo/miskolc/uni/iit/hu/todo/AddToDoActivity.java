package todo.miskolc.uni.iit.hu.todo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

public class AddToDoActivity extends AppCompatActivity {

    private static final String[] paths = {"Busy", "Free", "Tentative"};
    private int availability = 1; //0 -> Busy, 1 -> Free, 2 -> Tentative

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                availability = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                availability = 1;
            }
        });
    }

    public void onSaveClick(View view) {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        calendar.setTime(currentTime);
        calendar.add(Calendar.HOUR, 1);

        Calendar calendar2 = Calendar.getInstance();
        Date next = calendar2.getTime();
        calendar2.setTime(next);
        calendar2.add(Calendar.HOUR, 2);

        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        EditText location = findViewById(R.id.place);

        addEvent(calendar, calendar2, title.getText().toString(), description.getText().toString(), location.getText().toString());
    }

    private void addEvent(@NonNull Calendar beginTime, @NonNull Calendar endTime, @NonNull String title, @NonNull String description, @NonNull String location) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.CALENDAR_TIME_ZONE, Calendar.getInstance().getTimeZone())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.AVAILABILITY, availability)
                .putExtra(CalendarContract.Events.HAS_ALARM, true);

        startActivity(intent);
    }

    public void onChecked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            RelativeLayout layout = findViewById(R.id.options);
            layout.setVisibility(View.VISIBLE);
        }
    }
}
