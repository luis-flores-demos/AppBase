package com.example.luisfm.appbase.model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.luisfm.appbase.R;
import com.example.luisfm.appbase.helpers.Utilities;

import java.text.NumberFormat;

/**
 * Created by LUIS  FM on 25/09/2017.
 */

public class CEditText extends EditText {

    //region PROPERTIES

    private static String TAG_DEBUG = "CEditText";

    private EditText context = this;

    private Animation shake = AnimationUtils.loadAnimation(this.getContext(), R.anim.shake);

    private String beforeText = "";

    private boolean isValid;

    //endregion

    //region METHODS CONSTRUCT

    public CEditText(Context context) {
        super(context);
    }

    public CEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //endregion

    //region METHODS

    public void setCurrencyFormat(final String symbol, final double valueMin, final double valueMax){

        context.clearFocus();

        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeText = context.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                double valueParsed = 0.00;

                double beforeValue = 0.00;


                context.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Properties.LineColor)));

                context.removeTextChangedListener(this);

                context.setText(symbol.trim() + Utilities.paserCurrency(editable.toString()));

                try {

                    valueParsed = Double.parseDouble(context.getText().toString().replaceAll("\\$", "").replaceAll(",", ""));

                    if(beforeText.length() > 0)
                        beforeValue = Double.parseDouble(beforeText.toString().replaceAll("\\$", "").replaceAll(",", ""));

                } catch (NumberFormatException e) {
                    valueParsed = 0.00;
                }

                if(valueParsed > valueMax){

                    context.setText(symbol.trim() + Utilities.paserCurrency(beforeText));

                    valueParsed = beforeValue;
                }

                if (valueParsed <= 0)
                    setText("");

                if(valueParsed >= valueMin && valueParsed <= valueMax)
                    isValid = true;
                else
                    isValid = false;

                context.addTextChangedListener(this);

                //texto.setText(editable.toString().replaceAll("[^a-zA-Z0-9 ]+", ""));

                context.setSelection(context.length());

            }
        });

        //if(applyCurrencyFormat)
            //this.setText("$0.00");

        //if(lengthRequired > 0)
            //this.setFilters(new InputFilter[] { new InputFilter.LengthFilter(lengthRequired) });

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

}
