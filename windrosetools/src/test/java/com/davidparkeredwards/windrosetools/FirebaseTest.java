package com.davidparkeredwards.windrosetools;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidedwards on 5/27/17.
 */

@RunWith(MockitoJUnitRunner.class)

public class FirebaseTest {

    @Mock
    Context mContext;

    private static final String TAG = FirebaseTest.class.getSimpleName();

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void firebaseDbIsOnline() throws Exception {

        WindroseApplication app = new WindroseApplication();
        FirebaseApp.initializeApp(mContext);
        FirebaseDatabase db = app.firebaseDatabase;
        DatabaseReference ref = db.getReference("/dbCheck1");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.i(TAG, "onDataChange: Value = " + value);
                assertEquals("Lots to do", value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: Cancelled");

            }
        });
    }
}
