package com.example.familymattersproject.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.familymattersproject.R;
import com.example.familymattersproject.data.DataManager;

import com.example.familymattersproject.models.utils.AlertUtils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Activity_Login extends AppCompatActivity {

    private ActivityResultLauncher<Intent> signInLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActivityResultLaunchers();
        checkIfUserSignedIn();

    }


    private void checkIfUserSignedIn() {
        if (DataManager.getInstance().isUserSignedIn()) {
            handleSignedInUser();
        } else {
            launchSignInActivity();
        }

    }

    private void handleSignedInUser() {
        DataManager.getInstance().handleSignedInUser(isExist -> {
            if (!isExist){
                startCreateUserActivity();
            }else{
                startMainActivity();
            }
        });
    }


    private void setActivityResultLaunchers() {
        signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(), this::onSignInResult);
    }

    private List<AuthUI.IdpConfig> getSelectedProviders() {

        return Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
                );
    }

    private void startMainActivity() {
        DataManager.getInstance().removeAllEventListeners();
        DataManager.getInstance().readUserEntity();
        DataManager.getInstance().readFamilyEntity();
        Intent intent = new Intent(getApplicationContext(), Activity_MainPage.class);
        startActivity(intent);
        finish();

    }

    private void startCreateUserActivity() {
        Intent intent = new Intent(getApplicationContext(), Activity_CreateUser.class);
        startActivity(intent);
    }

    private void launchSignInActivity() {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(getSelectedProviders())
                .setLogo(R.drawable.ic_family)
                .setTheme(R.style.Theme_FamilyMattersProject)
                .build();

        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();

        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            DataManager.getInstance().setCurrentFirebaseUser();
            handleSignedInUser();
        } else {
            // Sign in failed
            if (response == null) {
                // UserEntity pressed back button
                AlertUtils.showToast(getApplicationContext(),getString(R.string.sign_in_cancelled));
                return;
            }

            if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                AlertUtils.showToast(getApplicationContext(),getString(R.string.no_internet_connection));
                return;
            }

            AlertUtils.showToast(getApplicationContext(),getString(R.string.unknown_error));

        }
    }


}