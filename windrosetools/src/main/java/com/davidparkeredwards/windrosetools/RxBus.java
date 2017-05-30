package com.davidparkeredwards.windrosetools;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by davidedwards on 5/29/17.
 */

public class RxBus {

    private static final String TAG = RxBus.class.getSimpleName();


    private static final RxBus INSTANCE = new RxBus();

    public static RxBus getInstance() {
        return INSTANCE;
    }

    private final Subject<Object> mBusSubject = PublishSubject.create();

    public void getCompanyIds() {
        mBusSubject.onNext("Test");
        mBusSubject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: Subscribed to bus subject");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i(TAG, "onNext: Object = " + o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: Complete");
                    }
                });

        mBusSubject.onNext("Test2");
    }


}
