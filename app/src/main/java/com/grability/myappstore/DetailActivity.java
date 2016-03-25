package com.grability.myappstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.grability.myappstore.app.AppController;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.model.category;
import com.grability.myappstore.model.entry;
import com.grability.myappstore.model.image;
import com.grability.myappstore.util.Utils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = DetailActivity.class.getName();

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context context;

    // Animation
    Animation animLefttoright;
    Animation animRighttoleft;
    Animation animSlidedown;

    // Views
    private CardView cardProfile;
    private CardView cardDescription;
    private CardView cardSummary;
    private NetworkImageView profilePic;
    private TextView txtName;
    private TextView txtArtist;
    private TextView txtPrice;
    private TextView txtType;
    private TextView txtCategory;
    private TextView txtId;
    private TextView txtBundleid;
    private TextView txtReleaseDate;
    private TextView txtSummary;
    private Button btnDownload;

    private dbController cont;
    private entry datos;
    private category datoCategory;
    private List<image> datoImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = DetailActivity.this;
        cont = new dbController(DetailActivity.this);

        cardProfile = (CardView)findViewById(R.id.cardProfile);
        cardDescription = (CardView)findViewById(R.id.cardDescription);
        cardSummary = (CardView)findViewById(R.id.cardSummary);
        profilePic = (NetworkImageView) findViewById(R.id.picture);
        txtName = (TextView)findViewById(R.id.txtName);
        txtArtist = (TextView)findViewById(R.id.txtArtist);
        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtType = (TextView)findViewById(R.id.txtType);
        txtCategory = (TextView)findViewById(R.id.txtCategory);
        txtId = (TextView)findViewById(R.id.txtId);
        txtBundleid = (TextView)findViewById(R.id.txtBundleid);
        txtReleaseDate = (TextView)findViewById(R.id.txtReleaseDate);
        txtSummary = (TextView)findViewById(R.id.txtSummary);
        btnDownload = (Button)findViewById(R.id.btnDownload);

        Intent intent = getIntent();
        datos = (entry) intent.getSerializableExtra("entry");
        if (datos!=null) {
            datoCategory = cont.getCategory(datos.getCategoryid());
            txtName.setText(datos.getImname());
            txtArtist.setText(datos.getArtist());
            txtPrice.setText("Price: "+ String.valueOf(datos.getAmount())+" "+datos.getCurrency() );
            txtType.setText(datos.getContenttype());
            if (datoCategory!=null) {
                txtCategory.setText("Category: "+ datoCategory.getLabel());
            }
            txtId.setText(datos.getId());
            txtBundleid.setText(datos.getBundleId());
            txtReleaseDate.setText(datos.getReleaseDate());
            txtSummary.setText(datos.getLabel());
            datoImages = cont.getAllImagebyEntry(datos.getId());
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            if (datoImages.size()>0) {
                profilePic.setImageUrl(datoImages.get(2).getLabel(),
                        imageLoader);
            }

        }
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datos!=null) {
                    if ( Utils.isConnectingToInternet(DetailActivity.this)) {
                        Utils.launchExplorerURL(DetailActivity.this, datos.getLinl_href());
                    } else {
                        Utils.DisplaySnackbarMessage(v, getString(R.string.error_mensaje_internet), "L" );
                    }
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load the animation Profile CardView
        animRighttoleft = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        animLefttoright = AnimationUtils.loadAnimation(context, R.anim.left_to_right);
        animSlidedown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

        cardProfile.startAnimation(animRighttoleft); // start the animation
        cardDescription.startAnimation(animLefttoright); // start the animation
        cardSummary.startAnimation(animSlidedown); // start the animation
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                overridePendingTransition(R.anim.left_to_right, R.anim.left_to_right);
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

}
