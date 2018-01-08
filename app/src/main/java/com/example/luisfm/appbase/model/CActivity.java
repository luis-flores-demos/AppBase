package com.example.luisfm.appbase.model;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.luisfm.appbase.R;

/**
 * Created by LUIS  FM on 15/10/2017.
 */

public class CActivity extends Activity{

    protected Activity context = this;

    private static String TAG_DEBUG = "CActivity";

    public void blockScreen(boolean status){

        View loading = findViewById(R.id.layout_loading);

        TextView txt_block_creen = findViewById(R.id.txt_block_creen);

        if(status)
            loading.setVisibility(View.VISIBLE);
        else
            loading.setVisibility(View.GONE);
    }

}
