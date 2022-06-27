package com.example.familymattersproject.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;

import com.example.familymattersproject.models.adapters.EventsAdapter;

import com.example.familymattersproject.entities.FamilyEventEntity;

import com.example.familymattersproject.models.utils.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class Activity_Events extends AppCompatActivity {


    public static final String EXTRAS_FAMILY_EVENT_KEY = "EXTRAS_FAMILY_EVENT_KEY";
    private FloatingActionButton add_event_fab;
    private EventsAdapter adapter;
    private RecyclerView events_recyclerview;
    private TextView events_LBL_instructions;
    private ActivityResultLauncher<Intent> addFamilyEventActivityResultLauncher;
    private BottomNavigationView bottom_navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        findViews();
        setActivityResultLaunchers();
        setRecyclerView();
        setOnClickListeners();
        setBottomNavigationView();

        setEvents();


    }

    private void setBottomNavigationView() {
        bottom_navigation.setSelectedItemId(R.id.menu_nav_events);
        bottom_navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_home:
                    homeClicked();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menu_nav_events:
                    return true;
                case R.id.menu_nav_todolist:
                    todoListClicked();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void homeClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_MainPage.class);
        startActivity(intent);
        finish();
    }

    private void todoListClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_ToDoList.class);
        startActivity(intent);
        finish();
    }


    private void setEvents() {
        DataManager.getInstance().getFamilyEvents(events -> {
            if (events.isEmpty())
                events_LBL_instructions.setVisibility(View.VISIBLE);
            else
                events_LBL_instructions.setVisibility(View.INVISIBLE);

            adapter.setFamilyEventList(events);
        });
    }

    private void setRecyclerView() {
        adapter = new EventsAdapter();
        adapter.setFamilyEventListener(this::deleteFamilyEvent);
        events_recyclerview.setAdapter(adapter);

        events_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setActivityResultLaunchers() {
        addFamilyEventActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            FamilyEventEntity familyEventEntity = new Gson().fromJson(result.getData().getStringExtra(EXTRAS_FAMILY_EVENT_KEY), FamilyEventEntity.class);
                            addEventToFirebase(familyEventEntity);
                        }

                    } else {
                        AlertUtils.showToast(getApplicationContext(), getString(R.string.event_not_saved));
                    }
                });
    }

    private void addEventToFirebase(FamilyEventEntity familyEventEntity) {
        DataManager.getInstance().addFamilyEvent(familyEventEntity);

    }

    private void deleteFamilyEvent(FamilyEventEntity familyEventEntity) {
        DataManager.getInstance().deleteFamilyEvent(familyEventEntity);
    }


    private void addEventClicked() {
        Intent intent = new Intent(this, Activity_AddFamilyEvent.class);
        addFamilyEventActivityResultLauncher.launch(intent);
    }


    private void setOnClickListeners() {
        add_event_fab.setOnClickListener(view -> addEventClicked());
    }


    private void findViews() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        add_event_fab = findViewById(R.id.add_event_fab);
        events_recyclerview = findViewById(R.id.events_recyclerview);
        events_LBL_instructions = findViewById(R.id.events_LBL_instructions);

    }


}