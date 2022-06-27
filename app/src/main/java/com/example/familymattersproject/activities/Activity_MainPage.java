package com.example.familymattersproject.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;
import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;
import com.example.familymattersproject.models.adapters.UpdatesAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;


public class Activity_MainPage extends AppCompatActivity {

    private MaterialButton main_BTN_myProfile;
    private MaterialButton main_BTN_myFamily;
    private TextView main_LBL_familyName;
    private TextView main_LBL_name;
    private BottomNavigationView bottom_navigation;
    private RecyclerView updates_recyclerview;
    private UpdatesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        findViews();
        setOnClickListeners();
        setBottomNavigationView();
        setRecyclerView();
        setFamilyName();
    }

    private void setUpdatesList() {
        DataManager.getInstance().getUpdatesList(updates -> adapter.setUpdatesList(updates));
    }

    private void setRecyclerView() {
        adapter = new UpdatesAdapter();
        updates_recyclerview.setAdapter(adapter);
        updates_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setBottomNavigationView() {
        bottom_navigation.setSelectedItemId(R.id.menu_nav_home);
        bottom_navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_home:
                    return true;
                case R.id.menu_nav_events:
                    eventsClicked();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menu_nav_todolist:
                    todoListClicked();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void setOnClickListeners() {
        main_BTN_myProfile.setOnClickListener(view -> myProfileClicked());
        main_BTN_myFamily.setOnClickListener(view -> myFamilyClicked());

    }

    private void myFamilyClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_MyFamily.class);
        startActivity(intent);
        finish();
    }

    private void myProfileClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_MyProfile.class);
        startActivity(intent);
        finish();
    }

    private void eventsClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_Events.class);
        startActivity(intent);
        finish();
    }

    private void todoListClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_ToDoList.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {

        main_LBL_familyName = findViewById(R.id.main_LBL_familyName);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        main_LBL_name = findViewById(R.id.main_LBL_name);
        main_BTN_myProfile = findViewById(R.id.main_BTN_myProfile);
        main_BTN_myFamily = findViewById(R.id.main_BTN_myFamily);
        updates_recyclerview = findViewById(R.id.updates_recyclerview);
    }


    private void setFamilyName() {
        main_LBL_familyName.setText(String.format("Welcome to %s Family ! ", DataManager.getInstance().getFamilyName()));
        main_LBL_name.setText(String.format("Hey, %s", DataManager.getInstance().getUserName()));
        setUpdatesList();

    }

}