package com.youme.candid.youmeapp.Activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.youme.candid.youmeapp.Activity.model.Apperance;
import com.youme.candid.youmeapp.Activity.model.CrushRequestBeans;
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

public class User_Favourite_Actiivty extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<FavouriteBean> favouriteArrayList;
    private CustomHobbiesAdapter customAdapter;

    ImageView imag_backimg, img_no_data;;
    ProgressBar pb;
    SessionManager sessionManager;
    ConnectionDetector connectionDetector;
    ProgressDialog pd ;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_favourites_activtiy);

       //for initiialize all the view this method will be invoked
        initView();

        //fo all click events  this method will be invoked
        clickEvent();

        Boolean internet = connectionDetector.isConnected(ctx);
        if (internet)
        {
            //for get favourites user data
            setTvAdapter();
        }
        else
        {
            Toast.makeText(ctx, "Check your internet connection.", Toast.LENGTH_LONG).show();
            pb.setVisibility(View.GONE);
            img_no_data.setVisibility(View.VISIBLE);
        }


    }



    private void initView()
    {
        ctx = this;
        sessionManager = new SessionManager(ctx);
        connectionDetector = new ConnectionDetector();
        pd = new ProgressDialog(ctx);
        favouriteArrayList = new ArrayList<>();
        pb = (ProgressBar)findViewById(R.id.progressbAr);
        pb.setVisibility(View.VISIBLE);

        img_no_data = (ImageView)findViewById(R.id.nofavdata);

        imag_backimg = (ImageView)findViewById(R.id.favourite_backarrow);
        recyclerView = (RecyclerView) findViewById(R.id.favourite_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                false));

    }

    private void clickEvent() {

        imag_backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,MyProfileActiivty.class);
                startActivity(intent);
            }
        });
    }


    private void setTvAdapter()
    {
        pb.setVisibility(View.VISIBLE);

        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setMessage("Loading..");
        pd.hide();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_FAVOURITES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Log.i("response..!", "data%%%" + response);
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);
                    String Status = obj.getString("status");
                    String msg = obj.getString("message");
                    Toast.makeText(User_Favourite_Actiivty.this, msg, Toast.LENGTH_SHORT).show();

                    if (Status.equals("success") ) {

                        JSONArray array = obj.getJSONArray("result");
                        Log.i("response..!", "length%%%" + array.length());
                        if (array.length()==0)
                        {
                            pd.dismiss();
                            pb.setVisibility(View.GONE);

                            recyclerView.setVisibility(View.GONE);

                            img_no_data.setVisibility(View.VISIBLE);
                        }
                        else {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String fav_user_id = jsonObject.getString("id");
                                String fav_user_nm = jsonObject.getString("name");
                                String fav_user_age = jsonObject.getString("age");
                                String fav_user_relationship_status = jsonObject.getString("relationship_status");
                                String fav_user_distance = jsonObject.getString("distance");
                                String fav_user_compatibility = jsonObject.getString("compatibility");
                                String fav_user_date = jsonObject.getString("date");
                                String image = jsonObject.getString("image");
                                favouriteArrayList.add(new FavouriteBean(fav_user_id, fav_user_nm, fav_user_age, fav_user_relationship_status
                                        , fav_user_distance, fav_user_date, image, fav_user_compatibility));
                            }

                            pd.dismiss();
                            pb.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            img_no_data.setVisibility(View.GONE);
                            Collections.reverse(favouriteArrayList);
                            customAdapter = new CustomHobbiesAdapter(User_Favourite_Actiivty.this, favouriteArrayList);
                            recyclerView.setAdapter(customAdapter);

                            // pb.setVisibility(View.GONE);
                        }
                    } else if (Status.equals("error")) {
                        pd.dismiss();
                        //pb.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        img_no_data.setVisibility(View.VISIBLE);
                        Toast.makeText(ctx, "Try after sometime.", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                        pb.setVisibility(View.GONE);

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

//.......................................................Inner Adapter class...........................................................//

    public  class CustomHobbiesAdapter extends RecyclerView.Adapter<CustomHobbiesAdapter.MyViewHolder> {

        private LayoutInflater inflater;
        public ArrayList<FavouriteBean> imageModelArrayList;
        public Context ctx;

        public CustomHobbiesAdapter(Context ctx, ArrayList<FavouriteBean> imageModelArrayList) {

            inflater = LayoutInflater.from(ctx);
            this.imageModelArrayList = imageModelArrayList;
            this.ctx = ctx;
        }

        @Override
        public CustomHobbiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.favourite_card_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final CustomHobbiesAdapter.MyViewHolder holder, final int position) {

            holder.user_nm.setText(imageModelArrayList.get(position).getFav_user_nm()+", "+imageModelArrayList.get(position).getFav_user_age());

            if (!imageModelArrayList.get(position).getFav_user_compatibility().equalsIgnoreCase("null"))
            {
                holder.compeatable.setText(imageModelArrayList.get(position).getFav_user_compatibility()+ "% Compatible");
                Log.i("response..!", "compatible%%%" + imageModelArrayList.get(position).getFav_user_compatibility());

            }

            holder.fav_date.setText(imageModelArrayList.get(position).getFav_user_date());
            String url = imageModelArrayList.get(position).getImage();
            System.out.println("image"+url);

            Glide.with(ctx).load(url).into(holder.circleImageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sessionManager.setData(SessionManager.KEY_ANOTHER_USER_ID,imageModelArrayList.get(position).getFav_user_id());
                    Intent intent = new Intent(ctx,Fullview_User_Profile.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return imageModelArrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView user_nm,compeatable,fav_date;
             CircleImageView circleImageView;
             public MyViewHolder(View itemView) {
                super(itemView);

                circleImageView = (CircleImageView) itemView.findViewById(R.id.favourite_image_icon);
                user_nm = (TextView) itemView.findViewById(R.id.fav_user_name);
                compeatable = (TextView) itemView.findViewById(R.id.fav_compeatible);
                fav_date = (TextView) itemView.findViewById(R.id.fav_date);

            }

        }
    }
}

/*for (int i = 0; i < Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.size(); i++){
            if(Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getSelected()) {
               // tv.setText(tv.getText() + " " + Edit_User_Lifestyle.CustomLifeStyleAdapter.imageModelArrayList.get(i).getLifestyle());
            }
        }*/