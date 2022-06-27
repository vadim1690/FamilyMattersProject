package com.example.familymattersproject.models.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AlertUtils {

    public static void showSnackBar(View parent, String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_LONG).show();
    }
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

}
