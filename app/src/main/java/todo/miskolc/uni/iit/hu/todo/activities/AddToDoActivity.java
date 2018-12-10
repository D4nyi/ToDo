package todo.miskolc.uni.iit.hu.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import todo.miskolc.uni.iit.hu.todo.R;
import todo.miskolc.uni.iit.hu.todo.models.ToDoItem;

public class AddToDoActivity extends BaseActivity {

    public static final String ITEM = "Item";
    private static final String[] paths = {"Busy", "Free", "Tentative"};
    private int availability = CalendarContract.Events.AVAILABILITY_FREE;
    private boolean isNew = true, saveToCalendar = false;
    private int itemIndex;

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

                try {
                    availability = position;
                } catch (IllegalArgumentException e) {
                    availability = CalendarContract.Events.AVAILABILITY_BUSY;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                availability = CalendarContract.Events.AVAILABILITY_FREE;
            }
        });

        if (getIntent().hasExtra(ITEM)) {
            itemIndex = getIntent().getExtras().getInt(ITEM, -1);
            if (itemIndex != -1) {
                isNew = false;
                setActivityValues(itemIndex);
            }
        }
    }

    private void setActivityValues(int itemIndex) {
        ToDoItem item = todos.getTodos().get(itemIndex);

        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        EditText place = findViewById(R.id.place);

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        place.setText(item.getPlace());

        if (item.getStartDate() != null) {
            CheckBox box = findViewById(R.id.save_to_calendar);
            box.setChecked(true);

            RelativeLayout layout = findViewById(R.id.options);
            layout.setVisibility(View.VISIBLE);

            EditText startDate = findViewById(R.id.start_date);
            EditText endDate = findViewById(R.id.end_date);

            startDate.setText(DateFormat.format("yyyy/MM/dd", item.getStartDate()));
            endDate.setText(DateFormat.format("yyyy/MM/dd", item.getEndDate()));
        }
    }

    public void onSaveClick(View view) {
        EditText start = findViewById(R.id.start_date);
        EditText end = findViewById(R.id.end_date);

        Calendar parsedStart = null;
        Calendar parsedEnd = null;

        if (!TextUtils.isEmpty(start.getText().toString())) {
            parsedStart = Calendar.getInstance();
            parsedEnd = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", getResources().getConfiguration().locale);
            try {
                Date temp = format.parse(start.getText().toString());
                Date tempp = format.parse(end.getText().toString());
                if (temp != null && tempp != null) {
                    parsedStart.setTime(temp);
                    parsedEnd.setTime(tempp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.description);
        EditText location = findViewById(R.id.place);

        ToDoItem item;
        if (parsedStart != null) {
            item = new ToDoItem(title.getText().toString(), description.getText().toString(), location.getText().toString(), parsedStart.getTime(), parsedEnd.getTime(), availability);
        } else {
            item = new ToDoItem(title.getText().toString(), description.getText().toString(), location.getText().toString(), availability);
        }

        if (!isNew) {
            todos.getTodos().remove(itemIndex);
            todos.getTodos().add(itemIndex, item);
        } else {
            todos.getTodos().add(item);
        }

        if (saveToCalendar) {
            addEvent(parsedStart, parsedEnd, item.getTitle(), item.getDescription(), item.getPlace());
        }

        finish();
    }

    private void addEvent(@Nullable Calendar beginTime, @Nullable Calendar endTime, @NonNull String title, @NonNull String description, @NonNull String location) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.AVAILABILITY, availability)
                .putExtra(CalendarContract.Events.HAS_ALARM, true);

        if (beginTime != null && endTime != null) {
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.CALENDAR_TIME_ZONE, Calendar.getInstance().getTimeZone());
        }

        startActivity(intent);
    }

    public void onChecked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            RelativeLayout layout = findViewById(R.id.options);
            layout.setVisibility(View.VISIBLE);
            saveToCalendar = true;
        }
    }
}
