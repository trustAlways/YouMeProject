package com.youme.candid.youmeapp.Activity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.youme.candid.youmeapp.Activity.model.CrushRequestBeans;
import com.youme.candid.youmeapp.Activity.model.DateReceiveRequestBeans;
import com.youme.candid.youmeapp.Activity.model.DateSentRequestBeans;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private ArrayList<DateReceiveRequestBeans> date_request;
    Context context;
    SessionManager sessionManager;

    public DateAdapter(FragmentActivity activity, ArrayList<DateReceiveRequestBeans> datebeans) {
        this.date_request = datebeans;
        context = activity;
        sessionManager = new SessionManager(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.datereceive_request_layout, parent, false);

        return new ViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.user_nm.setText(date_request.get(position).getReceived_date__user_nm()+", "+date_request.get(position).getReceived_date__age());

        holder.receive_date_compeatible.setText(date_request.get(position).getReceived_date__relationship_status());

        holder.received_date.setText(date_request.get(position).getReceived_date__date());

        String urls = date_request.get(position).getReceived_date_image();

        System.out.println("imagesss12344 "+urls);

        Glide.with(context).load(urls).into(holder.circleImageView);

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = sessionManager.getData(SessionManager.KEY_USER_ID);
                String status = "accepted";
                System.out.println("status id"+status+id);
                updateRequest(status,id,position);
            }
        });

        holder.btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = sessionManager.getData(SessionManager.KEY_USER_ID);
                String status = "rejected";
                System.out.println("status id"+status+id);
                updateRequest(status,id,position);
            }
        });

        }

    private void updateRequest(final String status, final String id, final int position) {
        //pb.setVisibility(View.VISIBLE);

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_DATE_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");

                    if (Status.equals("success")) {

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        // pb.setVisibility(View.GONE);
                        date_request.remove(position);

                    } else if (Status.equals("error")) {
                        pd.dismiss();
                        //pb.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        // pb.setVisibility(View.GONE);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pb.setVisibility(View.GONE);
                pd.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("receiver_id",id);
                params.put("status",status);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

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

        public TextView user_nm,receive_date_compeatible,received_date;
        CircleImageView circleImageView;
        public CircleImageView btn_accept;
        public CircleImageView btn_reject;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.receive_date_image_icon);
            user_nm = (TextView) itemView.findViewById(R.id.received_user_nm);
            receive_date_compeatible = (TextView) itemView.findViewById(R.id.receive_date_compitable);
            received_date = (TextView) itemView.findViewById(R.id.received_date);
            btn_accept = (CircleImageView) itemView.findViewById(R.id.date_req_accept);
            btn_reject = (CircleImageView) itemView.findViewById(R.id.date_req_reject);

        }
    }
}
