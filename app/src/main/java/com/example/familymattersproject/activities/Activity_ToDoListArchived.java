package com.example.familymattersproject.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;

import com.example.familymattersproject.entities.TodoTaskEntity;

import com.example.familymattersproject.models.adapters.TodoListAdapter;


public class Activity_ToDoListArchived extends AppCompatActivity {
    private Button toDoListArchived_BTN_back;
    private RecyclerView toDoListArchived_recyclerview;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_archived);
        findViews();
        setRecyclerView();
        setOnClickListeners();
        setTodoList();
    }

    private void setTodoList() {
        DataManager.getInstance().getFamilyTodoList(todoList -> adapter.setTodoTaskList(todoList), true);

    }

    private void setOnClickListeners() {
        toDoListArchived_BTN_back.setOnClickListener(view -> backClicked());
    }

    private void backClicked() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(), Activity_ToDoList.class);
        startActivity(intent);
        finish();
    }

    private void setRecyclerView() {
        adapter = new TodoListAdapter();
        adapter.setToDoTaskListener(this::todoTaskChecked);
        toDoListArchived_recyclerview.setAdapter(adapter);

        toDoListArchived_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void todoTaskChecked(TodoTaskEntity todoTaskEntity) {
        if (!todoTaskEntity.getIsDone()) {
            DataManager.getInstance().todoTaskUnchecked(todoTaskEntity);
        }


    }


    private void findViews() {
        toDoListArchived_BTN_back = findViewById(R.id.toDoListArchived_BTN_back);
        toDoListArchived_recyclerview = findViewById(R.id.toDoListArchived_recyclerview);
    }
}