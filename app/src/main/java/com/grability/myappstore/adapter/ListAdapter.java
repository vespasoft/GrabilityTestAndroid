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
import android.widget.Toast;

import com.grability.myappstore.DetailActivity;
import com.grability.myappstore.R;
import com.grability.myappstore.model.ItemObject;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    // Animation
    Animation animFadein;

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Activity context;

    public ListAdapter(Activity context, List<ItemObject> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
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
            convertView = layoutinflater.inflate(R.layout.row_list_item, parent, false);
            listViewHolder.card_view = (CardView)convertView.findViewById(R.id.card_view);
            listViewHolder.screenShot = (ImageView)convertView.findViewById(R.id.screen_shot);
            listViewHolder.musicName = (TextView)convertView.findViewById(R.id.music_name);
            listViewHolder.musicAuthor = (TextView)convertView.findViewById(R.id.music_author);

            // load the animation
            animFadein = AnimationUtils.loadAnimation(context,
                    R.anim.slide_down);
            // start the animation
            listViewHolder.card_view.startAnimation(animFadein);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.screenShot.setImageResource(listStorage.get(position).getScreenShot());
        listViewHolder.musicName.setText(listStorage.get(position).getMusicName());
        listViewHolder.musicAuthor.setText(listStorage.get(position).getMusicAuthor());
        listViewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                context.startActivity(i);
                context.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        ImageView screenShot;
        TextView musicName;
        TextView musicAuthor;
        CardView card_view;
    }
}
