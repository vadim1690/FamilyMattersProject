package com.example.familymattersproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.familymattersproject.R;
import com.example.familymattersproject.models.utils.AlertUtils;


public class Activity_CreateFamily extends AppCompatActivity {

    public static final String FAMILY_NAME_KEY = "FAMILY_NAME_KEY";
    private Button createFamily_BTN_create;
    private EditText createFamily_EDT_familyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        findViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        createFamily_BTN_create.setOnClickListener(view -> {
            if (TextUtils.isEmpty(createFamily_EDT_familyName.getText()))
                AlertUtils.showSnackBar(findViewById(android.R.id.content), getString(R.string.enter_valid_family_name));
            else
                createFamily();
        });
    }

    private void createFamily() {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(FAMILY_NAME_KEY, createFamily_EDT_familyName.getText().toString());
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private void findViews() {
        createFamily_BTN_create = findViewById(R.id.createFamily_BTN_create);
        createFamily_EDT_familyName = findViewById(R.id.createFamily_EDT_familyName);
    }
}