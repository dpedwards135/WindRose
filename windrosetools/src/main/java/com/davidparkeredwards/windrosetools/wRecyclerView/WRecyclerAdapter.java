package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidparkeredwards.windrosetools.R;
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

    public WRecyclerAdapter(WRecyclerBundle bundle) {
        this.bundle = bundle;
        Log.i(TAG, "WRecyclerAdapter: Bundle : " + bundle.getRecyclerObjectsArray().size());
    }

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

    /*
    public ArrayList<WFormField> getSavedObjects() {
        return constructorObjects;
    }
    */
    public WRecyclerBundle getSavedBundle() { return bundle;}
}

