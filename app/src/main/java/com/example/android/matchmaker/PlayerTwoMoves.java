package com.example.android.matchmaker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class PlayerTwoMoves extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //The reason I changed the suffix from Reference to Ref is because the names were long enough already.
    private DatabaseReference playerTwoBoardRowOneRef = database.getReference("playerOneBoard/0");
    private DatabaseReference playerTwoBoardRowTwoRef = database.getReference("playerOneBoard/1");
    private DatabaseReference playerTwoBoardRowThreeRef = database.getReference("playerOneBoard/2");
    private DatabaseReference playerTwoBoardRowFourRef = database.getReference("playerOneBoard/3");
    private DatabaseReference playerTwoBoardRowFiveRef = database.getReference("playerOneBoard/4");

    private DatabaseReference turnTrackerReference = database.getReference("turnTracker");
    private DatabaseReference gameFinishedReference = database.getReference("gameFinished");
    private DatabaseReference playerSignedOutReference = database.getReference("onePlayerSignedOut");
    private DatabaseReference playerOneFiveAwayReference = database.getReference("playerOneFiveAway");
    private DatabaseReference playerOneOneAwayReference = database.getReference("playerOneOneAway");
    private DatabaseReference playerTwoFiveAwayReference = database.getReference("playerTwoFiveAway");
    private DatabaseReference playerTwoOneAwayReference = database.getReference("playerTwoOneAway");

    private boolean gameFinished = false;
    private boolean turnTracker = false;
    private int hitCnt = 0;
    private Boolean booleanBoard[][] = new Boolean[5][5];
    private List<Boolean> booleanRowOne = new ArrayList<>();
    private List<Boolean> booleanRowTwo = new ArrayList<>();
    private List<Boolean> booleanRowThree = new ArrayList<>();
    private List<Boolean> booleanRowFour = new ArrayList<>();
    private List<Boolean> booleanRowFive = new ArrayList<>();

    private TextView boxZeroZero, boxZeroOne, boxZeroTwo, boxZeroThree, boxZeroFour, boxOneZero, boxOneOne, boxOneTwo,
            boxOneThree, boxOneFour, boxTwoZero, boxTwoOne, boxTwoTwo, boxTwoThree, boxTwoFour, boxThreeZero, boxThreeOne,
            boxThreeTwo, boxThreeThree, boxThreeFour, boxFourZero, boxFourOne, boxFourTwo, boxFourThree, boxFourFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_one_moves);
        boxZeroZero = (TextView) findViewById(R.id.box1);
        boxZeroOne = (TextView) findViewById(R.id.box2);
        boxZeroTwo = (TextView) findViewById(R.id.box3);
        boxZeroThree = (TextView) findViewById(R.id.box4);
        boxZeroFour = (TextView) findViewById(R.id.box5);
        boxOneZero = (TextView) findViewById(R.id.box6);
        boxOneOne = (TextView) findViewById(R.id.box7);
        boxOneTwo = (TextView) findViewById(R.id.box8);
        boxOneThree = (TextView) findViewById(R.id.box9);
        boxOneFour = (TextView) findViewById(R.id.box10);
        boxTwoZero = (TextView) findViewById(R.id.box11);
        boxTwoOne = (TextView) findViewById(R.id.box12);
        boxTwoTwo = (TextView) findViewById(R.id.box13);
        boxTwoThree = (TextView) findViewById(R.id.box14);
        boxTwoFour = (TextView) findViewById(R.id.box15);
        boxThreeZero = (TextView) findViewById(R.id.box16);
        boxThreeOne = (TextView) findViewById(R.id.box17);
        boxThreeTwo = (TextView) findViewById(R.id.box18);
        boxThreeThree = (TextView) findViewById(R.id.box19);
        boxThreeFour = (TextView) findViewById(R.id.box20);
        boxFourZero = (TextView) findViewById(R.id.box21);
        boxFourOne = (TextView) findViewById(R.id.box22);
        boxFourTwo = (TextView) findViewById(R.id.box23);
        boxFourThree = (TextView) findViewById(R.id.box24);
        boxFourFour = (TextView) findViewById(R.id.box25);

        //The next five Listeners are added to their respective elements in Firebase. Each one contains a row of what
        //will be added to booleanBoard.
        playerTwoBoardRowOneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Boolean>> genericTypeIndicator = new GenericTypeIndicator<List<Boolean>>() {
                };
                booleanRowOne = dataSnapshot.getValue(genericTypeIndicator);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        playerTwoBoardRowTwoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Boolean>> genericTypeIndicator = new GenericTypeIndicator<List<Boolean>>() {
                };
                booleanRowTwo = dataSnapshot.getValue(genericTypeIndicator);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        playerTwoBoardRowThreeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Boolean>> genericTypeIndicator = new GenericTypeIndicator<List<Boolean>>() {
                };
                booleanRowThree = dataSnapshot.getValue(genericTypeIndicator);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        playerTwoBoardRowFourRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Boolean>> genericTypeIndicator = new GenericTypeIndicator<List<Boolean>>() {
                };
                booleanRowFour = dataSnapshot.getValue(genericTypeIndicator);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Also calls createBooleanBoard which fills booleanBoard with the correct elements.
        playerTwoBoardRowFiveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Boolean>> genericTypeIndicator = new GenericTypeIndicator<List<Boolean>>() {
                };
                booleanRowFive = dataSnapshot.getValue(genericTypeIndicator);
                createBooleanBoard();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Tracks the value of the boolean turnTracker in Firebase. Then it assigns the value to turnTracker.
        //Controls the turn taking aspect of the game.
        turnTrackerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                turnTracker = dataSnapshot.getValue(Boolean.class);
                if (turnTracker) {
                    createNotificationPlayerTwo();
                    addTextViewListeners();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Once the game is finished, it sends the player to an Activity that contains the correct message.
        gameFinishedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameFinished = dataSnapshot.getValue(Boolean.class);
                if (gameFinished) {
                    Intent intent;
                    if (hitCnt == 9) {
                        intent = new Intent(PlayerTwoMoves.this, WinnerActivity.class);
                    } else {
                        intent = new Intent(PlayerTwoMoves.this, LoserActivity.class);
                    }

                    startActivity(intent);
                }
                gameFinishedReference.setValue(FALSE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //If one player signs out, this reference will detect it and send both players back to the
        // AuthenticationActivity.
        playerSignedOutReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean onePlayerSignedOut = dataSnapshot.getValue(Boolean.class);
                if (onePlayerSignedOut) {
                    Toast.makeText(getApplicationContext(), "One player signed out, so the game cannot continue",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PlayerTwoMoves.this, AuthenticationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Creates notification when the opponent is five hits away from winning.
        playerOneFiveAwayReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean playerOneFiveAway = dataSnapshot.getValue(Boolean.class);
                if (playerOneFiveAway) {
                    createNotificationFiveAway();
                    playerOneFiveAwayReference.setValue(FALSE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Creates notification when the opponent is one hit away from winning.
        playerOneOneAwayReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean playerOneOneAway = dataSnapshot.getValue(Boolean.class);
                if (playerOneOneAway) {
                    createNotificationOneAway();
                    playerOneOneAwayReference.setValue(FALSE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Assigns correct values to the booleanBoard based on the data retrieved from Firebase.
     */
    public void createBooleanBoard() {
        booleanRowOne.toArray(booleanBoard[0]);
        booleanRowTwo.toArray(booleanBoard[1]);
        booleanRowThree.toArray(booleanBoard[2]);
        booleanRowFour.toArray(booleanBoard[3]);
        booleanRowFive.toArray(booleanBoard[4]);
    }

    /**
     * Adds onClickListeners to the TextViews.
     */
    public void addTextViewListeners() {
        boxZeroZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(0, 0, boxZeroZero);
            }
        });
        boxZeroOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(0, 1, boxZeroOne);
            }
        });
        boxZeroTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onClickListenerWork(0, 2, boxZeroTwo);

            }
        });
        boxZeroThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(0, 3, boxZeroThree);

            }
        });
        boxZeroFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(0, 4, boxZeroFour);

            }
        });
        boxOneZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(1, 0, boxOneZero);

            }
        });
        boxOneOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(1, 1, boxOneOne);

            }
        });
        boxOneTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(1, 2, boxOneTwo);

            }
        });
        boxOneThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(1, 3, boxOneThree);

            }
        });
        boxOneFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(1, 4, boxOneFour);

            }
        });
        boxTwoZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(2, 0, boxTwoZero);

            }
        });
        boxTwoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(2, 1, boxTwoOne);
            }
        });
        boxTwoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(2, 2, boxTwoTwo);
            }
        });
        boxTwoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(2, 3, boxTwoThree);
            }
        });
        boxTwoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(2, 4, boxTwoFour);
            }
        });
        boxThreeZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(3, 0, boxThreeZero);
            }
        });
        boxThreeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(3, 1, boxThreeOne);

            }
        });
        boxThreeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(3, 2, boxThreeTwo);

            }
        });
        boxThreeThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(3, 3, boxThreeThree);

            }
        });
        boxThreeFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(3, 4, boxThreeFour);

            }
        });
        boxFourZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(4, 0, boxFourZero);

            }
        });
        boxFourOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(4, 1, boxFourOne);
            }
        });
        boxFourTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(4, 2, boxFourTwo);
            }
        });
        boxFourThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(4, 3, boxFourThree);
            }
        });
        boxFourFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(4, 4, boxFourFour);
            }
        });
    }

    /**
     * Removes listeners form the TextViews when it is not player two's turn.
     */
    public void removeTextViewListeners() {
        boxZeroZero.setOnClickListener(null);
        boxZeroOne.setOnClickListener(null);
        boxZeroTwo.setOnClickListener(null);
        boxZeroThree.setOnClickListener(null);
        boxZeroFour.setOnClickListener(null);
        boxOneZero.setOnClickListener(null);
        boxOneOne.setOnClickListener(null);
        boxOneTwo.setOnClickListener(null);
        boxOneThree.setOnClickListener(null);
        boxOneFour.setOnClickListener(null);
        boxTwoZero.setOnClickListener(null);
        boxTwoOne.setOnClickListener(null);
        boxTwoTwo.setOnClickListener(null);
        boxTwoThree.setOnClickListener(null);
        boxTwoFour.setOnClickListener(null);
        boxThreeZero.setOnClickListener(null);
        boxThreeOne.setOnClickListener(null);
        boxThreeTwo.setOnClickListener(null);
        boxThreeThree.setOnClickListener(null);
        boxThreeFour.setOnClickListener(null);
        boxFourZero.setOnClickListener(null);
        boxFourOne.setOnClickListener(null);
        boxFourTwo.setOnClickListener(null);
        boxFourThree.setOnClickListener(null);
        boxFourFour.setOnClickListener(null);
    }

    /**
     * Method creates notification notifying the player that its their turn.
     */
    public void createNotificationPlayerTwo() {

        PendingIntent resultIntent = intentWork();

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Notification")
                .setContentText("It's your turn!")
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());

    }

    /**
     * Method creates notification notifying the player that their opponent is five hits away from winning.
     */
    public void createNotificationFiveAway() {

        PendingIntent resultIntent = intentWorkAlternative();

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Notification")
                .setContentText("Your opponent is five hits away from winning!")
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, mNotificationBuilder.build());

    }

    /**
     * Method creates notification notifying the player that their opponent is one hit away from winning.
     */
    public void createNotificationOneAway() {

        PendingIntent resultIntent = intentWorkAlternative();

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Notification")
                .setContentText("Your opponent is one hit away from winning, hurry up!")
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(2, mNotificationBuilder.build());

    }

    /**
     * Makes sure notification goes to ResultActivity after it gets tapped on.
     *
     * @return PendingIntent - intent of the nextActivity.
     */
    public PendingIntent intentWork() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    /**
     * Makes sure notification goes to CloserToWinning after it gets tapped on.
     *
     * @return PendingIntent - intent of the nextActivity.
     */
    public PendingIntent intentWorkAlternative() {
        Intent intent = new Intent(this, CloserToWinning.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    /**
     * The updates the UI based on whether the user clicks a box that their opponent had placed one of their ships on.
     *
     * @param rowIndex    - Row index of booleanBoard
     * @param columnIndex - Column Index of booleanBoard
     * @param box         - arbitrary TextView
     */
    public void onClickListenerWork(int rowIndex, int columnIndex, TextView box) {

        if (booleanBoard[rowIndex][columnIndex]) {
            final int HIT_TEXT_SIZE = 30;
            box.setText("HIT!!");
            box.setTextColor(Color.WHITE);
            box.setTextSize(HIT_TEXT_SIZE);
            box.setBackgroundColor(Color.RED);
            hitCnt++;
            if (hitCnt == 9) {
                gameFinishedReference.setValue(TRUE);
            } else if (hitCnt == 4) {
                playerTwoFiveAwayReference.setValue(TRUE);
            } else if (hitCnt == 8) {
                playerTwoOneAwayReference.setValue(TRUE);
            }
        } else {
            final int MISS_TEXT_SIZE = 27;
            box.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.shadeOfBlue));
            box.setText("MISS");
            box.setTextColor(Color.WHITE);
            box.setTextSize(MISS_TEXT_SIZE);
        }

        removeTextViewListeners();
        turnTrackerReference.setValue(FALSE);
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
        AuthUI.getInstance().signOut(PlayerTwoMoves.this);
        playerSignedOutReference.setValue(TRUE);
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
