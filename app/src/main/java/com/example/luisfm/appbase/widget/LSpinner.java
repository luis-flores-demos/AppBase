package com.example.luisfm.appbase.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.example.luisfm.appbase.model.CSpinner;

/**
 * Created by Chris on 09/01/2018.
 */

public class LSpinner extends Spinner {

    private Spinner context;

    public LSpinner(Context context) {
        super(context);
    }

    public LSpinner(Context context, int mode) {
        super(context, mode);
    }

    public LSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public LSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
    }

    public LSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
    }
}
