package com.grability.myappstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.fragments.AplicacionesFragment;
import com.grability.myappstore.fragments.CategoriasFragment;
import com.grability.myappstore.model.category;

public class MainActivity extends AppCompatActivity implements CategoriasFragment.CategoriasListener  {

    private Toolbar mToolbar;
    private  boolean hayDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbController cont = new dbController(this);

        hayDetalle = (getSupportFragmentManager().findFragmentById(R.id.fragment_grid) != null);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CategoriasFragment fragment = new CategoriasFragment();
        String title = getString(R.string.app_name);
        // set the toolbar title
        getSupportActionBar().setTitle(title);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
           fragment.setCategoriasListener(this);
        }
        if (hayDetalle) {
            onCategorySelected(cont.getAllCategory().get(0));
        }

    }

    @Override
    public void onCategorySelected(category c) {
        if(hayDetalle) {
            ((AplicacionesFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_grid)).filtrarCategoria(c);
        }
        else {
            Toast.makeText(this, "Category Selected: "+c.getLabel(), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra("id_category", c.getId());
            startActivity(i);
            overridePendingTransition(R.anim.left_to_right, R.anim.left_to_right);
        }
    }

}
