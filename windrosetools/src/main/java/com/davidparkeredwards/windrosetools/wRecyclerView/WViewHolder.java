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
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerCheckBox;
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
    private Button submitButton;
    private Button saveButton;
    private Button cancelButton;

    

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
                WRecyclerCheckBox wCheckBox = (WRecyclerCheckBox) wRecyclerObject;
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(wCheckBox.getText());
                checkBox = (CheckBox) v.findViewById(R.id.wviewholder_checkbox);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(wCheckBox.getTrueOrFalse());
                break;

            case WRecyclerObject.FINALIZE_BUTTONS:
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                WRecyclerFinalizeButtons finalizeButtons = (WRecyclerFinalizeButtons) wRecyclerObject;

                if(finalizeButtons.isSetSubmit()) {
                    submitButton = (Button) v.findViewById(R.id.wviewholder_submitButton);
                    submitButton.setVisibility(View.VISIBLE);
                }

                if(finalizeButtons.isSetSave()) {
                    saveButton = (Button) v.findViewById(R.id.wviewholder_saveButton);
                    saveButton.setVisibility(View.VISIBLE);
                }

                if(finalizeButtons.isSetCancel()) {
                    cancelButton = (Button) v.findViewById(R.id.wviewholder_cancelButton);
                    cancelButton.setVisibility(View.VISIBLE);
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
