package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.youme.candid.youmeapp.Activity.adapter.CrushAdapter;
import com.youme.candid.youmeapp.Activity.adapter.CrushReceived;
import com.youme.candid.youmeapp.Activity.model.CrushReceivedBeans;
import com.youme.candid.youmeapp.Activity.model.CrushRequestBeans;
import com.youme.candid.youmeapp.Activity.utils.ConnectionDetector;
import com.youme.candid.youmeapp.Activity.utils.SessionManager;
import com.youme.candid.youmeapp.Activity.utils.URLs;
import com.youme.candid.youmeapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceivedCrushRequest extends Fragment {

    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ProgressDialog pd ;
    RecyclerView recyclerView;
    ImageView img_no_data;

    LinearLayout linear_old,linear__new,linear_cnfrm;
    Context ctx;
    CrushReceived crushReceived;
    String idd;
    ArrayList<CrushReceivedBeans> crushReceivedBeans;
    View v;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crushrequest,null);

        Initialize();
        //clickListner();

        boolean internet = connectionDetector.isConnected(ctx);
        if (internet)
        {
            setTvAdapter();
        }
        else
        {
            img_no_data.setVisibility(View.VISIBLE);
            Toast.makeText(ctx, "Check your internet connection.", Toast.LENGTH_SHORT).show();
        }
        return v;
    }
    private void Initialize() {
        ctx = getActivity();
        sessionManager = new SessionManager(getActivity());
        connectionDetector = new ConnectionDetector();
        img_no_data = (ImageView)v.findViewById(R.id.nocrushsent);

        pd = new ProgressDialog(getActivity());
        crushReceivedBeans = new ArrayList<>();
        recyclerView = (RecyclerView)v.findViewById(R.id.crush_request_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setTvAdapter()
    {
        //pb.setVisibility(View.VISIBLE);

        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_RECEIVED_CRUSH_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");

                    if (Status.equals("success") ) {

                        JSONArray array = obj.getJSONArray("result");
                        Log.i("response..!", "length%%%" + array.length());
                        if (array.length()==0)
                        {
                            pd.dismiss();
                            recyclerView.setVisibility(View.GONE);
                            img_no_data.setVisibility(View.VISIBLE);
                        }
                        else {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String Receive_Crush_user_id = jsonObject.getString("id");
                                String Receive_Crush__user_nm = jsonObject.getString("name");
                                String Receive_Crush__age = jsonObject.getString("age");
                                String Receive_Crush__relationship_status = jsonObject.getString("relationship_status");
                                String Receive_Crush__distance = jsonObject.getString("distance");
                                String Receive_Crush__date = jsonObject.getString("date");
                                String Receive_Crush_image = jsonObject.getString("image");
                                String Receive_crush_compatible = jsonObject.getString("compatibility");


                                crushReceivedBeans.add(new CrushReceivedBeans(Receive_Crush_user_id, Receive_Crush__user_nm, Receive_Crush__age
                                        , Receive_Crush__relationship_status, Receive_Crush__distance, Receive_Crush__date, Receive_Crush_image, Receive_crush_compatible));
                            }

                            pd.dismiss();
                            recyclerView.setVisibility(View.VISIBLE);
                            img_no_data.setVisibility(View.GONE);
                            Collections.reverse(crushReceivedBeans);

                            crushReceived = new CrushReceived(getActivity(), crushReceivedBeans);
                            recyclerView.setAdapter(crushReceived);
                            crushReceived.notifyDataSetChanged();
                        }
                       // pb.setVisibility(View.GONE);

                    } else if (Status.equals("error")) {
                          pd.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        img_no_data.setVisibility(View.VISIBLE);
                        //pb.setVisibility(View.GONE);
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        img_no_data.setVisibility(View.VISIBLE);
                       // pb.setVisibility(View.GONE);
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
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
                recyclerView.setVisibility(View.GONE);
                img_no_data.setVisibility(View.VISIBLE);
                Toast.makeText(ctx, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("receiver_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                return params;
            }
 


        };
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }


    public class CrushReceived extends RecyclerView.Adapter<com.youme.candid.youmeapp.Activity.adapter.CrushReceived.ViewHolder> {

        private ArrayList<CrushReceivedBeans> crushReceivedBeans;
        Context context;
        SessionManager sessionManager;

        public CrushReceived(FragmentActivity activity, ArrayList<CrushReceivedBeans> crushReceivedBeans) {
            this.crushReceivedBeans = crushReceivedBeans;
            context = activity;
            sessionManager = new SessionManager(context);
        }


        @Override
        public com.youme.candid.youmeapp.Activity.adapter.CrushReceived.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.received_crush_request_layout, parent, false);

            return new com.youme.candid.youmeapp.Activity.adapter.CrushReceived.ViewHolder(v);
        }




        @Override
        public void onBindViewHolder(com.youme.candid.youmeapp.Activity.adapter.CrushReceived.ViewHolder holder, final int position) {

            holder.user_nm.setText(crushReceivedBeans.get(position).getReceive_crush__user_nm()+", "+crushReceivedBeans.get(position).getReceive_crush__age());
            if (!crushReceivedBeans.get(position).getReceive_crush_compatible().equalsIgnoreCase("null"))
            {
                holder.compeatable.setText(crushReceivedBeans.get(position).getReceive_crush_compatible()+"% Compatible");
            }
            holder.datt_date.setText(crushReceivedBeans.get(position).getReceive_crush__date());

            String urls = crushReceivedBeans.get(position).getReceive_crush_image();
            System.out.println("imagesss12344 "+urls);
            Glide.with(context).load(urls).into(holder.circleImageView);
            System.out.println("imagesss12344 "+urls);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sessionManager.setData(SessionManager.KEY_ANOTHER_USER_ID,crushReceivedBeans.get(position).getReceive_crush_user_id());
                    Intent intent = new Intent(ctx,Fullview_User_Profile.class);
                    startActivity(intent);
                }
            });


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

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_CRUSH_REQUEST, new Response.Listener<String>() {
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
                            crushReceivedBeans.remove(position);
                            crushReceived.notifyDataSetChanged();

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
                    Toast.makeText(context, "Couldn't connect to server.", Toast.LENGTH_SHORT).show();
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
            return crushReceivedBeans.size();
        }



        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView user_nm,compeatable,datt_date;
            CircleImageView circleImageView, btn_accept,btn_reject;

            public ViewHolder(View itemView) {
                super(itemView);

                circleImageView = (CircleImageView) itemView.findViewById(R.id.receive_crush_image_icon);
                user_nm = (TextView) itemView.findViewById(R.id.receive_crush_user_nm);
                compeatable = (TextView) itemView.findViewById(R.id.receive_crush_compeatable);
                datt_date = (TextView) itemView.findViewById(R.id.receive_crush_date);

                btn_accept = (CircleImageView) itemView.findViewById(R.id.accept);
                btn_reject = (CircleImageView) itemView.findViewById(R.id.reject);

            }
        }
    }



}
