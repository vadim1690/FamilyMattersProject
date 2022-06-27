package com.example.familymattersproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;
import com.example.familymattersproject.models.adapters.UsersAdapter;


public class Activity_MyFamily extends AppCompatActivity {

    private TextView myFamily_LBL_familyName;
    private RecyclerView users_recyclerview;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);
        findViews();
        setRecyclerView();
        setFamilyDetails();
    }

    private void setRecyclerView() {
        adapter = new UsersAdapter();
        users_recyclerview.setAdapter(adapter);
        users_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }


    private void setFamilyDetails() {
        myFamily_LBL_familyName.setText(String.format("The %s Family", DataManager.getInstance().getFamilyName()));
        DataManager.getInstance().getFamilyMembers(familyMember -> adapter.addUserEntity(familyMember));


    }

    private void findViews() {
        myFamily_LBL_familyName = findViewById(R.id.myFamily_LBL_familyName);
        users_recyclerview = findViewById(R.id.users_recyclerview);
    }

    @Override
    public void onBackPressed() {
        DataManager.getInstance().removeAllEventListeners();
        Intent intent = new Intent(getApplicationContext(),Activity_MainPage.class);
        startActivity(intent);
        finish();
    }
}