package com.example.android.matchmaker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import static java.lang.Boolean.FALSE;

/**
 * Activity forces users to sign in before using the app.
 */
public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private final int RC_SIGN_IN = 2;
    private Button signIn;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firstPlayerReference = database.getReference("playerOneName");
    private DatabaseReference playerSignedOutReference = database.getReference("onePlayerSignedOut");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        AuthUI.getInstance().signOut(AuthenticationActivity.this);

        signIn = (Button) findViewById(R.id.signIn);
        playerSignedOutReference.setValue(FALSE);

        //Start FirebaseAuth sign-ina activity once users click the signIn button.
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .build(),
                        RC_SIGN_IN);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        //Assigns authStateListener a new AuthStateListener object with correct information.
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    String displayName = user.getDisplayName();

                    for (UserInfo userInfo : user.getProviderData()) {
                        if (displayName == null && userInfo.getDisplayName() != null) {
                            displayName = userInfo.getDisplayName();
                        }
                    }

                    //Transitions user to the activity where they will be connected with an opponent.
                    firstPlayerReference.setValue(displayName);
                    Toast.makeText(AuthenticationActivity.this, "You are signed in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                    startActivity(intent);


                } else {
                    // User is signed out
                    Toast.makeText(AuthenticationActivity.this, "You are signed out", Toast.LENGTH_LONG);
                }
                // ...
            }
        };
    }

    /**
     * Adds authStateListener once the user returns to the activity.
     */
    @Override
    public void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    /**
     * Removes the authStateListener of there is no action in the activity for an extended amount of time.
     */
    @Override
    public void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    /**
     * Signs the user out once the app is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        AuthUI.getInstance().signOut(AuthenticationActivity.this);
        firstPlayerReference.setValue("");
    }

}
