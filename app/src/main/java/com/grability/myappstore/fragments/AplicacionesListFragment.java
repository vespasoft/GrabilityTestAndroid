package com.grability.myappstore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.grability.myappstore.R;
import com.grability.myappstore.adapter.ListAdapter;
import com.grability.myappstore.model.ItemObject;

import java.util.ArrayList;
import java.util.List;

public class AplicacionesListFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listview = (ListView)view.findViewById(R.id.list);

        List<ItemObject> allItems = getAllItemObject();
        ListAdapter listAdapter = new ListAdapter(getActivity(), allItems);
        listview.setAdapter(listAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void filtratCategoria(int id) {
        Toast.makeText(getActivity(), "Categoria seleccionada "+id, Toast.LENGTH_LONG).show();
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
