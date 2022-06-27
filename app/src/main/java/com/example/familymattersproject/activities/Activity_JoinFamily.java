package com.example.familymattersproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.familymattersproject.R;
import com.example.familymattersproject.models.utils.AlertUtils;

public class Activity_JoinFamily extends AppCompatActivity {

    public static final String FAMILY_UID_KEY = "FAMILY_UID_KEY";
    private Button joinFamily_BTN_join;
    private EditText joinFamily_EDT_FamilyUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_family);
        findViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        joinFamily_BTN_join.setOnClickListener(view -> {
            if (TextUtils.isEmpty(joinFamily_EDT_FamilyUid.getText()))
                AlertUtils.showSnackBar(findViewById(android.R.id.content), getString(R.string.enter_valid_family_id));
            else
                joinFamily();
        });
    }

    private void joinFamily() {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(FAMILY_UID_KEY, joinFamily_EDT_FamilyUid.getText().toString());
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private void findViews() {
        joinFamily_BTN_join = findViewById(R.id.joinFamily_BTN_join);
        joinFamily_EDT_FamilyUid = findViewById(R.id.joinFamily_EDT_FamilyUid);
    }
}