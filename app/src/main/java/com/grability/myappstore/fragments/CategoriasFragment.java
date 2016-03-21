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
import com.grability.myappstore.model.ItemCategory;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoriasFragment extends Fragment {

    private ItemCategory[] datos =
            new ItemCategory[]{
                    new ItemCategory(4001, "Categoria 1", "http:/", "Categoria 1"), new ItemCategory(4002, "Categoria 2", "http:/", "Categoria 2"), new ItemCategory(4003, "Categoria 3", "http:/", "Categoria 3"), new ItemCategory(4004, "Categoria 4", "http:/", "Categoria 4"), new ItemCategory(4005, "Categoria 5", "http:/", "Categoria 5")};
    private ListView lstListado;

    private CategoriasListener listener;

    public CategoriasFragment() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        lstListado = (ListView)getView().findViewById(R.id.LstCategorias);
        lstListado.setAdapter(new AdaptadorCategory(this));
        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if (listener != null) {
                    listener.onCategorySelected(
                            (ItemCategory) lstListado.getAdapter().getItem(pos));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public interface CategoriasListener {
        void onCategorySelected(ItemCategory c);
    }

    public void setCategoriasListener(CategoriasListener listener) {
        this.listener=listener;
    }

    class AdaptadorCategory extends ArrayAdapter<ItemCategory> { Activity context;
        AdaptadorCategory(Fragment context) {
            super(context.getActivity(), R.layout.listitem_category, datos);
            this.context = context.getActivity();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_category, null);
            TextView label = (TextView)item.findViewById(R.id.label);
            label.setText(datos[position].getLabel());

            return(item);
        }
    }
}
