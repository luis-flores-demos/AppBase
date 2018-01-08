package com.example.luisfm.appbase.model;

import com.google.gson.Gson;

/**
 * Created by LUIS  FM on 11/09/2017.
 */

public class ModeloBase {

    //region TOSTRING

    @Override
    public String toString(){

        Gson json = new Gson();

        return  json.toJson(this, getClass());
    }

    //endregion

}
