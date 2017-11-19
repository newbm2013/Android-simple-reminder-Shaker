package com.example.alial_saeedi.atry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;


/**
 * Created by alialsaeedi on 11/18/16.
 */

public class Addevent extends AppCompatActivity implements View.OnClickListener {
    private TextView eventTitle;
    private TextView eventDetail;
    private Button pickTime;


    private static final String TAG = "EmailPassword";
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);
        eventTitle = (TextView) findViewById(R.id.EventTitle);
        eventDetail = (TextView) findViewById(R.id.EventDetails);
        pickTime = (Button) findViewById(R.id.PickTime);
        pickTime.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

    }

    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmTodo();
    }

    @Override
    public void onClick(View view) {
        if (view == pickTime) {
            if (validate()) {
                Intent pickTime = new Intent(Addevent.this, PickDate.class);
                pickTime.putExtra("eventTitle", eventTitle.getText().toString());
                pickTime.putExtra("eventDetail", eventDetail.getText().toString());
                startActivity(pickTime);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean validate()

    {
        boolean value = true;

        if (TextUtils.isEmpty(eventDetail.getText().toString())) {
            value = false;
            eventDetail.setError("Required!");
        }
        if (TextUtils.isEmpty(eventTitle.getText().toString())) {
            value = false;
            eventTitle.setError("Required!");
        }
        return value;
    }
}
