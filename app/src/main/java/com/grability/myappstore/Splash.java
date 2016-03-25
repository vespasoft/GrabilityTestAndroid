package com.grability.myappstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.grability.myappstore.app.AppConfig;
import com.grability.myappstore.app.AppController;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.parser.getGsonParser;
import com.grability.myappstore.util.Loader;
import com.grability.myappstore.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;


public class Splash extends AppCompatActivity {

    dbController cont;
    static int progress;
    ProgressBar progressBar;
    int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cont = new dbController(this);
        String title = getString(R.string.app_name);

        Loader mLoader = (Loader) findViewById(R.id.imageview_animation_list_filling);

        TextView txtLogo = (TextView)findViewById(R.id.txtLogo);
        Utils.SetTypeFaceTitle(Splash.this, title, txtLogo, 36);

        Animation animFadein = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        txtLogo.startAnimation(animFadein); // start the animation

        callToServer();
    }

    public void callToServer() {
        if ( Utils.isConnectingToInternet(this)) {
            // Este metodo consume el json y lo almacena en la base de datos SQLite
            getTopFreeAppsURL();
        } else {
            if (cont.getAllCategory().size()>0) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Utils.DisplayToastMessage(Splash.this, getString(R.string.error_offline_conexion), "L");
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }, 3000);
            } else {
                createDialogErrorConnection(this, getString(R.string.error_title_internet), getString(R.string.error_mensaje_internet));
                // Utils.DisplaySnackbarMessage(v, getString(R.string.error_mensaje_internet), "L");
            }
        }
    }

    /**
     * Crea un dialogo con personalizado para comportarse
     * como formulario de registro
     *
     * @return Dialogo
     */
    public void createDialogErrorConnection(Context context,String Titulo, String Mensaje) {
        //On instancie notre layout en tant que View
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        alertDialog.setTitle(Titulo);
        alertDialog.setMessage(Mensaje) ;
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callToServer();
            }
        });
        //On cree un bouton Annuler a notre AlertDialog et on lui affecte un evenement
        alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        alertDialog.show();
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    public void getTopFreeAppsURL() {
        // Tag used to cancel the request
        final String tag_string_req = "req_register";

        Log.d(tag_string_req, "Json request: " + AppConfig.URL_TOP_FREEAPPS);

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_TOP_FREEAPPS , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(tag_string_req, "Customer Response: " + response.toString());
                // Objeto
                getGsonParser parser = new getGsonParser();
                if ( parser.getFlujoJson(Splash.this, response) ) {
                    Toast.makeText(getApplicationContext(), "Datos Actualizados...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.bounce, R.anim.fade_out);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. Intente conectarse mas tarde...", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(tag_string_req, "AppStore Error: " + error.getMessage());
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Error. Intente conectarse mas tarde...", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
