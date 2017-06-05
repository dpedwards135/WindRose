package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObject;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /* Don't mess with inserting into RecyclerView, etc, just modify the ArrayList with the
    data and rebuild the RecyclerView. That means any edits need to be saved to the constructorObjects
    and that is always up to date. */

    private ArrayList<WRecyclerObject> constructorObjects;

    public WRecyclerAdapter(ArrayList<WRecyclerObject> constructorObjects) {
        this.constructorObjects = constructorObjects;
    }

    @Override
    public int getItemViewType(int position) {
        return constructorObjects.get(position).getWRecyclerViewType();
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder;
            int layoutResource = 0;
            View inflatedView;

            switch (viewType) {
                case WRecyclerObject.GEOPOINT:
                    layoutResource = R.layout.wviewholder_geopoint;
                    inflatedView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,
                            parent,false);
                    viewHolder = new WViewHolder(inflatedView);
                    break;

                default:
                    layoutResource = R.layout.wviewholder;
                    inflatedView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,
                            parent,false);
                    viewHolder = new WViewHolder(inflatedView);
            }
            return viewHolder;
        }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        WRecyclerObject wRecyclerObject = constructorObjects.get(position);
        WViewHolder wViewHolder = (WViewHolder) holder;
        wViewHolder.bindObject(wRecyclerObject);
    }

    @Override
    public int getItemCount() {
        return constructorObjects.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        //Save and persist dataset
        WRecyclerObject object = ((WViewHolder) holder).saveData();
        int position = holder.getAdapterPosition();
        constructorObjects.set(position, object);
        super.onViewRecycled(holder);
    }

    public ArrayList<WRecyclerObject> getSavedObjects() {
        return constructorObjects;
    }
}

