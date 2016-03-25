package com.grability.myappstore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.grability.myappstore.R;
import com.grability.myappstore.adapter.ListAdapter;
import com.grability.myappstore.app.AppConfig;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.model.entry;

import java.util.ArrayList;
import java.util.List;

public class AplicacionesListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "categoryid";

    // TODO: Rename and change types of parameters
    private String categoryid;
    private ListView listview;
    private List<entry> allItems;
    private ListAdapter listAdapter;

    // TODO: Rename and change types and number of parameters
    public static AplicacionesListFragment newInstance(String param1) {
        AplicacionesListFragment fragment = new AplicacionesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public AplicacionesListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listview = (ListView)view.findViewById(R.id.list);
        dbController cont = new dbController(getActivity());
        allItems = cont.getAllEntry(1, categoryid);

        listAdapter = new ListAdapter(getActivity(), allItems);
        listview.setAdapter(listAdapter);

        return view;
    }

    public void filtratCategoria(String id) {
        Toast.makeText(getActivity(), "Categoria seleccionada "+id, Toast.LENGTH_LONG).show();
        dbController cont = new dbController(getActivity());

        allItems = cont.getAllEntry(1, id);
        listAdapter = new ListAdapter(getActivity(), allItems);
        listview.setAdapter(listAdapter);
    }


}
