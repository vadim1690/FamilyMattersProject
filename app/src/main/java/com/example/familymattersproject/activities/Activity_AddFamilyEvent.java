package com.example.familymattersproject.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.CalendarView;

import android.widget.EditText;

import com.example.familymattersproject.R;
import com.example.familymattersproject.entities.FamilyEventEntity;
import com.example.familymattersproject.models.utils.AlertUtils;
import com.example.familymattersproject.models.utils.DateUtils;

import com.google.gson.Gson;

import java.util.Calendar;

public class Activity_AddFamilyEvent extends AppCompatActivity {


    private Button addEvent_BTN_save;
    private EditText addEvent_EDT_description;
    private CalendarView datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_event);
        findViews();
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        addEvent_BTN_save.setOnClickListener(view -> saveClicked());
        datePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            datePicker.setDate(calendar.getTimeInMillis());
        });
    }

    private void saveClicked() {

        if (TextUtils.isEmpty(addEvent_EDT_description.getText()))
            AlertUtils.showSnackBar(findViewById(android.R.id.content), getString(R.string.fillAllFieldsAlert));
        else {
            Intent replyIntent = new Intent();

            FamilyEventEntity familyEventEntity = new FamilyEventEntity(
                    DateUtils.getDateFromLong(datePicker.getDate()),
                    addEvent_EDT_description.getText().toString());
            replyIntent.putExtra(Activity_Events.EXTRAS_FAMILY_EVENT_KEY, new Gson().toJson(familyEventEntity));
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    private void findViews() {
        addEvent_BTN_save = findViewById(R.id.addEvent_BTN_save);
        addEvent_EDT_description = findViewById(R.id.addEvent_EDT_description);
        datePicker = findViewById(R.id.datePicker);
    }


}