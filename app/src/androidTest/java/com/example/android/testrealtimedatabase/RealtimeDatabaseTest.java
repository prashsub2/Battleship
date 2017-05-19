package com.example.android.testrealtimedatabase;

import android.support.annotation.NonNull;
import android.support.test.espresso.action.AdapterViewProtocol;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

/**
 * Created by prash on 4/18/2017.
 * Tests use of Firebase
 *
 * In each method, there is the use of a CountDownLatch object.
 * The initial count the number sent to the ConstructorDownLatch constructor.
 * Calling countDown() on a ConstructorDownLatch decrements this count.
 * When await( int, TimeUnit) means that the background thread must connect to the main thread either when the
 * initial count reaches zero or after the amount of seconds specified.
 */
@RunWith(AndroidJUnit4.class)
public class RealtimeDatabaseTest {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final String PLAYER_ONE_BOARD = "playerOneBoard";
    final String PLAYER_TWO_MOVES = "playerTwoMoves";

    /**
     * Method tests the ability to write a twice nested list and then read specific values from the node in
     * Firebase.
     *
     */
    @Test
    public void writeAndReadBoard() throws Exception{
        DatabaseReference boardReference = database.getReference(PLAYER_ONE_BOARD);

        List <Boolean> booleanRowOne = new ArrayList<>();
        booleanRowOne.add(true); 
        booleanRowOne.add(true);
        booleanRowOne.add(false);

        List <Boolean> booleanRowTwo = new ArrayList<>();
        booleanRowTwo.add(true);
        booleanRowTwo.add(false);
        booleanRowTwo.add(true);

        List <Boolean> booleanRowThree = new ArrayList<>();
        booleanRowThree.add(false);
        booleanRowThree.add(true);
        booleanRowThree.add(true);

        List <List <Boolean>> nestedBooleanList = new ArrayList<>();
        nestedBooleanList.add(booleanRowOne);
        nestedBooleanList.add(booleanRowTwo);
        nestedBooleanList.add(booleanRowThree);

        final CountDownLatch countDownLatch = new CountDownLatch(4);

        //Write into playerOneBoard inn the Firebase database
        DatabaseFunctions.setValue(boardReference, nestedBooleanList, countDownLatch);

        //Read three different values within the nested list.
        DatabaseReference indexZeroOne = database.getReference(PLAYER_ONE_BOARD + "/0/1");
        indexZeroOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue(dataSnapshot.getValue(Boolean.class));
                countDownLatch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        DatabaseReference indexOneOne = database.getReference(PLAYER_ONE_BOARD + "/1/1");
        indexOneOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertFalse(dataSnapshot.getValue(Boolean.class));
                countDownLatch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        DatabaseReference indexTwoTwo = database.getReference(PLAYER_ONE_BOARD + "/2/2");
        indexTwoTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue(dataSnapshot.getValue(Boolean.class));
                countDownLatch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        countDownLatch.await(1,TimeUnit.SECONDS);
    }

    /**
     * Tests the ability to write moves to the playerTwoMoves node in Firebase and read the right Moves from it.
     */
    @Test
    public void writeAndReadMoves() throws Exception{
        final String[] moves = new String[]{"A5", "B4", "C2", "E1", "D3"};
        final CountDownLatch countDownLatch = new CountDownLatch(10);

        //Write all 5 moves into playerTwoMovesNode
        DatabaseFunctions.setValueOfMoves(moves, PLAYER_TWO_MOVES, countDownLatch, database);

        //Read all 5 moves and assertEqual to their value written above.
        for( int i = 0; i < moves.length; i ++){
            final int index = i;
            DatabaseReference move = database.getReference(PLAYER_TWO_MOVES).child("Move" + (i + 1));
            move.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    assertEquals(moves[index], dataSnapshot.getValue(String.class));
                    countDownLatch.countDown();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        countDownLatch.await(1, TimeUnit.SECONDS);
    }

    /**
     * Write name into playerOneName and read same name from the node.
     */
    @Test
    public void writeAndReadName() throws Exception{
        DatabaseReference nameReference = database.getReference("playerOneName");
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        //Write name into the node.
        DatabaseFunctions.setValue(nameReference, "Prashant Subramanian", countDownLatch);

        //Read from the node.
        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertEquals("Prashant Subramanian", dataSnapshot.getValue(String.class));
                countDownLatch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        countDownLatch.await(1, TimeUnit.SECONDS);
    }



}
