package com.example.luisfm.appbase.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.luisfm.appbase.R;
import com.example.luisfm.appbase.helpers.Utilities;
import com.example.luisfm.appbase.model.Properties;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

/**
 * Created by LUIS  FM on 08/01/2018.
 * Customización EditText
 */

public class LEditText extends EditText {

    private boolean isValid = false;
    private boolean activateFormatCurrency = false;
    private boolean activateSpecialCharacters;
    private int maxLength = 0;
    private int minLength = 0;
    private Animation shake; // = AnimationUtils.loadAnimation(this.getContext(), R.anim.shake);
    private EditText context = this;


    public LEditText(Context context) {
        super(context);
        addEvents();
    }

    public LEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addEvents();
    }

    public LEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addEvents();
    }

    public LEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addEvents();
    }

    //
    private void addEvents(){

        new Handler().postDelayed(new Runnable() { public void run(){

            InputFilter[] arrayFilters = new InputFilter[2];

            arrayFilters[0] = new InputFilter() {
                public CharSequence filter(CharSequence src, int start, int end, Spanned dest, int dstart, int dend) {

                    try{

                        //Aceptar caracteres especiales
                            if(!activateSpecialCharacters){

                                if(activateFormatCurrency)
                                    return src;
                                else{
                                    //Caracteres permitidos [a-zA-Z0-9 ]
                                    if(src.toString().matches("[a-zA-Z0-9 ]+")){ return src; }
                                }

                            }else
                                return src;



                    }catch (Exception ex){

                    }

                    return "";
                }};

            if(activateFormatCurrency){
                maxLength = (maxLength > 12 || maxLength == 0) ? 12 : maxLength;

                if(context.getInputType() == InputType.TYPE_CLASS_NUMBER)
                    maxLength = 9;

            }

            arrayFilters[1] = new InputFilter.LengthFilter(maxLength);

            context.setFilters(arrayFilters);

            context.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    try{

                        isValid = false;

                        if(activateFormatCurrency){

                            context.removeTextChangedListener(this);

                            context.setText(formatCurrency(editable.toString()));

                            context.addTextChangedListener(this);

                            context.setSelection(context.length());
                        }else{
                            if(minLength == 0 && maxLength == 0)
                                isValid = true;
                            else if(minLength > 0 && maxLength > 0){
                                if(context.getText().toString().length() > minLength &&
                                        context.getText().toString().length() == maxLength)
                                    isValid = true;
                            }else if(minLength > 0 && maxLength == 0){
                                if(context.getText().toString().length() > minLength)
                                    isValid = true;
                            }else if(minLength == 0 && maxLength > 0){
                                if(context.getText().toString().length() == maxLength)
                                    isValid = true;
                            }
                        }




                    }catch (Exception ex){

                    }

                }
            });

        }}, 10);

    }

    private String formatCurrency(String dato){

        try{

            dato = dato.replaceAll("\\$","").replaceAll(",","").replaceAll("\\.","");

            float valor = (Float.parseFloat(dato) / 100);

            if(valor > 0)
                isValid = true;

            if(context.getInputType() == InputType.TYPE_CLASS_NUMBER)
                valor = Float.parseFloat(dato);

            String valueRight = (valor + "").split("\\.")[1];
            String valueLeft = (valor + "").split("\\.")[0];

            valueRight = (valueRight.length() == 1) ? valueRight + "0" : valueRight;

            String resultValueLeft = "";

            int count = 0;
            for (int i=valueLeft.length(); i>0; i--){

                resultValueLeft = ((++count == 3 && (i-1) > 0) ? ",": "") +
                        valueLeft.substring(i-1, i) + resultValueLeft;

                count = (count == 3) ? 0 : count;
            }

            String result = "0.0";

            if(context.getInputType() == 8194)
                return  resultValueLeft + "." + valueRight;
            else


            if(context.getInputType() == InputType.TYPE_CLASS_NUMBER)
                return resultValueLeft;
            else
                result = "0";

            return result;

        }catch (Exception ex){
            return "";
        }

    }

    //Indicador de validación donde el texto contenido cumple con el mínimo y máximo de caracteres permitidos.
    public boolean isValid() {
        return isValid;
    }

    //Activar formato de moneda dependiendo el inputType numberDecimal (0,000.00) o number (0,000).
    public void activateFormatCurrency() {
        this.activateFormatCurrency = true;
    }

    //Cantidad máxima de caracteres.
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    //Cantidad minina de caracteres.
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    //Aceptar caracteres especiales.
    public void activateSpecialCharacters() {
        this.activateSpecialCharacters = true;
    }
}
