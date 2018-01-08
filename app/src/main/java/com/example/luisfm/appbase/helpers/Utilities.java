package com.example.luisfm.appbase.helpers;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.luisfm.appbase.R;
import com.example.luisfm.appbase.activity.MainActivity;
import com.example.luisfm.appbase.interfaz.IAlertConfiguration;
import com.example.luisfm.appbase.interfaz.IGenericEvents;


import static android.support.v7.app.AlertDialog.*;

/**
 * Created by LUIS  FM on 11/09/2017.
 */

public class Utilities {

    /**
     * Cerrar teclado en el campo de texto especificado.
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {

        try {
            if (view != null && context != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } catch (Exception ex) {
                ex.getStackTrace();
        }

    }

    /**
     * Da formato de moneda al valor especificado.
     * @param dato
     * @return $0,000.00
     */
    public static String paserCurrency(String dato){

        try{

            dato = dato.replaceAll("\\$","").replaceAll(",","").replaceAll("\\.", "");

            double valor = (Double.parseDouble(dato) / 100) ;

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

            return  resultValueLeft + "." + valueRight;

        }catch (Exception ex){
            return "";
        }

    }

    /**
     * Ejecutar tareas largas como peticiones al servidor.
     * @param iGenericEvents
     */
    public static void longOperation(final IGenericEvents iGenericEvents){

        new LongTask(iGenericEvents).execute();

    }

    public static boolean hasFingerPrint(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED &&
                    context.getSystemService(FingerprintManager.class).isHardwareDetected();
        } else {
            return FingerprintManagerCompat.from(context).isHardwareDetected();
        }
    }

    //region NOTIFICATIONS

    /**
     *
     * @param activity
     * @param message
     */
    public static void launchAlert(final Activity activity, String title, String message,
                                   boolean isConfirmation, String textBtnAccept, String textBtnCancel,
                                   boolean blockScreen, final IAlertConfiguration iAlertConfiguration) {

        Builder dialogBuilder = new Builder(activity);

        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.layout_alert_dialog, null);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        Button btnAcept = (Button) dialogView.findViewById(R.id.btn_acept);

        if(textBtnAccept != null && textBtnAccept.trim().length() > 0)
            btnAcept.setText(textBtnAccept);

        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iAlertConfiguration.toAccept();
                Utilities.reverseRevealEffect(dialogView, alertDialog);
            }
        });

        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);

        if(isConfirmation){

            if(textBtnCancel != null && textBtnCancel.trim().length() > 0)
                btnAcept.setText(textBtnCancel);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iAlertConfiguration.toCancel();
                    Utilities.reverseRevealEffect(dialogView, alertDialog);
                }
            });
        }else{

            dialogView.findViewById(R.id.line_confirm).setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }

        TextView text_title = (TextView)  dialogView.findViewById(R.id.txt_title);
        text_title.setText(Html.fromHtml(title));

        TextView text_message = (TextView)  dialogView.findViewById(R.id.txt_message);
        text_message.setText(Html.fromHtml(message));

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(!blockScreen);
        alertDialog.show();

        dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Utilities.revealEffect(v);
            }
        });

    }

    public static  void launchNotification(final Context context){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setVibrate(new long[] { 1000, 1000})
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setContentTitle("Mensaje de Alerta")
                        .setContentText("Ejemplo de notificación.")
                        .setContentInfo("4")
                        .setTicker("Alerta!");

        Intent notIntent = new Intent(context, MainActivity.class);

        PendingIntent contIntent = PendingIntent.getActivity(context, 0, notIntent, 0);

        mBuilder.setContentIntent(contIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

    }

    //endregion

    //region ANIMATIONS

    /**
     *
     * @param v
     */
    public static void revealEffect(View v) {
        v.setVisibility(View.GONE);
        // get the center for the clipping circle
        int cx = (v.getLeft() + v.getRight()) / 2;
        int cy = (v.getTop() + v.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(v.getWidth(), v.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        v.setVisibility(View.VISIBLE);
        anim.start();
    }

    /**
     *
     * @param v
     */
    public static void reverseRevealEffect(final View v, final AlertDialog dialog) {
        // get the center for the clipping circle
        int cx = (v.getLeft() + v.getRight()) / 2;
        int cy = (v.getTop() + v.getBottom()) / 2;

        // get the initial radius for the clipping circle
        int initialRadius = v.getWidth();

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done and dismiss the AlertDialog
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        // start the animation
        anim.start();
    }

    //endregion

    //region CLASSES

    /**
     * Clase para realizar las tareas largas.
     */
    private static class LongTask extends AsyncTask<Void, Void, Object> {

        private IGenericEvents iGenericEvents;

        public LongTask(IGenericEvents iGenericEvents){

            this.iGenericEvents = iGenericEvents;
        }

        @Override
        protected Object doInBackground(Void... voids) {

            return this.iGenericEvents.doInBackground();
        }

        @Override
        protected void onPostExecute(Object object) {

            this.iGenericEvents.onPostExecute(object);
        }

        @Override
        protected void onPreExecute() {

            this.iGenericEvents.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            this.iGenericEvents.onProgressUpdate();
        }

    }

    //endregion

}
