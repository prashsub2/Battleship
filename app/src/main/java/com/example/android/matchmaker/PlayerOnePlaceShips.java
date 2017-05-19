package com.example.android.matchmaker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * Class allows the user that is player one to place their ships before the start of the game.
 */
public class PlayerOnePlaceShips extends AppCompatActivity {
    private TextView boxZeroZero, boxZeroOne, boxZeroTwo, boxZeroThree, boxZeroFour, boxOneZero, boxOneOne, boxOneTwo,
            boxOneThree, boxOneFour, boxTwoZero, boxTwoOne, boxTwoTwo, boxTwoThree, boxTwoFour, boxThreeZero, boxThreeOne,
            boxThreeTwo, boxThreeThree, boxThreeFour, boxFourZero, boxFourOne, boxFourTwo, boxFourThree, boxFourFour;

    private Button finish;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference playerOneBoardReference = database.getReference("playerOneBoard");
    private DatabaseReference startMovesCntReference = database.getReference("startMovesCnt");
    private DatabaseReference playerSignedOutReference = database.getReference("onePlayerSignedOut");
    private int nextActivityCnt = -1;
    private boolean[][] booleanBoard = new boolean[5][5];
    private int[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);
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
        finish = (Button) findViewById(R.id.finish);

        //Fill ids array with every TextViews id in order to iterate through the TextViews.
        ids = new int[]{R.id.box1, R.id.box2, R.id.box3, R.id.box4, R.id.box5, R.id.box6, R.id.box7, R.id.box8, R.id.box9,
                R.id.box10, R.id.box11, R.id.box12, R.id.box13, R.id.box14, R.id.box15, R.id.box16, R.id.box17, R.id.box18,
                R.id.box19, R.id.box20, R.id.box21, R.id.box22, R.id.box23, R.id.box24, R.id.box25};

        //Creates onClickListener for each textView so that the user is able to toggle them from blue to white.
        boxZeroZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxZeroZero);
            }
        });
        boxZeroOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxZeroOne);
            }
        });
        boxZeroTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxZeroTwo);
            }
        });
        boxZeroThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxZeroThree);
            }
        });
        boxZeroFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxZeroFour);
            }
        });
        boxOneZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxOneZero);
            }
        });
        boxOneOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxOneOne);
            }
        });
        boxOneTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxOneTwo);
            }
        });
        boxOneThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxOneThree);
            }
        });
        boxOneFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxOneFour);
            }
        });
        boxTwoZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxTwoZero);
            }
        });
        boxTwoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxTwoOne);
            }
        });
        boxTwoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxTwoTwo);
            }
        });
        boxTwoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxTwoThree);
            }
        });
        boxTwoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxTwoFour);
            }
        });
        boxThreeZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxThreeZero);
            }
        });
        boxThreeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxThreeOne);
            }
        });
        boxThreeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxThreeTwo);
            }
        });
        boxThreeThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxThreeThree);
            }
        });
        boxThreeFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxThreeFour);
            }
        });
        boxFourZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxFourZero);
            }
        });
        boxFourOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxFourOne);
            }
        });
        boxFourTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxFourTwo);
            }
        });
        boxFourThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxFourThree);
            }
        });
        boxFourFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerWork(boxFourFour);
            }
        });

        //OnClickListener for the finish button, determines whether the user has placed ships correctly. If so, it
        //adds their newly created board to Firebase.
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = fillBooleanBoard();
                int correctNumberOfShips = 9;

                if (cnt > correctNumberOfShips) {
                    Toast.makeText(getApplicationContext(), "You have selected too many boxes", Toast.LENGTH_LONG).
                            show();
                } else if (cnt < correctNumberOfShips) {
                    Toast.makeText(getApplicationContext(), "Not enough boxes were selected", Toast.LENGTH_LONG).
                            show();

                } else if (!CheckBoardValidity.checkBoardFormation(booleanBoard)) {
                    Toast.makeText(getApplicationContext(), "Invalid formation of boxes selected", Toast.LENGTH_LONG).
                            show();

                } else {
                    addToFirebase();
                }
            }
        });

        //Reference tracks when to move user on to the next activity.
        startMovesCntReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nextActivityCnt++;
                if (nextActivityCnt == 2) {
                    Intent intent = new Intent(PlayerOnePlaceShips.this, PlayerOneMoves.class);
                    startActivity(intent);
                }
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
                    Toast.makeText(getApplicationContext(), "One player signed out, so the game cannot start",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PlayerOnePlaceShips.this, AuthenticationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    /**
     * Logic that every clickable TextView must have with in its onClickListener. The method makes each TextView
     * toggleable from blue to white.
     *
     * @param box - the TextView which this method is being called for.
     */
    public void onClickListenerWork(TextView box) {
        ColorDrawable currentColor = (ColorDrawable) (box.getBackground());
        int colorCode = 0;

        if (currentColor != null) {
            colorCode = currentColor.getColor();
        }
        if (colorCode == Color.BLUE) {
            box.setBackgroundColor(Color.WHITE);
        } else {
            box.setBackgroundColor(Color.BLUE);
        }

    }

    /**
     * Fills the booleanBoard 2D array and increments the count based on how many boxes are left blue.
     *
     * @return - the number of boxes that are left blue.
     */
    public int fillBooleanBoard() {
        int cnt = 0;
        for (int i = 0; i < 25; i++) {
            TextView box = (TextView) findViewById(ids[i]);
            ColorDrawable currentColor = (ColorDrawable) box.getBackground();
            if (currentColor != null) {
                int colorCode = currentColor.getColor();
                if (colorCode == Color.BLUE) {
                    cnt++;
                    switch (i + 1) {
                        case 1:
                            booleanBoard[0][0] = true;
                            break;
                        case 2:
                            booleanBoard[0][1] = true;
                            break;
                        case 3:
                            booleanBoard[0][2] = true;
                            break;
                        case 4:
                            booleanBoard[0][3] = true;
                            break;
                        case 5:
                            booleanBoard[0][4] = true;
                            break;
                        case 6:
                            booleanBoard[1][0] = true;
                            break;
                        case 7:
                            booleanBoard[1][1] = true;
                            break;
                        case 8:
                            booleanBoard[1][2] = true;
                            break;
                        case 9:
                            booleanBoard[1][3] = true;
                            break;
                        case 10:
                            booleanBoard[1][4] = true;
                            break;
                        case 11:
                            booleanBoard[2][0] = true;
                            break;
                        case 12:
                            booleanBoard[2][1] = true;
                            break;
                        case 13:
                            booleanBoard[2][2] = true;
                            break;
                        case 14:
                            booleanBoard[2][3] = true;
                            break;
                        case 15:
                            booleanBoard[2][4] = true;
                            break;
                        case 16:
                            booleanBoard[3][0] = true;
                            break;
                        case 17:
                            booleanBoard[3][1] = true;
                            break;
                        case 18:
                            booleanBoard[3][2] = true;
                            break;
                        case 19:
                            booleanBoard[3][3] = true;
                            break;
                        case 20:
                            booleanBoard[3][4] = true;
                            break;
                        case 21:
                            booleanBoard[4][0] = true;
                            break;
                        case 22:
                            booleanBoard[4][1] = true;
                            break;
                        case 23:
                            booleanBoard[4][2] = true;
                            break;
                        case 24:
                            booleanBoard[4][3] = true;
                            break;
                        case 25:
                            booleanBoard[4][4] = true;
                            break;

                    }

                }
            }
        }
        return cnt;
    }

    /**
     * Adds a nested boolean ArrayList to firebase which serves as 2D boolean array when it is retrieved later.
     */
    public void addToFirebase() {

        List<Boolean> booleanRowOne = new ArrayList<Boolean>();
        for (int i = 0; i < booleanBoard[0].length; i++) {
            booleanRowOne.add(booleanBoard[0][i]);
        }
        List<Boolean> booleanRowTwo = new ArrayList<Boolean>();
        for (int i = 0; i < booleanBoard[1].length; i++) {
            booleanRowTwo.add(booleanBoard[1][i]);
        }
        List<Boolean> booleanRowThree = new ArrayList<Boolean>();
        for (int i = 0; i < booleanBoard[2].length; i++) {
            booleanRowThree.add(booleanBoard[2][i]);
        }
        List<Boolean> booleanRowFour = new ArrayList<Boolean>();
        for (int i = 0; i < booleanBoard[3].length; i++) {
            booleanRowFour.add(booleanBoard[3][i]);
        }
        List<Boolean> booleanRowFive = new ArrayList<Boolean>();
        for (int i = 0; i < booleanBoard[4].length; i++) {
            booleanRowFive.add(booleanBoard[4][i]);
        }

        List<List<Boolean>> nestedBooleanList = new ArrayList<List<Boolean>>();
        nestedBooleanList.add(booleanRowOne);
        nestedBooleanList.add(booleanRowTwo);
        nestedBooleanList.add(booleanRowThree);
        nestedBooleanList.add(booleanRowFour);
        nestedBooleanList.add(booleanRowFive);

        playerOneBoardReference.setValue(nestedBooleanList);
        Toast.makeText(getApplicationContext(), "You, Player1, have placed your ships", Toast.LENGTH_LONG).show();
        startMovesCntReference.setValue((int) (Math.random() * 100) + 1);
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
        AuthUI.getInstance().signOut(PlayerOnePlaceShips.this);
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
