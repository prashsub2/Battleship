package com.example.android.matchmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

/**
 * Class that users are brought to if they win the game.
 */
public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
    }

    /**
     * The method makes the option to sign out in the upper right hand corner visible in this activity.
     *
     * @param menu - Menu object needed to inflate the menu.
     * @return - boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_out, menu);
        return true;
    }

    /**
     * Method handles when the user clicks on sign out after clicking the drop down menu.
     *
     * @param item - Item within the menu created.
     * @return - boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AuthUI.getInstance().signOut(WinnerActivity.this);
        Intent intent = new Intent(WinnerActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        return true;
    }

    /**
     * Forces user to use sign out to leave after they have matched up with somebody.
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You must sign out if you want to leave this activity",
                Toast.LENGTH_LONG).show();

    }
}
