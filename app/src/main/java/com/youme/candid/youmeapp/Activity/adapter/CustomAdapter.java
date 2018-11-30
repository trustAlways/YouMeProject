package com.youme.candid.youmeapp.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youme.candid.youmeapp.Activity.model.Usergalleryimages;
import com.youme.candid.youmeapp.R;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Usergalleryimages> search_item;
    public Context context;

    public CustomAdapter(Context c, ArrayList<Usergalleryimages> image_list) {
        this.search_item = image_list;
         context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String urls = search_item.get(position).getImages();

        System.out.println("imagesss12344 "+urls);

        Glide.with(context).load(urls).into(holder.img_view);

        //holder.img_view.setOnTouchListener(new ImageMatrixTouchHandler(context));
        // holder.img_view.setOnTouchListener(this);
    }



    @Override
    public int getItemCount() {
        return search_item.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_view;

        public ViewHolder(View itemView) {
            super(itemView);

            img_view = (ImageView) itemView.findViewById(R.id.tv_versionname);
        }
    }

}
