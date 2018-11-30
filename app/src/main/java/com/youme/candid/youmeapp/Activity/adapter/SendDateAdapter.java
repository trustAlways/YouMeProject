package com.youme.candid.youmeapp.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youme.candid.youmeapp.Activity.activity.Fullview_User_Profile;
import com.youme.candid.youmeapp.Activity.model.DateSentRequestBeans;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SendDateAdapter extends RecyclerView.Adapter<SendDateAdapter.ViewHolder> {

    private ArrayList<DateSentRequestBeans> date_request;
    Context context;
    SessionManager sessionManager;

    public SendDateAdapter(FragmentActivity activity, ArrayList<DateSentRequestBeans> datebeans) {
        this.date_request = datebeans;
        context = activity;
        sessionManager  = new SessionManager(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.datesend_request_layout, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.user_nm.setText(date_request.get(position).getSend_date__user_nm()+", "+date_request.get(position).getSend_date__age());

        if (!date_request.get(position).getSend_date__relationship_status().equalsIgnoreCase("null"))
        {
            holder.compatibility.setText(date_request.get(position).getSend_date__relationship_status());
        }

        holder.date_status.setText(date_request.get(position).getSend_date__distance());
        holder.datt_date.setText(date_request.get(position).getSend_date__date());

        String urls = date_request.get(position).getSend_date_image();
        System.out.println("imagesss12344 " + urls);

        Glide.with(context).load(urls).into(holder.circleImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.setData(SessionManager.KEY_ANOTHER_USER_ID,date_request.get(position).getSend_date_user_id());
                Intent intent = new Intent(context,Fullview_User_Profile.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return date_request.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView user_nm, compatibility, date_status, datt_date;
        CircleImageView circleImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.send_date_image_icon);
            user_nm = (TextView) itemView.findViewById(R.id.send_date_user_nm);
            compatibility = (TextView) itemView.findViewById(R.id.send_date_compitable);
            date_status = (TextView) itemView.findViewById(R.id.date_send_status);
            datt_date = (TextView) itemView.findViewById(R.id.send_date_date);
        }
    }
}
