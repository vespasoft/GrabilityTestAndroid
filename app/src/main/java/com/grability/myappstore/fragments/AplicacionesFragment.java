package com.grability.myappstore.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.grability.myappstore.DetailActivity;
import com.grability.myappstore.DetalleActivity;
import com.grability.myappstore.R;
import com.grability.myappstore.adapter.CustomAdapter;
import com.grability.myappstore.model.ItemObject;

import java.util.ArrayList;
import java.util.List;

public class AplicacionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        GridView gridview = (GridView)view.findViewById(R.id.grid);

        List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), allItems);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), DetailActivity.class);
                getActivity().startActivity(i);
            }
        });

        return view;
    }

    public void filtrarCategoria(int id) {
        Toast.makeText(getActivity(), "En Fragment Categoria seleccionada "+id, Toast.LENGTH_LONG).show();
    }

    private List<ItemObject> getAllItemObject(){
        List<ItemObject> items = new ArrayList<>();
        items.add(new ItemObject(R.mipmap.app001,"Dip It Low", "Christina Milian"));
        items.add(new ItemObject(R.mipmap.app002,"Someone like you", "Adele Adkins"));
        items.add(new ItemObject(R.mipmap.app003,"Ride", "Ciara"));
        items.add(new ItemObject(R.mipmap.ic_launcher,"Paparazzi", "Lady Gaga"));
        items.add(new ItemObject(R.mipmap.ic_launcher,"Forever", "Chris Brown"));
        items.add(new ItemObject(R.mipmap.ic_launcher,"Stay", "Rihanna"));
        items.add(new ItemObject(R.mipmap.app001,"Marry me", "Jason Derulo"));
        items.add(new ItemObject(R.mipmap.app002,"Waka Waka", "Shakira"));
        items.add(new ItemObject(R.mipmap.app003,"Dark Horse", "Katy Perry"));
        items.add(new ItemObject(R.mipmap.ic_launcher,"Dip It Low", "Christina Milian"));
        items.add(new ItemObject(R.mipmap.ic_launcher, "Dip It Low", "Christina Milian"));
        items.add(new ItemObject(R.mipmap.ic_launcher, "Dip It Low", "Christina Milian"));
        return items;
    }


}
