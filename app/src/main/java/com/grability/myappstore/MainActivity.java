package com.grability.myappstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.grability.myappstore.fragments.AplicacionesFragment;
import com.grability.myappstore.fragments.CategoriasFragment;
import com.grability.myappstore.model.ItemCategory;

public class MainActivity extends AppCompatActivity implements CategoriasFragment.CategoriasListener  {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*CategoriasFragment frgCategoria
                =(CategoriasFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        frgCategoria.setCategoriasListener(this);*/
        CategoriasFragment fragment = new CategoriasFragment();
        String title = getString(R.string.app_name);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
            fragment.setCategoriasListener(this);
        }

    }

    @Override
    public void onCategorySelected(ItemCategory c) {
        boolean hayDetalle = (getSupportFragmentManager().findFragmentById(R.id.fragment_grid) != null);
        if(hayDetalle) {
            ((AplicacionesFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_grid)).filtrarCategoria(c.getId());
        }
        else {
            Toast.makeText(this, "Category Selected: "+c.getLabel(), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra("id_category", c.getId());
            startActivity(i);
        }
    }

}
