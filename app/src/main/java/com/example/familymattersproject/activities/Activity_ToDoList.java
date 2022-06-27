package com.example.familymattersproject.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;
import com.example.familymattersproject.models.adapters.TodoListAdapter;
import com.example.familymattersproject.entities.TodoTaskEntity;
import com.example.familymattersproject.models.utils.AlertUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class Activity_ToDoList extends AppCompatActivity {

    public static final String EXTRAS_TO_DO_TASK_KEY = "EXTRAS_TO_DO_TASK_KEY";

    private RecyclerView toDoList_recyclerview;
    private ImageView toDoList_IMG_addTask;
    private Button toDoList_BTN_archive;
    private TodoListAdapter adapter;
    private ActivityResultLauncher<Intent> addTodoTaskActivityResultLauncher;
    private BottomNavigationView bottom_navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        findViews();
        setActivityResultLaunchers();
        setRecyclerView();
        setOnClickListeners();
        setBottomNavigationView();
        setTodoList();
    }

    private void setBottomNavigationView() {
        bottom_navigation.setSelectedItemId(R.id.menu_nav_todolist);
        bottom_navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_home:
                    homeClicked();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menu_nav_events:
                    eventsClicked();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menu_nav_todolist:
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

    private void eventsClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_Events.class);
        startActivity(intent);
        finish();
    }

    private void setTodoList() {
        DataManager.getInstance().getFamilyTodoList(todoList -> adapter.setTodoTaskList(todoList), false);

    }


    private void setOnClickListeners() {
        toDoList_IMG_addTask.setOnClickListener(view -> addTodoTaskClicked());
        toDoList_BTN_archive.setOnClickListener(view -> archiveClicked());
    }

    private void archiveClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_ToDoListArchived.class);
        startActivity(intent);
        finish();
    }

    private void addTodoTaskClicked() {

        Intent intent = new Intent(Activity_ToDoList.this, Activity_AddTodoTask.class);
        addTodoTaskActivityResultLauncher.launch(intent);

    }

    private void setRecyclerView() {
        adapter = new TodoListAdapter();
        adapter.setToDoTaskListener(this::todoTaskChecked);
        toDoList_recyclerview.setAdapter(adapter);

        toDoList_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void todoTaskChecked(TodoTaskEntity todoTaskEntity) {
        if (todoTaskEntity.getIsDone()) {
            DataManager.getInstance().todoTaskChecked(todoTaskEntity);
        }
    }

    private void setActivityResultLaunchers() {
        addTodoTaskActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            TodoTaskEntity todoTaskEntity = new Gson().fromJson(result.getData().getStringExtra(EXTRAS_TO_DO_TASK_KEY), TodoTaskEntity.class);
                            addTodoTaskToList(todoTaskEntity);
                        }

                    } else {
                        AlertUtils.showToast(getApplicationContext(), getString(R.string.todoTaskNotSaved));
                    }
                });
    }

    private void addTodoTaskToList(TodoTaskEntity todoTaskEntity) {
        DataManager.getInstance().addFamilyTodoTask(todoTaskEntity);
    }

    private void findViews() {
        toDoList_recyclerview = findViewById(R.id.toDoList_recyclerview);
        toDoList_IMG_addTask = findViewById(R.id.toDoList_IMG_addTask);
        toDoList_BTN_archive = findViewById(R.id.toDoList_BTN_archive);
        bottom_navigation = findViewById(R.id.bottom_navigation);
    }

}