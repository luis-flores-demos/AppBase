package com.example.luisfm.appbase.model;

import android.app.Activity;

/**
 * Created by LUIS  FM on 02/10/2017.
 */

public class Singleton {

    private static final Singleton instance = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {

        return instance;
    }

    private class Session{

        private Activity activity;

        public Session(Activity activity){

            this.activity = activity;
        }
    }


}
