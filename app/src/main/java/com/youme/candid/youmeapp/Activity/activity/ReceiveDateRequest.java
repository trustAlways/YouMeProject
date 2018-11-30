package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youme.candid.youmeapp.Activity.adapter.DateAdapter;
import com.youme.candid.youmeapp.Activity.model.DateReceiveRequestBeans;
import com.youme.candid.youmeapp.Activity.model.DateSentRequestBeans;
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

public class ReceiveDateRequest extends Fragment {

    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ProgressDialog pd ;
    RecyclerView recyclerView;
    ImageView img_no_data;

    LinearLayout linear_old,linear__new,linear_cnfrm;
    Context ctx;
    DateAdapter dateAdapter;
    String idd;
    ArrayList<DateReceiveRequestBeans> datebeans;
    View v;
    ProgressBar pb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.date_receive_request,null);

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

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_change_password);


    }*/
   private void Initialize() {
        ctx = getActivity();
        sessionManager = new SessionManager(getActivity());
        connectionDetector = new ConnectionDetector();
        pd = new ProgressDialog(getActivity());
        img_no_data = (ImageView)v.findViewById(R.id.nodatereceive);
        datebeans = new ArrayList<>();
        recyclerView = (RecyclerView)v.findViewById(R.id.date_receive_request_recycler_view);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_RECEIVED_DATE_REQ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    String Status = obj.getString("status");
                    String msg = obj.getString("message");
                    Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

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
                                String Received_date_user_id = jsonObject.getString("id");
                                String Received_date__user_nm = jsonObject.getString("name");
                                String Received_date__age = jsonObject.getString("age");
                                String Received_date__relationship_status = jsonObject.getString("relationship_status");
                                String Received_date__distance = jsonObject.getString("distance");
                                String Received_date__date = jsonObject.getString("date");
                                String Received_date_image = jsonObject.getString("image");

                                datebeans.add(new DateReceiveRequestBeans(Received_date_user_id, Received_date__user_nm, Received_date__age
                                        , Received_date__relationship_status, Received_date__distance, Received_date__date, Received_date_image));
                            }

                            pd.dismiss();
                            recyclerView.setVisibility(View.VISIBLE);
                            img_no_data.setVisibility(View.GONE);

                            Collections.reverse(datebeans);
                            dateAdapter = new DateAdapter(getActivity(), datebeans);
                            recyclerView.setAdapter(dateAdapter);

                            // pb.setVisibility(View.GONE);
                        }
                    } else if (Status.equals("error")) {
                          pd.dismiss();
                        //pb.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        img_no_data.setVisibility(View.VISIBLE);
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
                Toast.makeText(ctx, "Couldn't connect to server..", Toast.LENGTH_SHORT).show();

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



}
