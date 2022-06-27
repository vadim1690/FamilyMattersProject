package com.example.familymattersproject.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;
import com.example.familymattersproject.models.utils.DateUtils;
import com.example.familymattersproject.models.utils.GlideApp;
import com.firebase.ui.auth.AuthUI;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Activity_MyProfile extends AppCompatActivity {
    private CircularImageView myProfile_IMG_icon;
    private TextView myProfile_LBL_joinDate;
    private TextView myProfile_LBL_name;
    private TextView myProfile_LBL_information;
    private Button myProfile_BTN_signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        findViews();
        setOnClickListeners();
        setUserDetails();

    }

    private void setUserDetails() {

        GlideApp.with(this)
                .load(DataManager.getInstance().getUserAvatar())
                .placeholder(R.drawable.ic_place_holder)
                .fitCenter()
                .into(myProfile_IMG_icon);
        myProfile_LBL_joinDate.setText(String.format("Joined in : %s", DateUtils.formatDateToDayMonthYear(DataManager.getInstance().getUserJoinDate())));
        myProfile_LBL_name.setText(DataManager.getInstance().getUserName());
        myProfile_LBL_information.setText(DataManager.getInstance().getUserInformation());


    }




    private void setOnClickListeners() {
        myProfile_BTN_signOut.setOnClickListener(view -> signOutClicked());
    }

    private void signOutClicked() {

        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnCompleteListener(task -> {
                    // user is now signed out
                    DataManager.getInstance().userSignedOut();
                    startActivity(new Intent(getApplicationContext(), Activity_Login.class));
                    finish();
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Activity_MainPage.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        myProfile_IMG_icon = findViewById(R.id.myProfile_IMG_icon);
        myProfile_LBL_joinDate = findViewById(R.id.myProfile_LBL_joinDate);
        myProfile_BTN_signOut = findViewById(R.id.myProfile_BTN_signOut);
        myProfile_LBL_name = findViewById(R.id.myProfile_LBL_name);
        myProfile_LBL_information = findViewById(R.id.myProfile_LBL_information);

    }
}