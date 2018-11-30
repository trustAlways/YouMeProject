package com.youme.candid.youmeapp.Activity.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.youme.candid.youmeapp.Activity.activity.Fullview_User_Profile;
import com.youme.candid.youmeapp.Activity.activity.User_Dates_Activity;
import com.youme.candid.youmeapp.Activity.model.CrushRequestBeans;
import com.youme.candid.youmeapp.Activity.model.Usergalleryimages;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrushAdapter extends RecyclerView.Adapter<CrushAdapter.ViewHolder> {

    private ArrayList<CrushRequestBeans> crush_request;
    Context context;
    SessionManager sessionManager;
    public CrushAdapter(FragmentActivity activity, ArrayList<CrushRequestBeans> crushRequestBeans)
    {
        this.crush_request = crushRequestBeans;
        context = activity;
        sessionManager = new SessionManager(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.send_crush_request_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.user_nm.setText(crush_request.get(position).getSend_crush__user_nm()+", "+crush_request.get(position).getSend_crush__age());
        if (!crush_request.get(position).getSend_crush_compatible().equalsIgnoreCase("null"))
        {
            holder.compatible.setText(crush_request.get(position).getSend_crush_compatible()+"% Compatible");
        }
        holder.datt_date.setText(crush_request.get(position).getSend_crush__date());
        holder.stts.setText(crush_request.get(position).getSend_crush_status());

        if (crush_request.get(position).getSend_crush_status().equalsIgnoreCase("accepted"))
        {
            holder.btn_send_date_req.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.btn_send_date_req.setVisibility(View.GONE);
        }

        String urls = crush_request.get(position).getSend_crush_image();
        System.out.println("imagesss12344 "+urls);
        Glide.with(context).load(urls).into(holder.circleImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.setData(SessionManager.KEY_ANOTHER_USER_ID,crush_request.get(position).getSend_crush_user_id());
                Intent intent = new Intent(context,Fullview_User_Profile.class);
                context.startActivity(intent);
            }
        });

        holder.btn_send_date_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setData(SessionManager.KEY_ANOTHER_USER_ID,crush_request.get(position).getSend_crush_user_id());
                send_Date_Request(crush_request.get(position).getSend_crush_user_id());
            }
        });

    }

    private void send_Date_Request(String send_crush_user_id)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        final String user_id = sessionManager.getData(SessionManager.KEY_USER_ID);
        final String id = send_crush_user_id;

        System.out.println(" another user req "+ id);
        System.out.println(" user id "+ user_id);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_DATE_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            progressDialog.dismiss();

                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("response..!","1235"+response);

                            String Status = jsonObject.getString("status");
                            String Status_code = jsonObject.getString("status_code");
                            String msg = jsonObject.getString("message");

                            if (Status.equals("success"))
                            {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, User_Dates_Activity.class);
                                context.startActivity(intent);
                            }
                            else if (Status.equals("error"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                System.out.println("Error occured "+error.getMessage());
                Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sender_id",user_id);
                if (id!=null)
                {
                    params.put("receiver_id",id);
                    params.put("status","pending");
                }

                System.out.println(" another user req "+ id);
                System.out.println(" user id "+ user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


    @Override
    public int getItemCount() {
        return crush_request.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView user_nm,compatible,stts,datt_date;
        CircleImageView circleImageView;
        public Button btn_send_date_req;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.send_crush_image_icon);
            user_nm = (TextView) itemView.findViewById(R.id.send_crush_user_nm);
            compatible = (TextView) itemView.findViewById(R.id.rsend_crush_compeatable);
            datt_date = (TextView) itemView.findViewById(R.id.send_crush_date);
            stts = (TextView) itemView.findViewById(R.id.send_crush_status);
            btn_send_date_req = (Button)itemView.findViewById(R.id.send_date_request);
        }
    }
}
