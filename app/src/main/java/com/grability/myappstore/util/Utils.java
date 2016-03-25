package com.grability.myappstore.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Usuario on 23/03/2016.
 */
public class Utils {

    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    // Mostrar TOAST
    public static void DisplaySnackbarMessage(View v, String Mensaje, String Tiempo){
        Snackbar snackbar = Snackbar.make(v,
                Mensaje, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void DisplayToastMessage(Context context, String Mensaje, String Tiempo){
        Toast tx = null;

        if(Tiempo.equals("L"))
            tx = Toast.makeText(context, Mensaje, Toast.LENGTH_LONG);
        else
            tx = Toast.makeText(context, Mensaje, Toast.LENGTH_SHORT);

        tx.show();
    }

    public static boolean getIsInternetConnection(Context ctx, View v) {
        boolean isinternet = isConnectingToInternet(ctx);
        if (!isinternet) {
            // Prompt user to enter credentials
            Snackbar snackbar = Snackbar.make(v,
                    "Veifique que su dispositivo tenga conexión a Internet o a una red WIFI!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        return isinternet;
    }

    // Definir titulo y tipo de letra
    public static void SetTypeFaceTitle(Context context,String title, TextView control, int size){
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoBold.ttf");
        control.setTypeface(face);
        control.setText(title);
        control.setTextSize(size);
    }

    public static void SetTypeFaceTitle(Context context,String title, TextView control, int size, String fuente){
        Typeface face = Typeface.createFromAsset(context.getAssets(), fuente);
        control.setTypeface(face);
        control.setText(title);
        control.setTextSize(size);
    }

    public static void launchExplorerURL(Activity context, String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    public static void SetDatoPreferenciasInt(Context context, String Campo, int Valor){ // 0 -Landscape 1-PORTRAIT
        SharedPreferences settings = context.getSharedPreferences("myappstore_preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Campo, Valor);

        editor.commit();
    }

    public static int GetDatoPreferenciasInt(Context context, String Campo) {
        SharedPreferences settings = context.getSharedPreferences("myappstore_orientacion", Context.MODE_PRIVATE);
        int dato = settings.getInt(Campo, 0);

        return dato;
    }
}
