package com.grability.myappstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.grability.myappstore.app.AppConfig;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.fragments.AplicacionesListFragment;
import com.grability.myappstore.model.category;

import java.util.ArrayList;
import java.util.List;

public class DetalleActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private dbController cont;
    private List<category> datos;
    private String idcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        cont = new dbController(this);
        datos = cont.getAllCategory();

        Intent intent = getIntent();
        idcategory =  intent.getStringExtra("id_category");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        // Carga la lista de las categorias desde la BD SQLite
        int itemSelected=0;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i=0; i < datos.size(); i++) {
            adapter.addFragment(AplicacionesListFragment.newInstance(datos.get(i).getId()), datos.get(i).getLabel());
            if (datos.get(i).getId().equalsIgnoreCase(idcategory)) {
                itemSelected = i;
            }
        }
        viewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(itemSelected);
        mViewPager.requestLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.left_to_right);
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
