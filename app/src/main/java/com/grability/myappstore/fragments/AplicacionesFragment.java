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
import com.grability.myappstore.R;
import com.grability.myappstore.adapter.CustomAdapter;
import com.grability.myappstore.app.AppConfig;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.model.category;
import com.grability.myappstore.model.entry;

import java.util.ArrayList;
import java.util.List;

public class AplicacionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "categoryid";

    // TODO: Rename and change types of parameters
    private String categoryid;
    private dbController cont;
    private List<entry> allItems;
    private GridView gridview;
    private CustomAdapter customAdapter;

    // TODO: Rename and change types and number of parameters
    public static AplicacionesFragment newInstance(String param1) {
        AplicacionesFragment fragment = new AplicacionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public AplicacionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryid = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        gridview = (GridView)view.findViewById(R.id.grid);

        cont = new dbController(getActivity());

        allItems = cont.getAllEntry(1, cont.getAllCategory().get(0).getId());

        customAdapter = new CustomAdapter(getActivity(), allItems);
        gridview.setAdapter(customAdapter);
        gridview.requestLayout();
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

    public void filtrarCategoria(category id) {
        // Toast.makeText(getActivity(), "En Fragment Categoria seleccionada "+id, Toast.LENGTH_LONG).show();
        allItems = cont.getAllEntry(1, id.getId());
        customAdapter = new CustomAdapter(getActivity(), allItems);
        gridview.setAdapter(customAdapter);
        gridview.requestLayout();
    }

}
