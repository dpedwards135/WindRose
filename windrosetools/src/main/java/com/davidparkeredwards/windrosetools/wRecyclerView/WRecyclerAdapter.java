package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.activity.WRecyclerViewActivity;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.wForm.WFormField;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /* Don't mess with inserting into RecyclerView, etc, just modify the ArrayList with the
    data and rebuild the RecyclerView. That means any edits need to be saved to the constructorObjects
    and that is always up to date. */

    private static final String TAG = WRecyclerAdapter.class.getSimpleName();

    //Rewrite to make bundle the foundation of the adapter and everything gets saved to it.

    private WRecyclerBundle bundle;
    private DbObject dbObject;
    private Context activity;

    public WRecyclerAdapter(DbObject dbObject, Context activity) {
        this.bundle = dbObject.toWRecyclerBundle(false);
        this.dbObject = dbObject;
        this.activity = activity;

    }

    /*
    public WRecyclerAdapter(WRecyclerBundle bundle) {
        this.bundle = bundle;
        Log.i(TAG, "WRecyclerAdapter: Bundle : " + bundle.getRecyclerObjectsArray().size());
    }
    */

    @Override
    public int getItemViewType(int position) {
        return bundle.getRecyclerObject(position).getWRecyclerViewType();
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder;
            int layoutResource = 0;
            View inflatedView;

            switch (viewType) {
                case WFormField.GEOSTOP:
                    layoutResource = R.layout.wviewholder_geopoint;
                    inflatedView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,
                            parent,false);
                    RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    inflatedView.setLayoutParams(lp);
                    viewHolder = new WViewHolder(inflatedView, this);
                    break;

                default:
                    layoutResource = R.layout.wviewholder;
                    inflatedView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,
                            parent,false);
                    RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    inflatedView.setLayoutParams(rlp);
                    viewHolder = new WViewHolder(inflatedView, this);
                    Log.i(TAG, "onCreateViewHolder: ");
                    break;
            }
            return viewHolder;
        }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        WFormField wFormField = bundle.getRecyclerObject(position);
        //WFormField wFormField = constructorObjects.get(position);
        WViewHolder wViewHolder = (WViewHolder) holder;
        wViewHolder.bindObject(wFormField);
    }

    @Override
    public int getItemCount() {
        return bundle.getRecyclerObjectsArray().size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        //Save and persist dataset
        WFormField object = ((WViewHolder) holder).saveData();
        int position = holder.getAdapterPosition();
        bundle.saveRecyclerObject(position, object);
        super.onViewRecycled(holder);
    }

    public WRecyclerBundle getSavedBundle() { return bundle;}


    public DbObject getDbObject() { return dbObject; }

    public void saveAllRecyclerObjects() {

        WRecyclerViewActivity activity1 = (WRecyclerViewActivity) activity;
        LinearLayoutManager llm = activity1.getmLinearLayoutManager();
        int start = llm.findFirstVisibleItemPosition();
        int end = llm.findLastVisibleItemPosition();

        int counter = start;
        while(counter <= end) {
            WViewHolder holder = (WViewHolder) activity1.getmRecyclerView().findViewHolderForLayoutPosition(counter);
            WFormField object = holder.saveData();
            int position = holder.getAdapterPosition();
            bundle.saveRecyclerObject(position, object);
            Log.i(TAG, "saveAllRecyclerObjects: Save");
            counter++;
        }
    }
}

