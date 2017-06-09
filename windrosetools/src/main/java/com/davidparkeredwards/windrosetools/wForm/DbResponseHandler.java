package com.davidparkeredwards.windrosetools.wForm;

import android.content.Context;
import android.util.Log;

/**
 * Created by davidedwards on 6/9/17.
 */

public class DbResponseHandler {

    private static final String TAG = DbResponseHandler.class.getSimpleName();


    private Context context;
    
    public DbResponseHandler(Context context) {
        this.context = context;
    }
    
    public void onSubscribe() {
        Log.i(TAG, "onSubscribe: ");
    }
    
    public void onNext() {
        Log.i(TAG, "onNext: ");
    }
    
    public void onComplete() {
        Log.i(TAG, "onComplete: ");
    }
    
    public void onError() {
        Log.i(TAG, "onError: ");
    }
}
