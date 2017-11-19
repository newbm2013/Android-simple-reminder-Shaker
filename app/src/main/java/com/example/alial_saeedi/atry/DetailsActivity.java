package com.example.alial_saeedi.atry;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by alial-saeedi on 11/19/17.
 */

public class DetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String eventDetails;
    private EditText detailText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        Intent detailIntent = getIntent();
        eventDetails = detailIntent.getStringExtra("eventDetails");
        detailText = (EditText) findViewById(R.id.detailText);
        detailText.setText(eventDetails);
        detailText.setEnabled(false);
    }
}
