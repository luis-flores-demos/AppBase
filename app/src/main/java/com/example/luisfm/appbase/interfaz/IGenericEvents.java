package com.example.luisfm.appbase.interfaz;

import android.content.Context;

/**
 * Created by LUIS  FM on 11/09/2017.
 */

public interface IGenericEvents {

    public Object doInBackground();

    public void onPostExecute(Object result);

    public void onPreExecute();

    public void onProgressUpdate();

    public void onPreExecuteError();

}
