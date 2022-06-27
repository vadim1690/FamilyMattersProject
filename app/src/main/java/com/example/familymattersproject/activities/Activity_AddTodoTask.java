package com.example.familymattersproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import com.example.familymattersproject.R;
import com.example.familymattersproject.entities.TodoTaskEntity;
import com.example.familymattersproject.models.utils.AlertUtils;
import com.google.gson.Gson;


public class Activity_AddTodoTask extends AppCompatActivity {

    private Button addTodoTask_BTN_save;
    private EditText addTodoTask_EDT_description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_task);

        findViews();
        setOnClickListeners();

    }



    private void setOnClickListeners() {
        addTodoTask_BTN_save.setOnClickListener(view -> saveClicked());
    }

    private void saveClicked() {
        if(TextUtils.isEmpty(addTodoTask_EDT_description.getText()) )
            AlertUtils.showSnackBar(findViewById(android.R.id.content),getString(R.string.fillAllFieldsAlert));
        else{
            Intent replyIntent = new Intent();
            TodoTaskEntity todoTaskEntity = new TodoTaskEntity(addTodoTask_EDT_description.getText().toString());
            replyIntent.putExtra(Activity_ToDoList.EXTRAS_TO_DO_TASK_KEY, new Gson().toJson(todoTaskEntity));
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    private void findViews() {
        addTodoTask_BTN_save = findViewById(R.id.addTodoTask_BTN_save);
        addTodoTask_EDT_description = findViewById(R.id.addTodoTask_EDT_description);

    }


}