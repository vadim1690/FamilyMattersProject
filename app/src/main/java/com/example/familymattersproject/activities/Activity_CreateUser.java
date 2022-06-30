package com.example.familymattersproject.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;
import com.example.familymattersproject.models.utils.AlertUtils;

public class Activity_CreateUser extends AppCompatActivity {
    private Button createUser_BTN_createFamily;
    private Button createUser_BTN_joinFamily;
    private EditText createUser_EDT_name;
    private EditText createUser_EDT_information;
    private ActivityResultLauncher<Intent> createFamilyActivityResultLauncher;
    private ActivityResultLauncher<Intent> joinFamilyActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setActivityResultLaunchers();
        findViews();
        setOnClickListeners();

    }


    private void setActivityResultLaunchers() {
        createFamilyActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            String familyName = result.getData().getStringExtra(Activity_CreateFamily.FAMILY_NAME_KEY);
                            createFamily(familyName);
                        }

                    } else {
                        AlertUtils.showToast(getApplicationContext(), getString(R.string.family_name_not_saved));
                    }
                });

        joinFamilyActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            String familyUid = result.getData().getStringExtra(Activity_JoinFamily.FAMILY_UID_KEY);
                            joinFamily(familyUid);
                        }

                    } else {
                        AlertUtils.showToast(getApplicationContext(), getString(R.string.family_id_not_saved));
                    }
                });


    }


    private void findViews() {
        createUser_BTN_createFamily = findViewById(R.id.createUser_BTN_createFamily);
        createUser_BTN_joinFamily = findViewById(R.id.createUser_BTN_joinFamily);
        createUser_EDT_name = findViewById(R.id.createUser_EDT_name);
        createUser_EDT_information = findViewById(R.id.createUser_EDT_information);
    }

    private void setOnClickListeners() {
        createUser_BTN_createFamily.setOnClickListener(view -> {
            if (TextUtils.isEmpty(createUser_EDT_name.getText())) {
                AlertUtils.showSnackBar(findViewById(android.R.id.content), getString(R.string.invalidFirstNameAlert));
            } else {
                createFamilyActivityResultLauncher.launch(new Intent(getApplicationContext(), Activity_CreateFamily.class));
            }

        });
        createUser_BTN_joinFamily.setOnClickListener(view -> {
            if (TextUtils.isEmpty(createUser_EDT_name.getText())) {
                AlertUtils.showSnackBar(findViewById(android.R.id.content), getString(R.string.invalidFirstNameAlert));
            } else {
                joinFamilyActivityResultLauncher.launch(new Intent(getApplicationContext(), Activity_JoinFamily.class));
            }
        });


    }

    private void joinFamily(String familyUid) {
        DataManager.getInstance().createNewJoiningUser(createUser_EDT_information.getText().toString(),createUser_EDT_name.getText().toString(), familyUid);
        DataManager.getInstance().addUserToFamily(familyUid);
        finish();
    }

    private void createFamily(String familyName) {

        DataManager.getInstance().createNewFamily(familyName);
        DataManager.getInstance().createNewUser(createUser_EDT_information.getText().toString(),createUser_EDT_name.getText().toString());
        DataManager.getInstance().addUserToFamily();
        finish();
    }



}