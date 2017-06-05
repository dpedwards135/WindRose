package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerTextView implements WRecyclerObject {

    private String text = "Test text string";


    public WRecyclerTextView() {}

    public WRecyclerTextView(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getWRecyclerViewType() {
        return 0;
    }

    @Override
    public boolean getIsEditable() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new WRecyclerTextViewHolder(view);
    }

    public class WRecyclerTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //2

        private TextView mItemDescription;

        //4
        public WRecyclerTextViewHolder(View v) {
            super(v);

            mItemDescription = (TextView) v.findViewById(R.id.wrecycler_text_view_text_view);
            v.setOnClickListener(this);
        }

        //5
        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!" + mItemDescription.getText());
        }

        public void bindText(String text) {

            mItemDescription.setText(text);
        }
    }
}
