package com.example.android.testrealtimedatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by prash on 4/18/2017.
 */

public class DatabaseFunctions {

    /**
     * Sets value of node in Firebase based on reference and value, decrements countDownLatch
     * @param reference - DatabaseReference
     * @param value - Either a String or a boolean
     * @param countDownLatch - CountDwonLatch
     */

    public static void setValue(DatabaseReference reference, Object value, final CountDownLatch countDownLatch){

        reference.setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                countDownLatch.countDown();
            }
        });
    }

    /**
     * Adds children as moves to the parentNode specified.
     * @param moves - String Array of moves
     * @param parentNode - Node of parent in String form
     * @param countDownLatch - CountDownLatch object
     * @param database - instance of Firebase database
     */
    public static void setValueOfMoves(String[] moves, String parentNode, final CountDownLatch countDownLatch,
                                       FirebaseDatabase database){

        for( int i = 0; i < moves.length; i ++){
            DatabaseReference move = database.getReference(parentNode).child("Move" + (i + 1));
            move.setValue(moves[i]).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    countDownLatch.countDown();
                }
            });
        }
    }


}
