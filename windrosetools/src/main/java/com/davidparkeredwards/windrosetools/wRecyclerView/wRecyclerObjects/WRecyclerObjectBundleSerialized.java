package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/6/17.
 */

public class WRecyclerObjectBundleSerialized {

    public List<WRecyclerObject> recyclerObjects;
    public String classKey;
    public String submissionKey;

    public WRecyclerObjectBundleSerialized(WRecyclerObjectBundle bundle) {
        recyclerObjects = bundle.getRecyclerObjectsArray();
        this.classKey = bundle.getClassKey();
        this.submissionKey = bundle.getSubmissionKey();
    }

    public WRecyclerObjectBundle deserialize() {

        ArrayList<WRecyclerObject> arrayList = new ArrayList<>();
        for(WRecyclerObject object : recyclerObjects) {
            arrayList.add(object);
        }
        WRecyclerObjectBundle bundle = new WRecyclerObjectBundle(classKey, arrayList, submissionKey);
        return bundle;
    }
}
