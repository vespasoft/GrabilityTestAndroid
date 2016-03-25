package com.grability.myappstore.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grability.myappstore.R;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.model.category;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoriasFragment extends Fragment {

    private List<category> datos;
    private ListView lstListado;

    private CategoriasListener listener;

    public CategoriasFragment() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        dbController cont = new dbController(getActivity());
        datos = cont.getAllCategory();
        cont.getCountEntry();

        lstListado = (ListView)getView().findViewById(R.id.LstCategorias);
        lstListado.setAdapter(new AdaptadorCategory(this));
        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if (listener != null) {
                    listener.onCategorySelected(
                            (category) lstListado.getAdapter().getItem(pos));
                }
            }
        });
        lstListado.setSelection(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public interface CategoriasListener {
        void onCategorySelected(category c);
    }

    public void setCategoriasListener(CategoriasListener listener) {
        this.listener=listener;
    }

    class AdaptadorCategory extends ArrayAdapter<category> { Activity context;
        AdaptadorCategory(Fragment context) {
            super(context.getActivity(), R.layout.listitem_category, datos);
            this.context = context.getActivity();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_category, null);
            TextView label = (TextView)item.findViewById(R.id.label);
            label.setText(datos.get(position).getLabel());

            return(item);
        }
    }
}
