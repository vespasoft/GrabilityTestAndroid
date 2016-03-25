package com.grability.myappstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.grability.myappstore.DetailActivity;
import com.grability.myappstore.R;
import com.grability.myappstore.app.AppController;
import com.grability.myappstore.controller.dbController;
import com.grability.myappstore.model.entry;
import com.grability.myappstore.model.image;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    // Animation
    Animation animFadein;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private LayoutInflater layoutinflater;
    private List<entry> listStorage;
    private Activity context;
    private dbController cont;

    public CustomAdapter(Activity context, List<entry> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        cont = new dbController(context);
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.row_app_item, parent, false);
            listViewHolder.card_view = (CardView)convertView.findViewById(R.id.card_view);
            listViewHolder.profilePic = (NetworkImageView) convertView.findViewById(R.id.picture);
            listViewHolder.txtName = (TextView)convertView.findViewById(R.id.txtName);
            listViewHolder.txtArtist = (TextView)convertView.findViewById(R.id.txtArtist);
            // load the animation
            animFadein = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
            // start the animation
            listViewHolder.card_view.startAnimation(animFadein);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.images = cont.getAllImagebyEntry(listStorage.get(position).getId());

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        if (listViewHolder.images.size()>0) {
            listViewHolder.profilePic.setImageUrl(listViewHolder.images.get(1).getLabel(),  imageLoader);
        }
        // load the animation
        animFadein = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        // start the animation
        listViewHolder.profilePic.startAnimation(animFadein);

        listViewHolder.txtName.setText(listStorage.get(position).getImname());
        listViewHolder.txtArtist.setText(listStorage.get(position).getArtist());
        final entry dato = listStorage.get(position);
        listViewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("entry", dato);
                context.startActivity(i);
                //context.overridePendingTransition(R.anim.left_to_right, R.anim.left_to_right);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        NetworkImageView profilePic;
        TextView txtName;
        TextView txtArtist;
        CardView card_view;
        List<image> images;

    }
}
