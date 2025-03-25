package com.mobilprogramlama.Odev_4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobilprogramlama.Odev_4.database.AppDatabase;
import com.mobilprogramlama.Odev_4.database.Event;
import com.mobilprogramlama.odev_4.R;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText eventNameInput;
    private TextView selectedDate, selectedTime, eventList;
    private Button btnPickDate, btnPickTime, btnSave, btnShowEvents;
    private ProgressBar progressBar;
    private String eventName;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobilprogramlama.odev_4.R.layout.activity_main);

        eventNameInput = findViewById(R.id.eventNameInput);
        selectedDate = findViewById(R.id.selectedDate);
        selectedTime = findViewById(R.id.selectedTime);
        eventList = findViewById(R.id.eventList);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        btnSave = findViewById(R.id.btnSave);
        btnShowEvents = findViewById(R.id.btnShowEvents);

        btnPickDate.setOnClickListener(view -> showDatePickerDialog());
        btnPickTime.setOnClickListener(view -> showTimePickerDialog());

        // Event Kaydetme
        btnSave.setOnClickListener(view -> {
            String name = eventNameInput.getText().toString();
            String date = selectedDate.getText().toString();
            String time = selectedTime.getText().toString();

            if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm bilgileri girin", Toast.LENGTH_SHORT).show();
                return;
            }

            Event event = new Event(name, date, time, "Genel"); // Varsayılan kategori
            AppDatabase.getInstance(this).eventDao().insert(event);
            Toast.makeText(this, "Etkinlik kaydedildi!", Toast.LENGTH_SHORT).show();
        });

        // Etkinlikleri Göster
        btnShowEvents.setOnClickListener(view -> {
            List<Event> events = AppDatabase.getInstance(this).eventDao().getAllEvents();
            StringBuilder eventText = new StringBuilder();
            for (Event e : events) {
                eventText.append(e.name).append(" - ").append(e.date).append(" - ").append(e.time).append("\n");
            }
            eventList.setText(eventText.toString());
        });
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, year, month, day);
        datePickerDialog.show();
    }
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedTime.setText(hourOfDay + ":" + minute);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void saveEvent() {
        eventName = eventNameInput.getText().toString();
        if (eventName.isEmpty() || selectedDate.getText().toString().isEmpty() || selectedTime.getText().toString().isEmpty()) {
            Toast.makeText(this, "Lütfen tüm bilgileri girin", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
            intent.putExtra("eventName", eventName);
            intent.putExtra("eventDate", selectedDate.getText().toString());
            intent.putExtra("eventTime", selectedTime.getText().toString());
            startActivity(intent);
        }, 2000);
    }
}
