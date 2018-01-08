package com.example.luisfm.appbase.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.example.luisfm.appbase.R;

import java.util.ArrayList;

/**
 * Created by LUIS  FM on 25/09/2017.
 */

public class CSpinner extends Spinner {

    //region PROPERTIES

    private Animation shake = AnimationUtils.loadAnimation(this.getContext(), R.anim.shake);

    private boolean isValid;

    private ArrayList<ItemSpiner> items;

    //endregion

    //region METHODS CONSTRUCT


    public CSpinner(Context context) {
        super(context);
    }

    public CSpinner(Context context, int mode) {
        super(context, mode);
    }

    public CSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    //endregion

    //region METHODS

    /**
     *
     * @param context
     * @param applyLabel
     * @param items
     * @param itemSelected
     */
    public void setAdpter(final Activity context, final boolean applyLabel, ArrayList<ItemSpiner> items, final OnItemSelectedListener itemSelected){

        this.items = items;

        final ArrayList<String> textItems = new ArrayList<String>();

        for (ItemSpiner item : items)
            textItems.add(item.getTex());

        CArrayAdapter spinnerAdapter = new CArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, textItems, applyLabel);

        this.setAdapter(spinnerAdapter);

        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(applyLabel)
                    if(i == 0 && textItems.size() > 0){

                        CheckedTextView checkedTextView = ((CheckedTextView) adapterView.getChildAt(0));
                        if(checkedTextView != null)
                            ((CheckedTextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor(Properties.TextHintColor));

                        isValid = false;
                    }else{
                        setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Properties.LineColor)));
                        isValid = true;
                    }
                else
                    isValid = true;

                itemSelected.onItemSelected(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                itemSelected.onNothingSelected(adapterView);
            }
        });

        this.setSelection(0);

    }

    /**
     *
     * @return
     */
    public int getItemId() {

        int value = items.get(this.getSelectedItemPosition()).getId();

        return value > 0 ? value : this.getSelectedItemPosition();
    }

    /**
     *
     * @return
     */
    public String getItemText() {

        return items.get(this.getSelectedItemPosition()).getTex();
    }

    public boolean isValid(boolean appliyAnimation) {

        if(appliyAnimation)
            if(!isValid)
                indicateError();

        return isValid;
    }

    private void indicateError(){

        this.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Properties.LineColorError)));

        this.startAnimation(shake);
    }


    //endregion

    //region CLASSES

    public static class ItemSpiner{

        private int Id;

        private String Text;

        public ItemSpiner(){}

        public ItemSpiner(String text){

            this.Text = text;
        }

        public ItemSpiner(int id, String text){

            this.Id = id;

            this.Text = text;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getTex() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

    }

    public class CArrayAdapter extends ArrayAdapter<String> {

        boolean applyLabel;

        public CArrayAdapter(Context context, int resourceId, ArrayList<String> values, boolean applyLabel) {
            super(context, resourceId, values);
            this.applyLabel = applyLabel;
        }
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {

            View view = super.getDropDownView(position, convertView,parent);

            if(applyLabel){
                if(position == 0)
                    ((CheckedTextView) view).setTextColor(Color.parseColor(Properties.TextHintColor));
                else
                    ((CheckedTextView) view).setTextColor(Color.parseColor(Properties.TextColor));
            }

            return view;
        }

    }

    //endregion

}
