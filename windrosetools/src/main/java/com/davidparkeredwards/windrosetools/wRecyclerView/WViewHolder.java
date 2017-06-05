package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerFinalizeButtons;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObject;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerTextView;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Try creating base class for WViewHolder that takes a view AND an object reference and whatever happens in
    //the view changes the object
    //Try to create just one ViewHolder that with minimal processing can take any object, create
    //a view and manipulate the object for saving and persistence.
    //Try to create just one WRecyclerObject which defines which ViewHolder elements to make visible

    private WRecyclerObject wRecyclerObject;
    private int viewType;
    private View v;

    //Put all the widgets in here, default "GONE" and set visible if needed.
    private TextView textView;
    private CheckBox checkBox;
    private Spinner spinner;
    private EditText editText;
    private Button button1;
    private Button button2;
    private Button button3;

    

    public WViewHolder(View v) {
        super(v);
        this.v = v;
    }

    private void configureViewHolder() {

        //ViewHolder Config:
        switch (viewType) {
            case WRecyclerObject.TEXT_VIEW:
                WRecyclerTextView wText = (WRecyclerTextView) wRecyclerObject;
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(wText.getText());
                break;

            case WRecyclerObject.CHECKBOX:
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                checkBox = (CheckBox) v.findViewById(R.id.wviewholder_checkbox);
                checkBox.setVisibility(View.VISIBLE);
                break;

            case WRecyclerObject.FINALIZE_BUTTONS:
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                WRecyclerFinalizeButtons finalizeButtons = (WRecyclerFinalizeButtons) wRecyclerObject;
                int numberOfButtons = finalizeButtons.getNumberOfButtons();
                switch(numberOfButtons) {
                    //Configure all button characteristics here
                    case 3:
                        button3.setVisibility(View.VISIBLE);
                    case 2:
                        button2.setVisibility(View.VISIBLE);
                    case 1:
                        button1.setVisibility(View.VISIBLE);
                    case 0:
                        break;

                }
                break;

            case WRecyclerObject.GEOPOINT:
                break;

            case WRecyclerObject.SELECT_FROM:
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                spinner = (Spinner) v.findViewById(R.id.wviewholder_spinner);
                spinner.setVisibility(View.VISIBLE);
                break;

            case WRecyclerObject.TEXT_EDIT:
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                editText = (EditText) v.findViewById(R.id.wviewholder_edittext);
                editText.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }


        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("RecyclerView", "CLICK!" + textView.getText());
    }

    public void bindObject(WRecyclerObject wRecyclerObject) {
        this.wRecyclerObject = wRecyclerObject;
        viewType = wRecyclerObject.getWRecyclerViewType();
        configureViewHolder();
    }

    public WRecyclerObject saveData() {
        return wRecyclerObject;
    }
}
