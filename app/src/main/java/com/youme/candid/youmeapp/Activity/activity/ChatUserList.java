package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.youme.candid.youmeapp.Activity.model.ChatUserBean;
import com.youme.candid.youmeapp.Activity.model.FavouriteBean;
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

public class ChatUserList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ChatUserBean> chatuserArrayList;



    ImageView imag_backimg, img_no_data;;
    UserChatadapter userChatadapter;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ProgressDialog pd ;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);

        //for initiialize all the view this method will be invoked
        initView();

        //fo all click events  this method will be invoked
        clickEvent();

        //for get favourites user data
        Boolean internet = connectionDetector.isConnected(ctx);
        if (internet)
        {
            setTvAdapter();
        }
        else
        {
            recyclerView.setVisibility(View.GONE);
            img_no_data.setVisibility(View.VISIBLE);
            Toast.makeText(ctx, "Check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setTvAdapter()
    {
        //pb.setVisibility(View.VISIBLE);

        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ALL_ACCEPTED_DATE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);
                    String Status = obj.getString("status");

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
                                String chat_user_id = jsonObject.getString("user_id");
                                String chat_user_nm = jsonObject.getString("name");
                                String chat_user_pseudonym = jsonObject.getString("pseudonym");
                                String chat_user_image = jsonObject.getString("profile_pic");

                                chatuserArrayList.add(new ChatUserBean(chat_user_id,chat_user_nm,chat_user_pseudonym,chat_user_image));
                            }

                            pd.dismiss();
                            recyclerView.setVisibility(View.VISIBLE);
                            img_no_data.setVisibility(View.GONE);

                            userChatadapter = new UserChatadapter(ctx,chatuserArrayList);
                            recyclerView.setAdapter(userChatadapter);

                            // pb.setVisibility(View.GONE);
                        }
                    } else if (Status.equals("error")) {
                        pd.dismiss();
                        //pb.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        img_no_data.setVisibility(View.VISIBLE);
                        Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        img_no_data.setVisibility(View.VISIBLE);
                        // pb.setVisibility(View.GONE);
                        Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getData(SessionManager.KEY_USER_ID));
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }
//-------------------------------------------------------------------------------------------------------
    private void clickEvent() {
        imag_backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,MyProfileActiivty.class);
                startActivity(intent);
            }
        });
    }
//-------------------------------------------------------------------------------------------------------
    private void initView()
    {
        ctx = this;
        sessionManager = new SessionManager(ctx);
        connectionDetector = new ConnectionDetector();
        pd = new ProgressDialog(ctx);
        chatuserArrayList = new ArrayList<ChatUserBean>();

        img_no_data = (ImageView)findViewById(R.id.nofavdata);

        imag_backimg = (ImageView)findViewById(R.id.chat_backarrow);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

    }
//------------------------------------------------------------------------------------------------------------------
    private class UserChatadapter extends RecyclerView.Adapter<UserChatadapter.MyViewHolder> {
    public ArrayList<ChatUserBean> chatuserArrayList;
    public Context ctx;
    private LayoutInflater inflater;

    public UserChatadapter(Context ctx, ArrayList<ChatUserBean> chatuserArrayList)
    {
        inflater = LayoutInflater.from(ctx);
        this.chatuserArrayList = chatuserArrayList;
        this.ctx = ctx;
    }

    @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View v = inflater.inflate(R.layout.chat_user_list_adapter, parent, false);
            return new MyViewHolder(v);
        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String url = chatuserArrayList.get(position).getChat_user_image();
        System.out.println("url "+ url);
        holder.user_nm.setText(chatuserArrayList.get(position).getChat_user_nm());

        Glide.with(ctx).load(url).into(holder.circleImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx,Chat.class);
                intent.putExtra("user_name",chatuserArrayList.get(position).getChat_user_nm());
                intent.putExtra("user_id",chatuserArrayList.get(position).getChat_user_id());
                intent.putExtra("user_image",chatuserArrayList.get(position).getChat_user_image());
                startActivity(intent);

            }
        });
    }

    @Override
        public int getItemCount() {
            return chatuserArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView user_nm,compeatable,datt_date;
            CircleImageView circleImageView, btn_accept,btn_reject;
            public MyViewHolder(View itemView) {
                super(itemView);
                circleImageView = (CircleImageView) itemView.findViewById(R.id.chatuser_image_icon);
                user_nm = (TextView) itemView.findViewById(R.id.chat_user_name);
            }
        }
    }

//-----------------------------------------------------------------------------------------------------------




}
