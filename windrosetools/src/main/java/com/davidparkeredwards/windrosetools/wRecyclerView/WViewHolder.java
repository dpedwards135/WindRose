package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.wForm.WCheckBox;
import com.davidparkeredwards.windrosetools.wForm.WFormField;
import com.davidparkeredwards.windrosetools.wForm.WTextView;
import com.davidparkeredwards.windrosetools.wForm.WFinalizeButtons;
import com.davidparkeredwards.windrosetools.wForm.WSelectFrom;
import com.davidparkeredwards.windrosetools.wForm.WTextEdit;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Try creating base class for WViewHolder that takes a view AND an object reference and whatever happens in
    //the view changes the object
    //Try to create just one ViewHolder that with minimal processing can take any object, create
    //a view and manipulate the object for saving and persistence.
    //Try to create just one WFormField which defines which ViewHolder elements to make visible

    private WFormField wFormField;
    private int viewType;
    private View v;
    private WRecyclerAdapter adapter;

    //Put all the widgets in here, default "GONE" and set visible if needed.
    private TextView textView;
    private CheckBox checkBox;
    private Spinner spinner;
    private EditText editText;
    private Button submitButton;
    private Button saveButton;
    private Button cancelButton;

    

    public WViewHolder(View v, WRecyclerAdapter adapter) {
        super(v);
        this.v = v;
        this.adapter = adapter;
    }

    private void configureViewHolder() {

        //ViewHolder Config:
        switch (viewType) {
            case WFormField.TEXT_VIEW:
                WTextView wText = (WTextView) wFormField;
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(wText.getText());
                break;

            case WFormField.CHECKBOX:
                WCheckBox wCheckBox = (WCheckBox) wFormField;
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(wCheckBox.getText());
                checkBox = (CheckBox) v.findViewById(R.id.wviewholder_checkbox);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(wCheckBox.getTrueOrFalse());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        wCheckBox.setTrueOrFalse(isChecked);
                    }
                });
                break;

            case WFormField.FINALIZE_BUTTONS:

                WFinalizeButtons finalizeButtons = (WFinalizeButtons) wFormField;
                Log.i("Finalize Buttons", "configureViewHolder: ");
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);

                if(finalizeButtons.isSetSubmit()) {
                    submitButton = (Button) v.findViewById(R.id.wviewholder_submitButton);
                    submitButton.setVisibility(View.VISIBLE);
                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalizeButtons.onClickSubmit(v.getContext(), adapter.getSavedBundle());
                        }
                    });
                }

                if(finalizeButtons.isSetSave()) {
                    saveButton = (Button) v.findViewById(R.id.wviewholder_saveButton);
                    saveButton.setVisibility(View.VISIBLE);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalizeButtons.onClickSave(v.getContext(), adapter.getSavedBundle());
                        }
                    });
                }

                if(finalizeButtons.isSetCancel()) {
                    cancelButton = (Button) v.findViewById(R.id.wviewholder_cancelButton);
                    cancelButton.setVisibility(View.VISIBLE);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalizeButtons.onClickCancel(v.getContext(), adapter.getSavedBundle());
                        }
                    });
                }

                break;

            case WFormField.GEOSTOP:
                break;

            case WFormField.SELECT_FROM:
                WSelectFrom wRecyclerSelectFrom = (WSelectFrom) wFormField;
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(wRecyclerSelectFrom.getText());
                spinner = (Spinner) v.findViewById(R.id.wviewholder_spinner);
                spinner.setVisibility(View.VISIBLE);
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(v.getContext(),
                        android.R.layout.simple_spinner_item, wRecyclerSelectFrom.getValues());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setPrompt(wRecyclerSelectFrom.getSpinnerPrompt());
                spinner.setSelection(wRecyclerSelectFrom.getSelectedValue());
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        wRecyclerSelectFrom.setSelectedValue(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                break;

            case WFormField.TEXT_EDIT:
                WTextEdit textEdit = (WTextEdit) wFormField;
                textView = (TextView) v.findViewById(R.id.wviewholder_text);
                textView.setVisibility(View.VISIBLE);
                textView.setText(textEdit.getText());
                editText = (EditText) v.findViewById(R.id.wviewholder_edittext);
                editText.setVisibility(View.VISIBLE);
                if(textEdit.getUserInput() == null) {
                    editText.setHint(textEdit.getPrompt());
                } else {
                    editText.setText(textEdit.getUserInput());
                }
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        textEdit.setUserInput(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
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

    public void bindObject(WFormField wFormField) {
        this.wFormField = wFormField;
        viewType = wFormField.getWRecyclerViewType();
        configureViewHolder();
    }

    public WFormField saveData() {
        return wFormField;
    }
}
