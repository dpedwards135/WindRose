package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidparkeredwards.windrosetools.R;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Define a viewHolder for each type of view required.

    private ArrayList<WRecyclerObject> constructorObjects;

    public WRecyclerAdapter(ArrayList<WRecyclerObject> constructorObjects) {
    }

    @Override
    public int getItemViewType(int position) {

        return constructorObjects.get(position).getWRecyclerViewType();

        //return super.getItemViewType(position);


    }

    /*
        @Override
        public int getItemViewType(int position) {
            // Just as an example, return 0 or 2 depending on position
            // Note that unlike in ListView adapters, types don't have to be contiguous
            return position % 2 * 2;
        }
        */

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder;
            int layoutResource = 0;
            View inflatedView;

            //Next - build the rest of the ViewHolders and cases for inflating
            switch (viewType) {
                case WRecyclerObject.TEXT_VIEW:
                    layoutResource = R.layout.wrecycler_text_view;
                    inflatedView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,
                            parent,false);
                    viewHolder = new WRecyclerTextView().getViewHolder(inflatedView);
                    break;
                default:
                    layoutResource = R.layout.wrecycler_text_view;
                    inflatedView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,
                            parent,false);
                    viewHolder = new WRecyclerTextView().getViewHolder(inflatedView);
            }

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            WRecyclerObject wRecyclerObject = constructorObjects.get(position);
            switch (holder.getItemViewType()) {
                case WRecyclerObject.TEXT_VIEW:
                    WRecyclerTextView.WRecyclerTextViewHolder vh = (WRecyclerTextView.WRecyclerTextViewHolder) holder;
                    WRecyclerTextView wRecyclerTextView = (WRecyclerTextView) wRecyclerObject;
                    vh.bindText(wRecyclerTextView.getText());
            }
        }

    @Override
    public int getItemCount() {
        return constructorObjects.size();
    }
}

