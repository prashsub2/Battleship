package com.example.android.matchmaker;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Activity helps match two players to a game of Battleship. Base code provided by Professor Zilles.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = AppCompatActivity.class.getSimpleName();

    private final String NONE = "none";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference matchmakerReference = database.getReference("matchmaker");
    private DatabaseReference gamesReference = database.getReference("games");
    private DatabaseReference playerOneNameReference = database.getReference("playerOneName");
    private DatabaseReference secondPlayerArrivedReference = database.getReference("secondPlayerArrived");
    private DatabaseReference startGameReference = database.getReference("startGame");
    private TextView playerOneName;
    private String firstPlayer = "";
    private static int signOutCnt = 0;
    private String playerNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (signOutCnt == 0) {
            AuthUI.getInstance().signOut(MainActivity.this);
            signOutCnt++;
        }

        playerOneName = (TextView) findViewById(R.id.name);

        //Assigns the name of the first player to click findGame to the firstPlayer String.
        playerOneNameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstPlayer = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Assigns the value of secondPlayerArrived in the Firebase database to the isSecondPlayer boolean.
        secondPlayerArrivedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isSecondPlayer = dataSnapshot.getValue(Boolean.class);
                if (isSecondPlayer) {
                    playerOneName.setText(firstPlayer + " is ready to play!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Starts new activity for each player once the second player has clicked find game.
        startGameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean startGame = dataSnapshot.getValue(Boolean.class);
                if (startGame) {
                    if (playerNumber.equals("player1")) {
                        Intent intent = new Intent(MainActivity.this, PlayerOnePlaceShips.class);
                        startActivity(intent);
                    }
                    if (playerNumber.equals("player2")) {
                        Intent intent = new Intent(MainActivity.this, PlayerTwoPlaceShips.class);
                        startActivity(intent);
                    }
                }
                startGameReference.setValue(FALSE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * This function is the callback of the "Find Match" button.   This function reads the current
     * value of the matchmaker storage location to determine if it thinks that we're the first arriver
     * or the second.
     *
     * @param view - The view in which the findGame button is.
     */
    public void findMatch(View view) {
        matchmakerReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String matchmaker = dataSnapshot.getValue(String.class);
                Log.d(TAG, "mMatchmaker: " + matchmaker);

                if (matchmaker.equals(NONE)) {
                    playerNumber = "player1";
                    findMatchFirstArriver();
                } else {
                    playerNumber = "player2";
                    findMatchSecondArriver(matchmaker);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * The first arriver needs to create the game, add themselves to it, and then verify that no one
     * else has posted a game yet and post the game.
     */
    private void findMatchFirstArriver() {

        final String matchmaker;
        final DatabaseReference dbReference = gamesReference.push();
        dbReference.push().setValue("player0");
        matchmaker = dbReference.getKey();
        final String newMatchmaker = matchmaker;
        playerOneName.setText(firstPlayer + " is ready to play!");
        secondPlayerArrivedReference.setValue(TRUE);
        matchmakerReference.setValue(newMatchmaker);
    }

    /**
     * The second player needs to  verify that the game is still available to join and then remove the game from
     * the matchmaker.  It then adds itself to the game, so that player0 gets a notification that the game was joined.
     *
     * @param matchmaker - Key of the current game.
     */
    private void findMatchSecondArriver(final String matchmaker) {

        if (!matchmaker.equals(NONE)) {
            DatabaseReference gameReference = gamesReference.child(matchmaker);
            gameReference.push().setValue("player1");
            matchmakerReference.setValue(NONE);
            secondPlayerArrivedReference.setValue(FALSE);
            startGameReference.setValue(TRUE);
            playerOneName.setText("");
            playerOneNameReference.setValue("");
        }
    }

    /**
     * Signs the user out when they press the back button
     */
    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        AuthUI.getInstance().signOut(MainActivity.this);
        playerOneNameReference.setValue("");

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
        AuthUI.getInstance().signOut(MainActivity.this);
        playerOneNameReference.setValue("");
        Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
        startActivity(intent);
        return true;
    }
}
