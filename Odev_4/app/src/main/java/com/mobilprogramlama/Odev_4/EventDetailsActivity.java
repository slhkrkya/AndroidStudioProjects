package com.mobilprogramlama.Odev_4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.mobilprogramlama.odev_4.R;

public class EventDetailsActivity extends AppCompatActivity {
    private TextView eventName, eventDate, eventTime;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventName = findViewById(R.id.eventName);
        eventDate = findViewById(R.id.eventDate);
        eventTime = findViewById(R.id.eventTime);
        btnShare = findViewById(R.id.btnShare);

        Intent intent = getIntent();
        eventName.setText(intent.getStringExtra("eventName"));
        eventDate.setText(intent.getStringExtra("eventDate"));
        eventTime.setText(intent.getStringExtra("eventTime"));

        btnShare.setOnClickListener(view -> shareEvent());
    }

    private void shareEvent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Etkinlik: " + eventName.getText().toString() + "\nTarih: " + eventDate.getText().toString() + "\nSaat: " + eventTime.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Etkinliği Paylaş"));
    }
}
